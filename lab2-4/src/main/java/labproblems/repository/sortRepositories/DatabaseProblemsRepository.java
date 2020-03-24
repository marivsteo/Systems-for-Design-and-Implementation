package labproblems.repository.sortRepositories;

import labproblems.domain.entities.Problem;
import labproblems.domain.exceptions.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

public class DatabaseProblemsRepository implements ISortingRepository <Long,Problem>{

    private static final String URL = "jdbc:postgresql://localhost:5432/labProblemsSDI";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");
    private Map<Long, Problem> problemMap = new HashMap<>();


    public DatabaseProblemsRepository() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception exception){
            throw new SQLException("DatabaseProblemsRepository > DatabaseProblemsRepository: Could not connect to db. (verify password)");
        }
        loadData();
    }

    private void loadData() throws SQLException {
        String sql = "select * from Problems";

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            int number = resultSet.getInt("number");
            String text = resultSet.getString("text");

            Problem problem = new Problem(number,text);
            problem.setId(id);
            this.problemMap.putIfAbsent(problem.getId(),problem);
            //System.out.println(problem);
        }
    }

    private void writeData() throws SQLException {
        Iterator it = this.problemMap.entrySet().iterator();
        String sql2 = "delete from Problems";
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
        preparedStatement2.executeUpdate();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            Problem problem = (Problem) pair.getValue();
            String sql = "insert into Problems (id, number, text) values(?,?,?)";

            //System.out.println("Problem = " + problem);

            PreparedStatement preparedStatement =
                    connection.prepareStatement(sql);
            preparedStatement.setInt(1, toIntExact(problem.getId()));
            preparedStatement.setInt(2, problem.getNumber());
            preparedStatement.setString(3, problem.getText());

            preparedStatement.executeUpdate();

        }

    }

    @Override
    public Iterable<Problem> findAll(Sort sort) {
        return null;
    }

    @Override
    public Optional<Problem> findOne(Long id) {
        try{
            assert(id!=null);
        }
        catch (AssertionError error){
            throw new IllegalArgumentException("DatabaseProblemRepository > findOne : ID must not be null.");
        }
        return Optional.ofNullable(this.problemMap.get(id));
    }

    @Override
    public Iterable<Problem> findAll() {
        Set<Problem> allEntities = this.problemMap.values().stream().collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        try{
            assert (entity!=null);
        } catch(AssertionError error) {
            throw new IllegalArgumentException("DatabaseProblemsRepository > save: ID must not be null.");
        }

        Optional<Problem> optional =  Optional.ofNullable(this.problemMap.putIfAbsent(entity.getId(), entity));
        try {
            writeData();
        } catch (Exception exception){
            throw new ValidatorException(exception.toString());
        }
        return optional;
    }

    @Override
    public Optional<Problem> delete(Long id) {
        try{
            assert(id!=null);
        } catch (AssertionError error){
            throw new IllegalArgumentException("DatabaseProblemsRepository > delete : id must not be null.");
        }
        Optional<Problem> optionalProblem = Optional.ofNullable(this.problemMap.remove(id));
        try {
            writeData();
        } catch (Exception exception){
            throw new ValidatorException(exception.toString());
        }
        return optionalProblem;
    }

    @Override
    public Optional<Problem> update(Problem entity) throws ValidatorException, ParserConfigurationException, IOException, SAXException, TransformerException {
        if (entity == null) {
            throw new IllegalArgumentException("DatabaseProblemsRepository > update: The entity must not be null.");
        }

        if(this.problemMap.containsKey(entity.getId())) {
            this.problemMap.computeIfPresent(entity.getId(), (k, v) -> entity);
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
