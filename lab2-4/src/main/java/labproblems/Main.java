package labproblems;

import labproblems.domain.Student;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.Validator;
import labproblems.repository.InMemoryRepository;
import labproblems.repository.Repository;
import labproblems.service.StudentService;
import labproblems.ui.Console;

/**
 * Created by Marius
 * <p>
 * <p>
 * Lab Problems App
 * </p>
 * <p>
 * <p>
 * I1:
 * </p>
 * <ul>
 * <li>F1: add student</li>
 * <li>F2: print all students</li>
 * <li>in memory repo</li>
 * </ul>
 * <p>
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
         Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);
         StudentService studentService = new StudentService(studentRepository);
         Console console = new Console(studentService);
         console.runConsole();

        //file repo
//        try {
//            System.out.println(new File(".").getCanonicalPath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //in file repo
//        Validator<Student> studentValidator = new StudentValidator();
//        Repository<Long, Student> studentRepository = new StudentFileRepository(studentValidator, "./data/students");
//        StudentService studentService = new StudentService(studentRepository);
//        Console console = new Console(studentService);
//        console.runConsole();
    }
}
