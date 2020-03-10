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

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AssignmentServiceTest {
    
    @Test
    public void testAddAssignment() throws ValidatorException {
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        studentService.addStudent(1L,"SN1","N1",1);
        studentService.addStudent(2L,"SN2","N2",2);
        problemService.addProblem(1L,1,"text");
        problemService.addProblem(2L,2,"text");
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);

        Assignment a3 = repository.findOne(1L).get();

        assertEquals("The two assignments should be the same", a, a3);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddException() throws Exception {
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

        assignmentService.addAssignment(1L, "a1", null, null, 0);
    }


    @Test
    public void testGetAll() throws Exception {
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        studentService.addStudent(1L,"SN1","N1",1);
        studentService.addStudent(2L,"SN2","N2",2);
        problemService.addProblem(1L,1,"text");
        problemService.addProblem(2L,2,"text");
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

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
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        studentService.addStudent(1L,"SN1","N1",1);
        studentService.addStudent(2L,"SN2","N2",2);
        problemService.addProblem(1L,1,"text");
        problemService.addProblem(2L,2,"text");
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

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
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        studentService.addStudent(1L,"SN1","N1",1);
        studentService.addStudent(2L,"SN2","N2",2);
        problemService.addProblem(1L,1,"text");
        problemService.addProblem(2L,2,"text");
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

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
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        studentService.addStudent(1L,"SN1","N1",1);
        studentService.addStudent(2L,"SN2","N2",2);
        problemService.addProblem(1L,1,"text");
        problemService.addProblem(2L,2,"text");
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);

        assignmentService.removeAssignment(1L);

        assertEquals("The repo should not contain a assignment with the ID 1L", Optional.empty(), repository.findOne(1L));
        //assertFalse("The set should not contain s2", assignments.contains(s2));
    }


    @Test
    public void testUpdateAssignment() throws Exception {
        Repository<Long, Student> studentRepository = new InMemoryRepository<>();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
        StudentService studentService = new StudentService(studentRepository);
        ProblemService problemService = new ProblemService(problemRepository);
        studentService.addStudent(1L,"SN1","N1",1);
        studentService.addStudent(2L,"SN2","N2",2);
        studentService.addStudent(3L,"SN3","N3",3);
        problemService.addProblem(1L,1,"text");
        problemService.addProblem(2L,2,"text");
        problemService.addProblem(3L,3,"text");
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Assignment> repository = new InMemoryRepository<>();
        AssignmentService assignmentService = new AssignmentService(repository,assignmentValidator,studentService,problemService);

        Assignment a = new Assignment("a1", 1L, 1L, 0);
        a.setId(1L);
        Assignment a2 = new Assignment("a2", 2L, 2L, 10);
        a2.setId(2L);
        Assignment a3 = new Assignment("a3",3L,3L,10);
        a3.setId(3L);

        assignmentService.addAssignment(1L, "a1", 1L, 1L, 0);
        assignmentService.addAssignment(2L,"a2",2L,2L, 10);
        assignmentService.addAssignment(3L,"a3",3L,3L,10);

        assignmentService.updateAssignment(1L, "a3", 3L, 3L, 9);

        assertEquals("The repo should have a assignment with name a3", "a3", repository.findOne(1L).get().getName());
    }

}
