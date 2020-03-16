package labproblems.repository;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentXMLRepository extends InMemoryRepository<Long, Student> {

    private String filename;

    public StudentXMLRepository( String fileName) {
        this.filename = fileName;

        loadData();
    }

    public void Test() {
        loadData();

        try {
            Student s = new Student("sn4", "n4", 4);
            s.setId(4L);
            saveStudent(s);
        } catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Path path = Paths.get(filename);

        try {
            List<Student> result = new ArrayList<>();

            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse("./data/students.xml");

            Element root = document.getDocumentElement();

            NodeList children = root.getChildNodes();

            result = IntStream
                    .range(0, children.getLength())
                    .mapToObj(children::item)
                    .filter(node -> node instanceof Element)
                    .map(node -> createStudentFromElement((Element) node))
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

    private static Student createStudentFromElement(Element studentElement) {
        Student student = new Student();

        student.setId(Long.parseLong(getTextFromTagName(studentElement, "id")));
        student.setSerialNumber(getTextFromTagName(studentElement,"serialNumber"));
        student.setName(getTextFromTagName(studentElement,"name"));
        student.setGroup(Integer.parseInt(getTextFromTagName(studentElement,"group")));

        return student;
    }

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    public static Node studentToNode(Student student, Document document) {
        Element studentElement = document.createElement("student");

        appendChildWithTextToNode(document, studentElement, "id", String.valueOf(student.getId()));
        appendChildWithTextToNode(document, studentElement, "serialNumber", student.getSerialNumber());
        appendChildWithTextToNode(document, studentElement, "name", student.getName());
        appendChildWithTextToNode(document, studentElement, "group", String.valueOf(student.getGroup()));

        return studentElement;
    }

    public static void saveStudent(Student student) throws ParserConfigurationException, IOException, SAXException, TransformerException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/students.xml");

        Element root = document.getDocumentElement();
        Node studentNode = studentToNode(student, document);
        root.appendChild(studentNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult("./data/students.xml"));
    }
}
