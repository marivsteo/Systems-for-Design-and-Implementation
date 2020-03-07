package labproblems.ui;

import labproblems.domain.Problem;
import labproblems.domain.Student;
import labproblems.domain.validators.ValidatorException;
import labproblems.service.ProblemService;
import labproblems.service.StudentService;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.NoSuchElementException;
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
            System.out.println("Enter a choice:\n0.Exit\n1.Add student\n2.Show all students\n3.Show filtered students by name\n" +
                    "4.Add problem\n5.Show all problems\n6.Show filtered problems by text\n7.Delete student");
            String choice = keyboard.nextLine();
            studentService.addStudent(1L,"sn1","n1",1);
            switch(choice) {
                case "1":
                    System.out.println("Reading a student:");
                    addStudents();
                    break;
                case "2":
                    System.out.println("Listing all the students:");
                    printAllStudents();
                    break;
                case "3":
                    System.out.println("Enter the substring for the name:");
                    String parameter = keyboard.nextLine();
                    filterStudents(parameter);
                    break;
                case "4":
                    System.out.println("Reading a problem:");
                    addProblems();
                    break;
                case "5":
                    System.out.println("Listing all the problems:");
                    printAllProblems();
                    break;
                case "6":
                    System.out.println("Enter the substring for the text:");
                    String parameter2 = keyboard.nextLine();
                    filterProblems(parameter2);
                    break;
                case "7":
                    System.out.println("Enter the id of the student you want to delete:");
                    deleteStudent();
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
        System.out.println("Listing the students with the name containing '" + parameter +"':");
        Set<Student> students = studentService.filterStudentsByName(parameter);
        students.stream().forEach(System.out::println);
    }

    private void filterProblems(String parameter){
        System.out.println("Listing the problems with the text containing '" + parameter + "':");
        Set<Problem> problems = problemService.filterProblemsByText(parameter);
        problems.stream().forEach(System.out::println);
    }

    private void deleteStudent(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            studentService.removeStudent(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
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
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        // Declare and initialize the parameters for the service
        long id = 0L;
        String serialNumber = "";
        String name = "";
        int group = 0;
        // Read the parameters
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the serial number (String) >>");
            serialNumber = bufferRead.readLine();
            System.out.println("Enter the name (String) >>");
            name = bufferRead.readLine();
            System.out.println("Enter the group (integer) >>");
            group = NumberUtils.toInt(bufferRead.readLine());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            studentService.addStudent(id, serialNumber, name, group);
        } catch (ValidatorException e) {
            System.out.println(e.toString());
        }
    }

    private void addProblems(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        // Declare and initialize the parameters for the service
        Long id = 0L;
        int number = 0;
        String text = "default";
        // Read the parameters
        try{
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(),0L);
            System.out.println("Enter the number (integer) >>");
            number = NumberUtils.toInt(bufferRead.readLine(),0);
            System.out.println("Enter the text (String) >>");
            text = bufferRead.readLine();
        } catch (IOException exception){
            exception.printStackTrace();
        }
        try{
            problemService.addProblem(id,number,text);
        } catch (ValidatorException e){
            System.out.println(e.toString());
        }
    }
}
