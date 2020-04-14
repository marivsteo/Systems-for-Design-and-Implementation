package lab8.server.Repository;

import lab8.common.Entities.Student;
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
public class DatabaseStudentsRepository implements ISortingRepository <Long, Student>{

    private Map<Long, Student> studentMap = new HashMap<>();

    @Autowired
    private JdbcOperations jdbcOperations;

    public void loadData() throws SQLException {
        String sql = "select * from Students";

        List<Student> students = jdbcOperations.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            String serialNumber = rs.getString("serialnr");
            String name = rs.getString("name");
            int group = rs.getInt("groupnr");

            Student student = new Student(serialNumber, name, group);
            student.setId(id);
            this.studentMap.putIfAbsent(student.getId(), student);

            return student;
        });
    }

    private void writeData() throws SQLException {
        Iterator it = this.studentMap.entrySet().iterator();
        String sql2 = "delete from Students";
        jdbcOperations.update(sql2);

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();


            Student student = (Student) pair.getValue();
            String sql = "insert into Students (id, serialnr, name, groupnr) values(?,?,?,?)";

//            System.out.println("Student = " + student);

            jdbcOperations.update(sql, student.getId(), student.getSerialNumber(), student.getName(), student.getGroup());

            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Override
    public Iterable<Student> findAll(Sort sort) {
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Set<Student> allEntities = this.studentMap.values().stream().collect(Collectors.toSet());
        return allEntities;
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
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
