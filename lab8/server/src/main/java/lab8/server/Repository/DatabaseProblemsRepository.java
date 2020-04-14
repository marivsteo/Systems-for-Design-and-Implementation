package lab8.server.Repository;

import lab8.common.Entities.Problem;
import lab8.common.Exceptions.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.toIntExact;

@Repository
public class DatabaseProblemsRepository implements ISortingRepository <Long, Problem>{


    private Map<Long, Problem> problemMap = new HashMap<>();

    @Autowired
    private JdbcOperations jdbcOperations;

    private void loadData() throws SQLException {
        String sql = "select * from Problems";


        List<Problem> problems = jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            int number = rs.getInt("number");
            String text = rs.getString("text");

            Problem problem = new Problem(number,text);
            problem.setId(id);
            this.problemMap.putIfAbsent(problem.getId(),problem);

            return problem;
            //System.out.println(problem);
        });
    }

    private void writeData() throws SQLException {
        Iterator it = this.problemMap.entrySet().iterator();
        String sql2 = "delete from Problems";
        jdbcOperations.update(sql2);

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            Problem problem = (Problem) pair.getValue();
            String sql = "insert into Problems (id, number, text) values(?,?,?)";

            //System.out.println("Problem = " + problem);

            jdbcOperations.update(sql, problem.getId(), problem.getNumber(), problem.getText());

        }
    }

    @Override
    public Iterable<Problem> findAll(Sort sort) {
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Problem> allEntities = this.problemMap.values().stream().collect(Collectors.toList());
        return sort.sortp(allEntities);
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
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
