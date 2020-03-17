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
import javax.xml.transform.TransformerConfigurationException;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProblemXMLRepository extends InMemoryRepository<Long, Problem> {

    private String filename;

    public ProblemXMLRepository( String fileName) {
        this.filename = fileName;

        loadData();
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


    public Optional<Problem> save(Problem problem_) {
        Optional<Problem> optional = super.save(problem_);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveProblem(problem_);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
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

    public Optional<Problem> delete(Long id) {


        Optional<Problem> optional = super.delete(id);

        Document document = null;
        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("./data/problems.xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        // <person>
        NodeList nodes = document.getElementsByTagName("problem");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element problem_ = (Element)nodes.item(i);
            // <name>
            Element id1 = (Element)problem_.getElementsByTagName("id").item(0);
            String id2 = id1.getTextContent();
            Long id3 = Long.parseLong(id2);
            if (id3.equals(id)) {
                problem_.getParentNode().removeChild(problem_);
            }
        }

        Transformer transformer = null;
        try {
            transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        try {
            transformer.transform(new DOMSource(document),
                    new StreamResult("./data/problems.xml"));
        } catch (TransformerException e) {
            e.printStackTrace();
        }


        if( optional.isPresent())
            return optional;
        return optional;
    }

    public Optional<Problem> update(Problem problem_) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/problems.xml");

        // <person>
        NodeList nodes = document.getElementsByTagName("problem");

        Long id = problem_.getId();

        if (problem_ == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The problem must not be null.");
        }

        Map<Long,Problem> entities = super.getEntities();

        if(entities.containsKey(problem_.getId())) {
            entities.computeIfPresent(problem_.getId(), (k, v) -> problem_);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element problem = (Element)nodes.item(i);
                // <name>
                Element id1 = (Element)problem.getElementsByTagName("id").item(0);
                String id2 = id1.getTextContent();
                Long id3 = Long.parseLong(id2);
                if (id3.equals(id)) {
                    problem.getParentNode().removeChild(problem);
                }
            }

            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult("./data/problems.xml"));

            saveProblem(problem_);
            return null;
        }
        return Optional.ofNullable(problem_);
    }
}
