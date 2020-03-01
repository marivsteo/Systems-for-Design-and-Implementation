package labproblems.repository;

import labproblems.domain.Student;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import org.junit.Ignore;
import org.junit.Test;
import labproblems.domain.validators.ValidatorException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Test
    public void testFindAll() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);

        Iterable<Student> students = studentRepository.findAll();

        List<Student> result =
                StreamSupport.stream(students.spliterator(), false)
                        .collect(Collectors.toList());

        assertEquals("The two students should be the same", s, result.get(0));
        assertEquals("The two students should be the same", s2, result.get(1));


    }

    @Test
    public void testSave() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);

        Optional<Student> o = studentRepository.findOne(1L);

        Student s3 = o.get();

        assertEquals("The two students should be the same", s, s3);

        Student s4 = new Student(null, "s4", 4);
        s4.setId(3L);

        try {
            studentRepository.save(null);
            fail("Adding a null entity should have thrown a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected, do nothing.
        }

        try {
            studentRepository.save(s4);
            fail("Adding " + s4.toString() + " should have thrown a ValidatorException");
        }
        catch (ValidatorException e) {
            // Expected, do nothing.
        }
    }

    @Test(expected = ValidatorException.class)
    public void testSaveException() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student(null, "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", null, 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);
    }

    @Test
    public void testDelete() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);

        Optional<Student> o = studentRepository.delete(2L);

        Student s3 = o.get();

        assertEquals("The two students should be the same", s2, s3);

        try {
            studentRepository.delete(null);
            fail("Deleting a null entity should have thrown a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected, do nothing.
        }
    }

    @Test
    public void testUpdate() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);

        Optional<Student> o = studentRepository.update(s2);

        Student s3 = o.get();

        assertEquals("The two students should be the same", s2, s3);

        Student s4 = new Student("sn4", "s4", 4);
        s4.setId(3L);

        Optional<Student> o1 = studentRepository.update(s4);

        assertEquals("Both should be empty, because s4 is not in the repository", o1, Optional.empty());

        try {
            studentRepository.update(null);
            fail("Updating a null entity should have thrown a IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // Expected, do nothing.
        }
    }

    @Test(expected = ValidatorException.class)
    public void testUpdateException() throws Exception {
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", null, 2);
        s2.setId(2L);

        studentRepository.save(s);
        studentRepository.save(s2);

        studentRepository.update(s2);

        //It is obvious that this will throw an exception because we can not even save an invalid student in the
        //repository, so we should not even have this test method
        //i.e. The program does not even get to studentRepository.update(s2);
    }
}