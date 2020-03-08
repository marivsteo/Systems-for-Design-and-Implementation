package labproblems.service;

import labproblems.domain.Assignment;
import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.AssignmentValidator;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.domain.validators.ValidatorException;
import labproblems.repository.InMemoryRepository;
import labproblems.repository.Repository;
import org.junit.Test;

import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AssignmentServiceTest {
    
    @Test
    public void testAddAssignment() throws ValidatorException {
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(repository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        Assignment a = new Assignment("a1", s, p, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", s2, p2, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", s, p, 0);
        assignmentService.addAssignment(2L,"a2",s2,p2, 10);

        Assignment a3 = repository.findOne(1L).get();

        assertEquals("The two assignments should be the same", a, a3);
    }


    @Test(expected = ValidatorException.class)
    public void testAddException() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        assignmentService.addAssignment(1L, "a1", null, null, 0);
    }

    @Test
    public void testGetAll() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        Assignment a = new Assignment("a1", s, p, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", s2, p2, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", s, p, 0);
        assignmentService.addAssignment(2L,"a2",s2,p2, 10);

        Set<Assignment> assignments = assignmentService.getAllAssignments();

        assertTrue("The set should contain a", assignments.contains(a));
        assertTrue("The set should contain a2", assignments.contains(a2));
    }

    @Test
    public void testFilterAssignmentsByStudent() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        Assignment a = new Assignment("a1", s, p, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", s2, p2, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", s, p, 0);
        assignmentService.addAssignment(2L,"a2",s2,p2, 10);

        Set<Assignment> assignments = assignmentService.filterAssignmentsByStudent(s);

        assertTrue("The set should contain a", assignments.contains(a));
        assertFalse("The set should not contain a2", assignments.contains(a2));
    }

    @Test
    public void testFilterAssignmentsByName() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        Assignment a = new Assignment("a1", s, p, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", s2, p2, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", s, p, 0);
        assignmentService.addAssignment(2L,"a2",s2,p2, 10);

        Set<Assignment> assignments = assignmentService.filterAssignmentsByName("a1");

        assertTrue("The set should contain a", assignments.contains(a));
        assertFalse("The set should not contain a2", assignments.contains(a2));
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        Assignment a = new Assignment("a1", s, p, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", s2, p2, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", s, p, 0);
        assignmentService.addAssignment(2L,"a2",s2,p2, 10);

        assignmentService.removeAssignment(1L);

        assertEquals("The repo should not contain a assignment with the ID 1L", Optional.empty(), assignmentRepository.findOne(1L));
        //assertFalse("The set should not contain s2", assignments.contains(s2));
    }

    @Test
    public void testUpdateAssignment() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Student s = new Student("sn1", "s1", 1);
        s.setId(1L);
        Student s2 = new Student("sn2", "s2", 2);
        s2.setId(2L);
        Student s3 = new Student("sn3", "s3", 3);
        s3.setId(3L);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);
        Problem p3 = new Problem(3, "Write a Java program");
        p3.setId(3L);

        Assignment a = new Assignment("a1", s, p, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", s2, p2, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", s, p, 0);
        assignmentService.addAssignment(2L,"a2",s2,p2, 10);

        assignmentService.updateAssignment(1L, "a3", s3, p3, 9);

        assertEquals("The repo should have a assignment with name a3", "a3", assignmentRepository.findOne(1L).get().getName());
        assertEquals("The repo should have a assignment with student s3", s3, assignmentRepository.findOne(1L).get().getStudent());
    }
}
