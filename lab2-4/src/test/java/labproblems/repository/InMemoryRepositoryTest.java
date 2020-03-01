package labproblems.repository;

import labproblems.domain.Student;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import org.junit.Ignore;
import org.junit.Test;
import labproblems.domain.validators.ValidatorException;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Marius
 */
public class InMemoryRepositoryTest {

    @Test
    public void testFindOne() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);

        Optional<Student> s3 = studentRepository.findOne(1L);

        Student s4 = s3.get();

        assertEquals("The two students should be the same", s, s4);
    }

    @Ignore
    @Test
    public void testFindAll() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test
    public void testSave() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test(expected = ValidatorException.class)
    public void testSaveException() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test
    public void testDelete() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test
    public void testUpdate() throws Exception {
        fail("Not yet tested");
    }

    @Ignore
    @Test(expected = ValidatorException.class)
    public void testUpdateException() throws Exception {
        fail("Not yet tested");
    }
}