package labproblems.service;

import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.ProblemValidator;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.domain.validators.ValidatorException;
import labproblems.repository.InMemoryRepository;
import labproblems.repository.Repository;
import labproblems.service.StudentService;
import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

/**
 * @author Marius
 * This class contains all the tests related to the ProblemService
 */
public class ProblemServiceTest {

    @Test
    public void testAddProblem() throws ValidatorException {
        ProblemValidator problemValidator = new ProblemValidator();
        Repository<Long, Problem> repository = new InMemoryRepository<>(problemValidator);
        ProblemService problemService = new ProblemService(repository);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        problemService.addProblem(1L, 1, "Write a C program");
        problemService.addProblem(2L,2,"Write a Python program");

        Problem p3 = repository.findOne(1L).get();

        assertEquals("The two problems should be the same", p, p3);
    }

    @Test(expected = ValidatorException.class)
    public void testAddException() throws Exception {
        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>(problemValidator);
        ProblemService problemService = new ProblemService(problemRepository);

        problemService.addProblem(1L, 1, "");
    }

    @Test
    public void testGetAll() throws Exception {
        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>(problemValidator);
        ProblemService problemService = new ProblemService(problemRepository);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        problemService.addProblem(1L, 1, "Write a C program");
        problemService.addProblem(2L,2,"Write a Python program");

        Set<Problem> problems = problemService.getAllProblems();

        assertTrue("The set should contain p", problems.contains(p));
        assertTrue("The set should contain p2", problems.contains(p2));
    }

    @Test
    public void testFilterProblemsByText() throws Exception {
        Validator<Problem> problemValidator = new ProblemValidator();
        Repository<Long, Problem> problemRepository = new InMemoryRepository<>(problemValidator);
        ProblemService problemService = new ProblemService(problemRepository);

        Problem p = new Problem(1, "Write a C program");
        p.setId(1L);
        Problem p2 = new Problem(2, "Write a Python program");
        p2.setId(2L);

        problemService.addProblem(1L, 1, "Write a C program");
        problemService.addProblem(2L,2,"Write a Python program");

        Set<Problem> problems = problemService.filterProblemsByText("Python");

        assertTrue("The set should contain p2", problems.contains(p2));
        assertFalse("The set should not contain p", problems.contains(p));
    }
}