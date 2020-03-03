package labproblems.ui;

import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.ValidatorException;
import labproblems.service.ProblemService;
import labproblems.service.StudentService;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @author Marius
 * Console class, part of the UI
 */
public class Console {
    private StudentService studentService;
    private ProblemService problemService;

    public Console(StudentService _studentService, ProblemService _problemService) {
        this.studentService = _studentService;
        this.problemService = _problemService;
    }

    /**
     * <p>Method that is used for running the console</p>
     */
    public void runConsole() {
        while(true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter a choice:\n0.Exit\n1.Add student\n2.Show all students\n3.Show filtered students\n4.Add problem\n5.Show all problems\n6.Show filtered problems ");
            String choice = keyboard.nextLine();
            switch(choice) {
                case "1":
                    System.out.println("Enter a student:");
                    addStudents();
                    break;
                case "2":
                    System.out.println("List of all students:");
                    printAllStudents();
                    break;
                case "3":
                    System.out.println("List of filtered students:");
                    System.out.println("Enter the parameters:");
                    String parameter = keyboard.nextLine();
                    filterStudents(parameter);
                    break;
                case "4":
                    System.out.println("Enter problem:");
                    addProblems();
                    break;
                case "5":
                    System.out.println("List of all problems:");
                    printAllProblems();
                    break;
                case "6":
                    System.out.println("List of filtered problems:");
                    System.out.println("Enter the parameters:");
                    String parameter2 = keyboard.nextLine();
                    filterProblems(parameter2);
                    break;
                case "0":
                    System.exit(0);
            }
        }
    }

    /** <p>
     * Method used for filtering students based on name
     * @param parameter
     *          given input from keyboard to search all students with name containing it
     *          </p>
     */
    private void filterStudents(String parameter) {
        System.out.println("filtered students (name containing '" + parameter +"):");
        Set<Student> students = studentService.filterStudentsByName(parameter);
        students.stream().forEach(System.out::println);
    }

    private void filterProblems(String parameter){
        System.out.println("filtere problems (text containing '" + parameter + "'):");
        Set<Problem> problems = problemService.filterProblemsByText(parameter);
        problems.stream().forEach(System.out::println);
    }

    private void printAllStudents() {
        Set<Student> students = studentService.getAllStudents();
        students.stream().forEach(System.out::println);
    }

    private void printAllProblems(){
        Set<Problem> problems = problemService.getAllProblems();
        problems.stream().forEach(System.out::println);
    }

    /**
     * If a student is not null, it adds it
     */
    private void addStudents() {
            Student student = readStudent();
            if (student == null || student.getId() < 0) {
                System.out.println("Invalid field.");
            }
            else {
                try {
                    studentService.addStudent(student);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            }
    }

    private void addProblems(){
        Problem problem = readProblem();
        if( problem == null || problem.getId() < 0 ){
            System.out.println("Invalid field.");
        }
        else{
            try{
                problemService.addProblem(problem);
            } catch (ValidatorException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Function that reads a student from a BufferedReader
     * @return student read from the BufferedReader
     */
    private Student readStudent() {
        System.out.println("Read student {id,serialNumber, name, group}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            //Long id = Long.parseLong(Optional.ofNullable(bufferRead.readLine()).orElseGet(() -> "-1"));// ...
            Long id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();
            int group = NumberUtils.toInt(bufferRead.readLine());// ...

            Student student = new Student(serialNumber, name, group);
            student.setId(id);

            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private Problem readProblem(){
        System.out.println("Read problem {id,number,text}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));

        try{
            Long id = NumberUtils.toLong(bufferRead.readLine(),0L);
            int number = NumberUtils.toInt(bufferRead.readLine(),0);
            String text = bufferRead.readLine();

            Problem problem = new Problem(number,text);
            problem.setId(id);

            return problem;
        } catch (IOException exception){
            exception.printStackTrace();
        }

        return null;
    }
}
