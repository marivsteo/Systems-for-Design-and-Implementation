package labproblems;

import labproblems.domain.Assignment;
import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.AssignmentValidator;
import labproblems.domain.validators.ProblemValidator;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.repository.*;
import labproblems.service.AssignmentService;
import labproblems.service.ProblemService;
import labproblems.service.StudentService;
import labproblems.ui.Console;

import java.io.File;
import java.io.IOException;

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
         try {
            System.out.println(new File(".").getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //in file repo
        Validator<Problem> problemValidator = new ProblemValidator();
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        Repository<Long, Problem> problemRepository = new ProblemFileRepository("./data/problems");
        Repository<Long, Assignment> assignmentRepository = new AssignmentFileRepository("./data/assignments");
        ProblemService problemService = new ProblemService(problemRepository);
        Validator<Student> studentValidator = new StudentValidator();
        Repository<Long, Student> studentRepository = new StudentFileRepository("./data/students");
        StudentService studentService = new StudentService(studentRepository);
        AssignmentService assignmentService = new AssignmentService(assignmentRepository,assignmentValidator,studentService,problemService);
        Console console = new Console(studentService,problemService,assignmentService);
        console.runConsole();
         /*
         /in-memory repo
         Validator<Student> studentValidator = new StudentValidator();
         Validator<Problem> problemValidator = new ProblemValidator();
         AssignmentValidator assignmentValidator = new AssignmentValidator();
         Repository<Long, Student> studentRepository = new InMemoryRepository<>();
         Repository<Long, Problem> problemRepository = new InMemoryRepository<>();
         Repository<Long, Assignment> assignmentRepository = new InMemoryRepository<>();
         StudentService studentService = new StudentService(studentRepository);
         ProblemService problemService = new ProblemService(problemRepository);
         AssignmentService assignmentService = new AssignmentService(assignmentRepository,assignmentValidator,studentService,problemService);
         Console console = new Console(studentService,problemService,assignmentService);
         console.runConsole();
          */
    }
}
