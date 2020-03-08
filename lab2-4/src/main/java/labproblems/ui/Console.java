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
                    "4.Add problem\n5.Show all problems\n6.Show filtered problems by text\n7.Delete student\n8.Update student" +
                    "\n9.Delete problem\n10.Update problem");
            String choice = keyboard.nextLine();
            switch(choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    printAllStudents();
                    break;
                case "3":
                    filterStudents();
                    break;
                case "4":
                    addProblem();
                    break;
                case "5":
                    printAllProblems();
                    break;
                case "6":
                    filterProblems();
                    break;
                case "7":
                    deleteStudent();
                    break;
                case "8":
                    updateStudent();
                    break;
                case "9":

                case "10":
                    updateProblem();
                    break;
                case "99":
                    studentService.addStudent(1L,"sn1","n1",1);
                    problemService.addProblem(1L,1,"pb1");
                    break;
                case "0":
                    System.exit(0);
            }
        }
    }

    /** <p>
     * Method used for filtering students based on name
     *  </p>
     */
    private void filterStudents() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String substring = "";
        try {
            System.out.println("Enter the substring for the name:");
            substring = bufferRead.readLine();
        } catch( IOException exception ){
            System.out.println(exception.toString());
        }
        System.out.println("Listing the students with the name containing '" + substring +"':");
        Set<Student> students = studentService.filterStudentsByName(substring);
        students.stream().forEach(System.out::println);
    }

    /** <p>
     * Method used for filtering students based on name
     *  </p>
     */
    private void filterProblems(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String substring = "";
        try {
            System.out.println("Enter the substring for the text:");
            substring = bufferRead.readLine();
        } catch( IOException exception ){
            System.out.println(exception.toString());
        }
        System.out.println("Listing the problems with the text containing '" + substring + "':");
        Set<Problem> problems = problemService.filterProblemsByText(substring);
        problems.stream().forEach(System.out::println);
    }

    private void updateStudent(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        String serialNumber = "";
        String name = "";
        int group = 0;
        try {
            System.out.println("Enter the id of the student you want to update:");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the new serial number (String) >>");
            serialNumber = bufferRead.readLine();
            System.out.println("Enter the new name (String) >>");
            name = bufferRead.readLine();
            System.out.println("Enter the new group (integer) >>");
            group = NumberUtils.toInt(bufferRead.readLine());
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            studentService.updateStudent(id,serialNumber,name,group);
        } catch( Exception exception){
            System.out.println(exception.toString());
        }
    }

    private void updateProblem(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        int number = 0;
        String text = "";
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the new number (integer) >>");
            number = NumberUtils.toInt(bufferRead.readLine(),0);
            System.out.println("Enter the new text (String) >>");
            text = bufferRead.readLine();
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try {
            problemService.updateProblem(id, number, text);
        } catch( Exception exception ){
            System.out.println(exception.toString());
        }
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
        System.out.println("Listing all the students:");
        Set<Student> students = studentService.getAllStudents();
        students.stream().forEach(System.out::println);
    }

    private void printAllProblems(){
        System.out.println("Listing all the problems:");
        Set<Problem> problems = problemService.getAllProblems();
        problems.stream().forEach(System.out::println);
    }

    /**
     * If a student is not null, it adds it
     */
    private void addStudent() {
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

    private void addProblem(){
        System.out.println("Reading a problem:");
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
