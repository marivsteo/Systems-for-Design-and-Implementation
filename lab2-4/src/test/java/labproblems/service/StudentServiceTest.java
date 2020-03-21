package labproblems.service;

import labproblems.domain.entities.Student;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.domain.exceptions.ValidatorException;
import labproblems.repository.inMemoryRepository.InMemoryRepository;
import labproblems.repository.Repository;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Marius
 * This class contains all the tests related to the StudentService
 */
public class StudentServiceTest {

    @Test
    public void testAddStudent() throws ValidatorException{
        StudentValidator studentValidator = new StudentValidator();
        Repository<Long, Student> repository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(repository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L,"sn1","s1",1);
        studentService.addStudent(2L,"sn2","s2",1);

        Student s3 = repository.findOne(1L).get();

        assertEquals("The two students should be the same", s, s3);
    }


    @Test(expected = ValidatorException.class)
    public void testAddException() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);

        studentService.addStudent(1L, "sn1", "", 1);
    }

    @Test
    public void testGetAll() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L, "sn1", "s1", 1);
        studentService.addStudent(2L,"sn2","s2", 2);

        Set<Student> students = studentService.getAllStudents();

        assertTrue("The set should contain s", students.contains(s));
        assertTrue("The set should contain s2", students.contains(s2));
    }

    @Test
    public void testFilterStudentsByName() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L, "sn1", "s1", 1);
        studentService.addStudent(2L,"sn2","s2", 2);

        Set<Student> students = studentService.filterStudentsByName("s1");

        assertTrue("The set should contain s", students.contains(s));
        assertFalse("The set should not contain s2", students.contains(s2));
    }

    @Test
    public void testFilterStudentsBySerialNumber() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L, "sn1", "s1", 1);
        studentService.addStudent(2L,"sn2","s2", 2);

        Set<Student> students = studentService.filterStudentsBySerialNumber("sn1");

        assertTrue("The set should contain s", students.contains(s));
        assertFalse("The set should not contain s2", students.contains(s2));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L, "sn1", "s1", 1);
        studentService.addStudent(2L,"sn2","s2", 2);

        studentService.removeStudent(1L);

        assertEquals("The repo should not contain a student with the ID 1L", Optional.empty(), studentRepository.findOne(1L));
        //assertFalse("The set should not contain s2", students.contains(s2));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L, "sn1", "s1", 1);
        studentService.addStudent(2L,"sn2","s2", 2);

        studentService.updateStudent(1L, "sn3", "s3", 3);

        assertEquals("The repo should have a student with name s3", "s3", studentRepository.findOne(1L).get().getName());
        assertEquals("The repo should have a student with serial number sn3", "sn3", studentRepository.findOne(1L).get().getSerialNumber());

        //assertFalse("The set should not contain s2", students.contains(s2));
    }
}
