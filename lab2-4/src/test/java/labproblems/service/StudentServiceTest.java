package labproblems.service;

import labproblems.domain.Student;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.ValidatorException;
import labproblems.repository.InMemoryRepository;
import labproblems.repository.Repository;
import org.junit.Test;

/**
 * @author Marius
 * This class contains all the tests related to the StudentService
 */
public class StudentServiceTest {

    @Test
    public void testAddStudent() throws ValidatorException{
        StudentValidator studentValidator = new StudentValidator();
        Repository<Long, Student> repository = new InMemoryRepository<>(studentValidator);
        StudentService studentService = new StudentService(repository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentService.addStudent(1L,"sn1","s1",1);
        studentService.addStudent(2L,"sn2","s2",1);

        System.out.println(repository);
    }
}
