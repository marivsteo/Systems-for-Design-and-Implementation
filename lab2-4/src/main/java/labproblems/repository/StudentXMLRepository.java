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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StudentXMLRepository extends InMemoryRepository<Long, Student> {

    private String filename;

    public StudentXMLRepository( String fileName) {
        this.filename = fileName;

        loadData();
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
        Student student_ = new Student();

        student_.setId(Long.parseLong(getTextFromTagName(studentElement, "id")));
        student_.setSerialNumber(getTextFromTagName(studentElement,"serialNumber"));
        student_.setName(getTextFromTagName(studentElement,"name"));
        student_.setGroup(Integer.parseInt(getTextFromTagName(studentElement,"group")));

        return student_;
    }

    private static void appendChildWithTextToNode(Document document,
                                                  Node parentNode,
                                                  String tagName,
                                                  String textContent) {
        Element element = document.createElement(tagName);
        element.setTextContent(textContent);
        parentNode.appendChild(element);
    }

    public static Node studentToNode(Student student_, Document document) {
        Element studentElement = document.createElement("student");

        appendChildWithTextToNode(document, studentElement, "id", String.valueOf(student_.getId()));
        appendChildWithTextToNode(document, studentElement, "serialNumber", student_.getSerialNumber());
        appendChildWithTextToNode(document, studentElement, "name", student_.getName());
        appendChildWithTextToNode(document, studentElement, "group", String.valueOf(student_.getGroup()));

        return studentElement;
    }

    public Optional<Student> save(Student student_) {
        Optional<Student> optional = super.save(student_);
        if (optional.isPresent()) {
            return optional;
        }
        try {
            saveStudent(student_);
        } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void saveStudent(Student student_) throws ParserConfigurationException, IOException, SAXException, TransformerException, TransformerException {
        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/students.xml");

        Element root = document.getDocumentElement();
        Node studentNode = studentToNode(student_, document);
        root.appendChild(studentNode);

        Transformer transformer = TransformerFactory
                .newInstance()
                .newTransformer();
        transformer.transform(new DOMSource(document),
                new StreamResult("./data/students.xml"));
    }

    public Optional<Student> delete(Long id) {


        Optional<Student> optional = super.delete(id);

        Document document = null;
        try {
            document = DocumentBuilderFactory
                        .newInstance()
                        .newDocumentBuilder()
                        .parse("./data/students.xml");
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        // <person>
        NodeList nodes = document.getElementsByTagName("student");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element student_ = (Element)nodes.item(i);
            // <name>
            Element id1 = (Element)student_.getElementsByTagName("id").item(0);
            String id2 = id1.getTextContent();
            Long id3 = Long.parseLong(id2);
            if (id3.equals(id)) {
                student_.getParentNode().removeChild(student_);
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
                                        new StreamResult("./data/students.xml"));
        } catch (TransformerException e) {
            e.printStackTrace();
        }


        if( optional.isPresent())
            return optional;
        return optional;
    }

    public Optional<Student> update(Student student_) throws ParserConfigurationException, IOException, SAXException, TransformerException {

        Document document = DocumentBuilderFactory
                .newInstance()
                .newDocumentBuilder()
                .parse("./data/students.xml");

        // <person>
        NodeList nodes = document.getElementsByTagName("student");

        Long id = student_.getId();

        if (student_ == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The student must not be null.");
        }

        Map<Long,Student> entities = super.getEntities();

        if(entities.containsKey(student_.getId())) {
            entities.computeIfPresent(student_.getId(), (k, v) -> student_);

            for (int i = 0; i < nodes.getLength(); i++) {
                Element student = (Element)nodes.item(i);
                // <name>
                Element id1 = (Element)student.getElementsByTagName("id").item(0);
                String id2 = id1.getTextContent();
                Long id3 = Long.parseLong(id2);
                if (id3.equals(id)) {
                    student.getParentNode().removeChild(student);
                }
            }

            Transformer transformer = TransformerFactory
                    .newInstance()
                    .newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult("./data/students.xml"));

            saveStudent(student_);
            return null;
        }
        return Optional.ofNullable(student_);
    }
}
