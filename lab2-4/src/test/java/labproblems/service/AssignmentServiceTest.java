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

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);

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

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L, 2L, 10);

        Set<Assignment> assignments = assignmentService.getAllAssignments();

        assertTrue("The set should contain a", assignments.contains(a));
        assertTrue("The set should contain a2", assignments.contains(a2));
    }

    @Test
    public void testFilterAssignmentsByStudent() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);

        Set<Assignment> assignments = assignmentService.filterAssignmentsByStudent(1L);

        assertTrue("The set should contain a", assignments.contains(a));
        assertFalse("The set should not contain a2", assignments.contains(a2));
    }

    @Test
    public void testFilterAssignmentsByName() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);

        Set<Assignment> assignments = assignmentService.filterAssignmentsByName("a1");

        assertTrue("The set should contain a", assignments.contains(a));
        assertFalse("The set should not contain a2", assignments.contains(a2));
    }

    @Test
    public void testDeleteAssignment() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);

        assignmentService.removeAssignment(1L);

        assertEquals("The repo should not contain a assignment with the ID 1L", Optional.empty(), assignmentRepository.findOne(1L));
        //assertFalse("The set should not contain s2", assignments.contains(s2));
    }

    /*
    @Test
    public void testUpdateAssignment() throws Exception {
        Validator<Assignment> assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);
        Assignment a3 = new Assignment("a3",3L,3L,10);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);
        assignmentService.addAssignment(3L,"a3",3L,3L,10);

        assignmentService.updateAssignment(1L, "a3", 3L, 3L, 9);

        assertEquals("The repo should have a assignment with name a3", "a3", assignmentRepository.findOne(1L).get().getName());
        assertEquals("The repo should have a assignment with student s3", 3L, assignmentRepository.findOne(1L).get().getStudent());
    }
*/

}
