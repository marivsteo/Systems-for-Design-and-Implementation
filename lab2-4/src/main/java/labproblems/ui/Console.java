package labproblems.ui;

import labproblems.domain.entities.Assignment;
import labproblems.domain.entities.Problem;
import labproblems.domain.entities.Student;
import labproblems.domain.exceptions.ValidatorException;
import labproblems.service.AssignmentService;
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
    private AssignmentService assignmentService;

    public Console(StudentService _studentService, ProblemService _problemService, AssignmentService _assignmentService) {
        this.studentService = _studentService;
        this.problemService = _problemService;
        this.assignmentService = _assignmentService;
    }

    /**
     * <p>Method that is used for running the console</p>
     */
    public void runConsole() {
        while(true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter a choice:\n 0.Exit\n 1.Add student\n 2.Show all students\n 3.Show filtered students by name\n" +
                    " 4.Add problem\n 5.Show all problems\n 6.Show filtered problems by text\n 7.Delete student\n 8.Update student" +
                    "\n 9.Delete problem\n10.Update problem\n11.Show filtered student by serial number\n12.Add assignment" +
                    "\n13.Show all assignments\n14.Update assignment\n15.Delete assignment\n16.Show the students who failed an assignment" +
                    "\n17.Show the student along with their total points\n18.Show most assigned problem" +
                    "\n19.Show all assignments for the given student and problem along with the weighted average" );
            String choice = keyboard.nextLine();
            switch(choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    printAllStudents();
                    break;
                case "3":
                    filterStudentsByName();
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
                    deleteProblem();
                    break;
                case "10":
                    updateProblem();
                    break;
                case "11":
                    filterStudentBySerialNumber();
                    break;
                case "12":
                    addAssignment();
                    break;
                case "13":
                    printAllAssignments();
                    break;
                case "14":
                    updateAssignment();
                    break;
                case "15":
                    deleteAssignment();
                    break;
                case "16":
                    showStudentsWhoFailed();
                    break;
                case "17":
                    showStudentWithTotalPoints();
                    break;
                case "18":
                    getMostAssignedProblems();
                    break;
                case "19":
                    showAssignmentsfOfStudentWithProblem();
                    break;
                case "99":
                    studentService.addStudent(1L,"sn1","n1",1);
                    problemService.addProblem(1L,1,"pb1");
                    assignmentService.addAssignment(1L,"asdas",1L,1L,(float)5.69);
                    break;
                case "0":
                    System.exit(0);
            }
        }
    }

    private void showAssignmentsfOfStudentWithProblem(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long sid = 0L;
        Long pid = 0L;
        try {
            System.out.println("Enter the student id (Long) >>");
            sid = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the problem id (Long) >>");
            pid = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        Set<Assignment> assignments = this.assignmentService.getAssignmentsfOfStudentWithProblem(sid,pid);
        assignments.forEach(System.out::println);
        System.out.println("The weighted average for these assignments is: " + this.assignmentService.getAvgGradeOfAssignmentsfOfStudentWithProblem(sid,pid));
    }

    private void showStudentWithTotalPoints(){
        //this.assignmentService.getStudentsWithTheirAverage();
        this.assignmentService.getStudentWithTotalPoints();
    }

    private void showStudentsWhoFailed(){
        Set<Long> students = this.assignmentService.getStudentsWhoFailed();
        System.out.println("The students who failed at least one assignment are:");
        for(Long student: students){
            System.out.println("Student with studentId = " + student.toString());
        }
    }

    private void getMostAssignedProblems() {
        Long problemID = this.assignmentService.getProblemAssignedMostTimes();
        Problem problem = this.problemService.findProblem(problemID).get();
        System.out.println(problem);
    }

    private void filterStudentsByName() {
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

    private void filterStudentBySerialNumber(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String substring = "";
        try {
            System.out.println("Enter the substring for the serial number:");
            substring = bufferRead.readLine();
        } catch( IOException exception ){
            System.out.println(exception.toString());
        }
        System.out.println("Listing the students with the serial number containing '" + substring +"':");
        Set<Student> students = studentService.filterStudentsBySerialNumber(substring);
        students.stream().forEach(System.out::println);
    }

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
            exception.printStackTrace();
            //System.out.println(exception.toString());
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

    private void updateAssignment(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        long id = 0L;
        long studentId = 0L;
        long problemId = 0L;
        String name = "";
        float grade = (float)0;
        // Read the parameters
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the new name (String) >>");
            name = bufferRead.readLine();
            System.out.println("Enter the new studentId (Long) >>");
            studentId = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the new problemId (Long) >>");
            problemId = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the new grade (float) >>");
            grade = NumberUtils.toFloat(bufferRead.readLine(), (float)0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            assignmentService.updateAssignment(id,name,studentId,problemId,grade);
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
            assignmentService.synchronizeStudentsInAssignments(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private void deleteProblem(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            problemService.removeProblem(id);
            assignmentService.synchronizeProblemsInAssignments(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private void deleteAssignment(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            assignmentService.removeAssignment(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private void printAllStudents() {
        System.out.println("Listing all the students:");
        Iterable<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }

    private void printAllProblems(){
        System.out.println("Listing all the problems:");
        Iterable<Problem> problems = problemService.getAllProblems();
        problems.forEach(System.out::println);
    }

    private void printAllAssignments(){
        System.out.println("Listing all the assignments:");
        Iterable<Assignment> assignments = assignmentService.getAllAssignments();
        assignments.forEach(System.out::println);
    }

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

    private void addAssignment(){
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        // Declare and initialize the parameters for the service
        long id = 0L;
        long studentId = 0L;
        long problemId = 0L;
        String name = "";
        float grade = (float)0;
        // Read the parameters
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the name (String) >>");
            name = bufferRead.readLine();
            System.out.println("Enter the studentId (Long) >>");
            studentId = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the problemId (Long) >>");
            problemId = NumberUtils.toLong(bufferRead.readLine(), 0L);
            System.out.println("Enter the grade (float) >>");
            grade = NumberUtils.toFloat(bufferRead.readLine(), (float)0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            assignmentService.addAssignment(id,name,studentId,problemId,grade);
        } catch (ValidatorException e) {
            System.out.println(e.toString());
        }
    }
}
