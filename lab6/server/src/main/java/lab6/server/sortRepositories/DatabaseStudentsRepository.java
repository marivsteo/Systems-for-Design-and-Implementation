package lab6.server.sortRepositories;

import lab6.common.Entities.Student;
import lab6.common.exceptions.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class DatabaseStudentsRepository implements ISortingRepository <Long, Student>{

    private static final String URL = "jdbc:postgresql://localhost:5432/labProblemsSDI";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");
    private Map<Long, Student> studentMap = new HashMap<>();


    public DatabaseStudentsRepository() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception exception){
            exception.printStackTrace();
            throw new SQLException("DatabaseStudentsRepository > DatabaseStudentsRepository: Could not connect to db. (verify password)");
        }
        loadData();
    }

    private void loadData() throws SQLException {
        String sql = "select * from Students";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String serialNumber = resultSet.getString("serialnr");
            String name = resultSet.getString("name");
            int groupNumber = resultSet.getInt("groupnr");
            Student student = new Student(serialNumber,name,groupNumber);
            student.setId(id);
            this.studentMap.putIfAbsent(student.getId(),student);
            //System.out.println(student);
        }
    }

    private void writeData() throws SQLException {
        Iterator it = this.studentMap.entrySet().iterator();
        String sql2 = "delete from Students";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.executeUpdate();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            Student student = (Student) pair.getValue();
            String sql = "insert into Students (id, serialnr, name, groupnr) values(?,?,?,?)";

            //System.out.println("Student = " + student);

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, toIntExact(student.getId()));
            preparedStatement.setString(2, student.getSerialNumber());
            preparedStatement.setString(3, student.getName());
            preparedStatement.setInt(4, student.getGroup());

            preparedStatement.executeUpdate();

            //it.remove(); // avoids a ConcurrentModificationException
        }

    }

    @Override
    public Iterable<Student> findAll(Sort sort) {
        List<Student> allEntities = this.studentMap.values().stream().collect(Collectors.toList());
        return sort.sorts(allEntities);
    }

    @Override
    public Optional<Student> findOne(Long id) {
        try{
            assert(id!=null);
        }
        catch (AssertionError error){
            throw new IllegalArgumentException("InMemoryRepository > findOne : ID must not be null.");
        }
        return Optional.ofNullable(this.studentMap.get(id));
    }

    @Override
    public Iterable<Student> findAll() {
        Set<Student> allEntities = this.studentMap.values().stream().collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        System.out.println("qaq" + this.studentMap.size());
        try{
            assert (entity!=null);
        } catch(AssertionError error) {
            throw new IllegalArgumentException("DatabaseStudentsRepository > save: ID must not be null.");
        }

        Optional<Student> optional =  Optional.ofNullable(this.studentMap.putIfAbsent(entity.getId(), entity));
        System.out.println(this.studentMap.size());
        try {
            writeData();
            System.out.println("got out of writeData()");
        } catch (Exception exception){
            throw new ValidatorException(exception.toString());
        }
        return optional;
    }

    @Override
    public Optional<Student> delete(Long id) {
        try{
            assert(id!=null);
        } catch (AssertionError error){
            throw new IllegalArgumentException("DatabaseStudentsRepository > delete : id must not be null.");
        }
        Optional<Student> optionalStudent = Optional.ofNullable(this.studentMap.remove(id));
        try {
            writeData();
        } catch (Exception exception){
            throw new ValidatorException(exception.toString());
        }
        return optionalStudent;
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        if (entity == null) {
            throw new IllegalArgumentException("DatabaseStudentsRepository > update: The entity must not be null.");
        }

        if(this.studentMap.containsKey(entity.getId())) {
            this.studentMap.computeIfPresent(entity.getId(), (k, v) -> entity);
            try {
                writeData();
            } catch (Exception exception){
                throw new ValidatorException(exception.toString());
            }
            return null;
        }

        return Optional.ofNullable(entity);
    }
}
