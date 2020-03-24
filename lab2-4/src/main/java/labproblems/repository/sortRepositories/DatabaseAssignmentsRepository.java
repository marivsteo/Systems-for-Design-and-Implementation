package labproblems.repository.sortRepositories;

import labproblems.domain.entities.Assignment;
import labproblems.domain.entities.Student;
import labproblems.domain.exceptions.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class DatabaseAssignmentsRepository implements ISortingRepository <Long,Assignment>{

    private static final String URL = "jdbc:postgresql://localhost:5432/labProblemsSDI";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");
    private Map<Long, Assignment> assignmentMap = new HashMap<>();


    public DatabaseAssignmentsRepository() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception exception){
            throw new SQLException("DatabaseAssignmentsRepository > DatabaseAssignmentsRepository: Could not connect to db. (verify password)");
        }
        loadData();
    }

    private void loadData() throws SQLException {
        String sql = "select * from Assignments";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Long studentId = resultSet.getLong("studentid");
            Long problemId = resultSet.getLong("problemsid");
            Float grade = resultSet.getFloat("grade");
            Assignment assignment = new Assignment(name, studentId, problemId, grade);
            assignment.setId(id);
            this.assignmentMap.putIfAbsent(assignment.getId(),assignment);
            //System.out.println(assignment);
        }
    }

    private void writeData() throws SQLException {
        Iterator it = this.assignmentMap.entrySet().iterator();
        String sql2 = "delete from Assignments";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.executeUpdate();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            Assignment assignment = (Assignment) pair.getValue();
            String sql = "insert into Assignments (id, name, studentid, problemsid, grade) values(?,?,?,?,?)";

            //System.out.println("Assignment = " + assignment);

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, toIntExact(assignment.getId()));
            preparedStatement.setString(2, assignment.getName());
            preparedStatement.setInt(3, toIntExact(assignment.getStudent()));
            preparedStatement.setInt(4, toIntExact(assignment.getProblem()));
            preparedStatement.setFloat(5, assignment.getGrade());

            preparedStatement.executeUpdate();

        }

    }

    @Override
    public Iterable<Assignment> findAll(Sort sort) {
        List<Assignment> allEntities = this.assignmentMap.values().stream().collect(Collectors.toList());
        allEntities = (List<Assignment>) sort.sorta(allEntities);
        return allEntities;
    }

    @Override
    public Optional<Assignment> findOne(Long id) {
        try{
            assert(id!=null);
        }
        catch (AssertionError error){
            throw new IllegalArgumentException("DatabaseAssignmentsRepository > findOne : ID must not be null.");
        }
        return Optional.ofNullable(this.assignmentMap.get(id));
    }

    @Override
    public Iterable<Assignment> findAll() {
        try {
            loadData();
        } catch(Exception exception) {}
        Set<Assignment> allEntities = this.assignmentMap.values().stream().collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        try{
            assert (entity!=null);
        } catch(AssertionError error) {
            throw new IllegalArgumentException("DatabaseAssignmentsRepository > save: ID must not be null.");
        }

        Optional<Assignment> optional =  Optional.ofNullable(this.assignmentMap.putIfAbsent(entity.getId(), entity));
        try {
            writeData();
        } catch (Exception exception){
            throw new ValidatorException(exception.toString());
        }
        return optional;
    }

    @Override
    public Optional<Assignment> delete(Long id) {
        try{
            assert(id!=null);
        } catch (AssertionError error){
            throw new IllegalArgumentException("DatabaseAssignmentsRepository > delete : id must not be null.");
        }
        Optional<Assignment> optionalAssignment = Optional.ofNullable(this.assignmentMap.remove(id));
        try {
            writeData();
        } catch (Exception exception){
            throw new ValidatorException(exception.toString());
        }
        return optionalAssignment;
    }

    @Override
    public Optional<Assignment> update(Assignment entity) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        if (entity == null) {
            throw new IllegalArgumentException("DatabaseAssignmentsRepository > update: The entity must not be null.");
        }

        if(this.assignmentMap.containsKey(entity.getId())) {
            this.assignmentMap.computeIfPresent(entity.getId(), (k, v) -> entity);
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
