package labproblems;

import labproblems.domain.entities.Assignment;
import labproblems.domain.entities.Problem;
import labproblems.domain.entities.Student;
import labproblems.domain.validators.AssignmentValidator;
import labproblems.domain.validators.ProblemValidator;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.repository.*;
import labproblems.repository.fileRepositories.AssignmentFileRepository;
import labproblems.repository.fileRepositories.ProblemFileRepository;
import labproblems.repository.fileRepositories.StudentFileRepository;
import labproblems.repository.inMemoryRepository.InMemoryRepository;
import labproblems.repository.sortRepositories.DatabaseAssignmentsRepository;
import labproblems.repository.sortRepositories.DatabaseProblemsRepository;
import labproblems.repository.sortRepositories.DatabaseStudentsRepository;
import labproblems.repository.xmlRepositories.AssignmentXMLRepository;
import labproblems.repository.xmlRepositories.ProblemXMLRepository;
import labproblems.repository.xmlRepositories.StudentXMLRepository;
import labproblems.service.AssignmentService;
import labproblems.service.ProblemService;
import labproblems.service.StudentService;
import labproblems.ui.Console;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

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

        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a choice:\n 0.Exit\n 1.File repository\n 2.In-memory repository\n 3.XML repository\n" +
                " 4.Postgresql database");
        String choice = keyboard.nextLine();
        Validator<Problem> problemValidator;
        AssignmentValidator assignmentValidator;
        Repository<Long, Problem> problemRepository;
        Repository<Long, Assignment> assignmentRepository;
        ProblemService problemService;
        Validator<Student> studentValidator;
        Repository<Long, Student> studentRepository;
        StudentService studentService;
        AssignmentService assignmentService;
        Console console;
        switch(choice) {
            case "1":
                problemValidator = new ProblemValidator();
                assignmentValidator = new AssignmentValidator();
                problemRepository = new ProblemFileRepository("./data/problems");
                assignmentRepository = new AssignmentFileRepository("./data/assignments");
                problemService = new ProblemService(problemRepository);
                studentValidator = new StudentValidator();
                studentRepository = new StudentFileRepository("./data/students");
                studentService = new StudentService(studentRepository);
                assignmentService = new AssignmentService(assignmentRepository, assignmentValidator, studentService, problemService);
                console = new Console(studentService, problemService, assignmentService);
                console.runConsole();
                break;

            case "2":
                 //in-memory repo
                 studentValidator = new StudentValidator();
                 problemValidator = new ProblemValidator();
                 assignmentValidator = new AssignmentValidator();
                 studentRepository = new InMemoryRepository<>();
                 problemRepository = new InMemoryRepository<>();
                 assignmentRepository = new InMemoryRepository<>();
                 studentService = new StudentService(studentRepository);
                 problemService = new ProblemService(problemRepository);
                 assignmentService = new AssignmentService(assignmentRepository,assignmentValidator,studentService,problemService);
                 console = new Console(studentService,problemService,assignmentService);
                 console.runConsole();
                 break;
            case "3":
                //xml repo
                problemValidator = new ProblemValidator();
                assignmentValidator = new AssignmentValidator();
                problemRepository = new ProblemXMLRepository("./data/problems.xml");
                assignmentRepository = new AssignmentXMLRepository("./data/assignments.xml");
                problemService = new ProblemService(problemRepository);
                studentValidator = new StudentValidator();
                studentRepository = new StudentXMLRepository("./data/students.xml");
                studentService = new StudentService(studentRepository);
                assignmentService = new AssignmentService(assignmentRepository, assignmentValidator, studentService, problemService);
                console = new Console(studentService,problemService,assignmentService);
                console.runConsole();
                break;
            case "4":
                // postgresql
                problemValidator = new ProblemValidator();
                assignmentValidator = new AssignmentValidator();
                studentValidator = new StudentValidator();

                try {
                    studentRepository = new DatabaseStudentsRepository();
                    problemRepository = new DatabaseProblemsRepository();
                    assignmentRepository = new DatabaseAssignmentsRepository();

                    studentService = new StudentService(studentRepository);
                    problemService = new ProblemService(problemRepository);
                    assignmentService = new AssignmentService(assignmentRepository, assignmentValidator, studentService, problemService);

                    console = new Console(studentService, problemService, assignmentService);
                    console.runConsole();
                } catch (Exception exception){
                    System.out.println(exception.toString());
                }
                break;
        }
    }
}
