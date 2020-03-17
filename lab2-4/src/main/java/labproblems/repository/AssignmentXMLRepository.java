package labproblems.repository;

import labproblems.domain.Assignment;
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

public class AssignmentXMLRepository extends InMemoryRepository<Long, Assignment> {

    private String filename;

    public AssignmentXMLRepository( String fileName) {
        this.filename = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(filename);

        try {
            List<Assignment> result = new ArrayList<>();

            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("./data/assignments.xml");

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();

            result = IntStream
                    .range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> createAssignmentFromElement((Element) node))
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

    private static Assignment createAssignmentFromElement(Element assignmentElement) {
        Assignment assignment = new Assignment();

        assignment.setId(Long.parseLong(getTextFromTagName(assignmentElement, "id")));
        assignment.setName(getTextFromTagName(assignmentElement,"name"));
        assignment.setStudent(Long.parseLong(getTextFromTagName(assignmentElement,"student")));
        assignment.setProblem(Long.parseLong(getTextFromTagName(assignmentElement,"problem")));
        assignment.setGrade(Float.parseFloat(getTextFromTagName(assignmentElement, "grade")));

        return assignment;
    }

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    public static Node assignmentToNode(Assignment assignment, Document document) {
        Element assignmentElement = document.createElement("assignment");

        appendChildWithTextToNode(document, assignmentElement, "id", String.valueOf(assignment.getId()));
        appendChildWithTextToNode(document, assignmentElement, "name", assignment.getName());
        appendChildWithTextToNode(document, assignmentElement, "student", String.valueOf(assignment.getStudent()));
        appendChildWithTextToNode(document, assignmentElement, "problem", String.valueOf(assignment.getProblem()));
        appendChildWithTextToNode(document, assignmentElement, "grade", String.valueOf(assignment.getGrade()));

        return assignmentElement;
    }

    public Optional<Assignment> save(Assignment assignment_) {
        Optional<Assignment> optional = super.save(assignment_);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveAssignment(assignment_);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
    public static void saveAssignment(Assignment assignment) throws ParserConfigurationException, IOException, SAXException, TransformerException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/assignments.xml");

        Element root = document.getDocumentElement();
        Node assignmentNode = assignmentToNode(assignment, document);
        root.appendChild(assignmentNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult("./data/assignments.xml"));
    }

    public Optional<Assignment> delete(Long id) {


        Optional<Assignment> optional = super.delete(id);

        Document document = null;
        try {
            document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("./data/assignments.xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        // <person>
        NodeList nodes = document.getElementsByTagName("assignment");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element assignment_ = (Element)nodes.item(i);
            // <name>
            Element id1 = (Element)assignment_.getElementsByTagName("id").item(0);
            String id2 = id1.getTextContent();
            Long id3 = Long.parseLong(id2);
            if (id3.equals(id)) {
                assignment_.getParentNode().removeChild(assignment_);
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
                    new StreamResult("./data/assignments.xml"));
        } catch (TransformerException e) {
            e.printStackTrace();
        }


        if( optional.isPresent())
            return optional;
        return optional;
    }

    public Optional<Assignment> update(Assignment assignment_) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/assignments.xml");

        // <person>
        NodeList nodes = document.getElementsByTagName("assignment");

        Long id = assignment_.getId();

        if (assignment_ == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The assignment must not be null.");
        }

        Map<Long,Assignment> entities = super.getEntities();

        if(entities.containsKey(assignment_.getId())) {
            entities.computeIfPresent(assignment_.getId(), (k, v) -> assignment_);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element assignment = (Element)nodes.item(i);
                // <name>
                Element id1 = (Element)assignment.getElementsByTagName("id").item(0);
                String id2 = id1.getTextContent();
                Long id3 = Long.parseLong(id2);
                if (id3.equals(id)) {
                    assignment.getParentNode().removeChild(assignment);
                }
            }

            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult("./data/assignments.xml"));

            saveAssignment(assignment_);
            return null;
        }
        return Optional.ofNullable(assignment_);
    }
}
