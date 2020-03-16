package labproblems.repository;

import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.ValidatorException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemXMLRepository extends InMemoryRepository<Long, Problem> {

    private String filename;

    public ProblemXMLRepository( String fileName) {
        this.filename = fileName;

        loadData();
    }

    public void Test() {
        loadData();

        try {
            Problem p = new Problem(4, "problem number 4");
            p.setId(4L);
            saveProblem(p);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Path path = Paths.get(filename);

        try {
            List<Problem> result = new ArrayList<>();

            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("./data/problems.xml");

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();

            result = IntStream
                    .range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> createProblemFromElement((Element) node))
                    .collect(Collectors.toList());

            try {
                result.forEach(super::save);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    private static String getTextFromTagName(Element parentElement, String tagName) {
        Node node = parentElement.getElementsByTagName(tagName).item(0);
        return node.getTextContent();
    }

    private static Problem createProblemFromElement(Element problemElement) {
        Problem problem = new Problem();

        problem.setId(Long.parseLong(getTextFromTagName(problemElement, "id")));
        problem.setNumber(Integer.parseInt(getTextFromTagName(problemElement,"number")));
        problem.setText(getTextFromTagName(problemElement,"text"));

        return problem;
    }

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    public static Node problemToNode(Problem problem, Document document) {
        Element problemElement = document.createElement("problem");

        appendChildWithTextToNode(document, problemElement, "id", String.valueOf(problem.getId()));
        appendChildWithTextToNode(document, problemElement, "number", String.valueOf(problem.getNumber()));
        appendChildWithTextToNode(document, problemElement, "text", problem.getText());

        return problemElement;
    }

    public static void saveProblem(Problem problem) throws ParserConfigurationException, IOException, SAXException, TransformerException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/problems.xml");

        Element root = document.getDocumentElement();
        Node problemNode = problemToNode(problem, document);
        root.appendChild(problemNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult("./data/problems.xml"));
    }
}
