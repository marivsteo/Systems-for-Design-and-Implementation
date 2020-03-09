package labproblems;

import labproblems.domain.Assignment;
import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.AssignmentValidator;
import labproblems.domain.validators.ProblemValidator;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.repository.InMemoryRepository;
import labproblems.repository.Repository;
import labproblems.service.AssignmentService;
import labproblems.service.ProblemService;
import labproblems.service.StudentService;
import labproblems.ui.Console;

/**
 * Created by Marius
 *
 * <p>
 * Lab Problems App
 * </p>
 *
 * <p>
 * I1:
 * </p>
 * <ul>
 * <li>F1: add student</li>
 * <li>F2: print all students</li>
 * <li>in memory repo</li>
 * </ul>
 *
 * <p>
 * I2:
 * </p>
 * <ul>
 * <li>in file repo</li>
 * <li>F3: print students whose name contain a given string</li>
 * </ul>
 */

public class Main {
    public static void main(String args[]) {
        //in-memory repo
         Validator<Student> studentValidator = new StudentValidator();
         Validator<Problem> problemValidator = new ProblemValidator();
         Validator<Assignment> assignmentValidator = new AssignmentValidator();
         Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);
         Repository<Long, Problem> problemRepository = new InMemoryRepository<>(problemValidator);
         Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>(assignmentValidator);
         StudentService studentService = new StudentService(studentRepository);
         ProblemService problemService = new ProblemService(problemRepository);
         AssignmentService assignmentService = new AssignmentService(assignmentRepository);
         Console console = new Console(studentService,problemService,assignmentService);
         console.runConsole();
    }
}
