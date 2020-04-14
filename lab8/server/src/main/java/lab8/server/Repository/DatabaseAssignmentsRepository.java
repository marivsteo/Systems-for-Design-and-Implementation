package lab8.server.Repository;

import lab8.common.Entities.Assignment;
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
public class DatabaseAssignmentsRepository implements ISortingRepository <Long, Assignment>{


    private Map<Long, Assignment> assignmentMap = new HashMap<>();

    @Autowired
    private JdbcOperations jdbcOperations;

    private void loadData() throws SQLException {
        String sql = "select * from Assignments";

        List<Assignment> assignments = jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String name = rs.getString("name");
            Long studentId = rs.getLong("studentid");
            Long problemId = rs.getLong("problemsid");
            Float grade = rs.getFloat("grade");

            Assignment assignment = new Assignment(name, studentId, problemId, grade);
            assignment.setId(id);
            this.assignmentMap.putIfAbsent(assignment.getId(),assignment);

            return assignment;
            //System.out.println(assignment);
        });
    }

    private void writeData() throws SQLException {
        Iterator it = this.assignmentMap.entrySet().iterator();
        String sql2 = "delete from Assignments";
        jdbcOperations.update(sql2);
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            Assignment assignment = (Assignment) pair.getValue();
            String sql = "insert into Assignments (id, name, studentid, problemsid, grade) values(?,?,?,?,?)";

            //System.out.println("Assignment = " + assignment);

            jdbcOperations.update(sql, assignment.getId(), assignment.getName(), assignment.getStudent(),
                    assignment.getProblem(), assignment.getGrade());

        }
    }

    @Override
    public Iterable<Assignment> findAll(Sort sort) {
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
