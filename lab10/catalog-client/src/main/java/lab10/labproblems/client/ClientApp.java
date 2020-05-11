package lab10.labproblems.client;

import lab10.labproblems.core.model.entities.Assignment;
import lab10.labproblems.core.model.entities.Problem;
import lab10.labproblems.core.model.entities.Student;
import lab10.labproblems.core.model.exceptions.ValidatorException;
import lab10.labproblems.web.converter.AssignmentConverter;
import lab10.labproblems.web.converter.ProblemConverter;
import lab10.labproblems.web.converter.StudentConverter;
import lab10.labproblems.web.dto.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

public class ClientApp {
    public static final String URL = "http://localhost:8080/api/";

    static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(
                    "lab10.labproblems.client.config"
            );

    public static RestTemplate restTemplate = context.getBean(RestTemplate.class);

    public static void main(String[] args) {


        while(true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter a choice:\n 0.Exit\n 1.Add student\n 2.Show all students\n 3.Show filtered students by name\n" +
                    " 4.Add problem\n 5.Show all problems\n 6.Show filtered problems by text\n 7.Delete student\n 8.Update student" +
                    "\n 9.Delete problem\n10.Update problem\n11.Show filtered student by serial number\n12.Add assignment" +
                    "\n13.Show all assignments\n14.Update assignment\n15.Delete assignment\n" );
            String choice = keyboard.nextLine();
            switch(choice) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    printAllStudents(restTemplate);
                    break;
                case "3":
                    filterStudentsByName();
                    break;
                case "4":
                    addProblem();
                    break;
                case "5":
                    printAllProblems(restTemplate);
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
                    printAllAssignments(restTemplate);
                    break;
                case "14":
                    updateAssignment();
                    break;
                case "15":
                    deleteAssignment();
                    break;
                case "0":
                    System.exit(0);
            }
        }
    }

    private static void updateAssignment() {
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
            System.out.println("update:");
            AssignmentDto assignmentdto = restTemplate.getForObject(URL + "assignments/{id}", AssignmentDto.class, id);
            AssignmentConverter converter = new AssignmentConverter();
            Assignment assignment = converter.convertDtoToModel(assignmentdto);
            assignment.setName(name);
            assignment.setStudent(studentId);
            assignment.setProblem(problemId);
            assignment.setGrade(grade);
            restTemplate.put(URL + "assignments/{id}", assignment, id);
            printAllAssignments(restTemplate);
        } catch( Exception exception ){
            System.out.println(exception.toString());
        }

    }

    private static void updateProblem() {
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
            System.out.println("update:");
            ProblemDto problemDto = restTemplate.getForObject(URL + "problems/{id}", ProblemDto.class, id);
            ProblemConverter converter = new ProblemConverter();
            Problem problem = converter.convertDtoToModel(problemDto);
            problem.setNumber(number);
            problem.setText(text);
            restTemplate.put(URL + "problems/{id}", problem, id);
            printAllProblems(restTemplate);
        } catch( Exception exception ){
            System.out.println(exception.toString());
        }
    }


    private static void deleteAssignment() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            System.out.println("delete: ");
            restTemplate.delete(URL + "assignments/{id}", id);
            printAllAssignments(restTemplate);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private static void printAllAssignments(RestTemplate restTemplate) {
        AssignmentsDto allAssignments = restTemplate.getForObject(URL + "assignments", AssignmentsDto.class);
        for (AssignmentDto a: allAssignments.getAssignments()) {
            System.out.println(a);
        }
    }

    private static void filterStudentBySerialNumber() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String substring = "";
        try {
            System.out.println("Enter the substring for the serial number:");
            substring = bufferRead.readLine();
        } catch( IOException exception ){
            System.out.println(exception.toString());
        }
        System.out.println("Listing the students with the serial number containing '" + substring +"':");
        StudentsDto allStudents = restTemplate.getForObject(URL + "students/filterSerialNumber/{filter}", StudentsDto.class, substring);
        System.out.println(allStudents);
    }

    private static void addAssignment() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        // Declare and initialize the parameters for the service
        long studentId = 0L;
        long problemId = 0L;
        String name = "";
        float grade = (float)0;
        // Read the parameters
        try {
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
            AssignmentDto savedAssignment = restTemplate.postForObject(
                    URL + "assignments",
                    new AssignmentDto(name, studentId, problemId, grade),
                    AssignmentDto.class);
            System.out.println("savedAssignment: " + savedAssignment);
        }
        catch (ValidatorException e) {
            System.out.println(e.toString());
        }
    }

    private static void updateStudent() {
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
            System.out.println("update:");
            StudentDto studentdto = restTemplate.getForObject(URL + "students/{id}", StudentDto.class, id);
            StudentConverter converter = new StudentConverter();
            Student student = converter.convertDtoToModel(studentdto);
            student.setName(name);
            student.setSerialNumber(serialNumber);
            student.setGroupNumber(group);
            restTemplate.put(URL + "students/{id}", student, id);
            printAllStudents(restTemplate);
        } catch( Exception exception){
            exception.printStackTrace();
            //System.out.println(exception.toString());
        }
    }

    private static void deleteProblem() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            System.out.println("delete: ");
            restTemplate.delete(URL + "problems/{id}", id);
            printAllProblems(restTemplate);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private static void deleteStudent() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            System.out.println("delete: ");
            restTemplate.delete(URL + "students/{id}", id);
            printAllStudents(restTemplate);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private static void filterProblems() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String substring = "";
        try {
            System.out.println("Enter the substring for the text:");
            substring = bufferRead.readLine();
        } catch( IOException exception ){
            System.out.println(exception.toString());
        }
        System.out.println("Listing the problems with the text containing '" + substring + "':");
        ProblemsDto allProblems = restTemplate.getForObject(URL + "problems/filter/{filter}", ProblemsDto.class, substring);
        System.out.println(allProblems);
    }

    private static void printAllProblems(RestTemplate restTemplate) {
        ProblemsDto allProblems = restTemplate.getForObject(URL + "problems", ProblemsDto.class);
        for (ProblemDto p: allProblems.getProblems()) {
            System.out.println(p);
        }
    }

    private static void addProblem() {
        System.out.println("Reading a problem:");
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        // Declare and initialize the parameters for the service
        int number = 0;
        String text = "default";
        // Read the parameters
        try{
            System.out.println("Enter the number (integer) >>");
            number = NumberUtils.toInt(bufferRead.readLine(),0);
            System.out.println("Enter the text (String) >>");
            text = bufferRead.readLine();
        } catch (IOException exception){
            exception.printStackTrace();
        }
        try{
            ProblemDto savedProblem = restTemplate.postForObject(
                    URL + "problems",
                    new ProblemDto(number, text),
                    ProblemDto.class);
            System.out.println("savedProblem: " + savedProblem);
        } catch (ValidatorException e){
            System.out.println(e.toString());
        }
    }

    private static void filterStudentsByName() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        String substring = "";
        try {
            System.out.println("Enter the substring for the name:");
            substring = bufferRead.readLine();
        } catch( IOException exception ){
            System.out.println(exception.toString());
        }
        System.out.println("Listing the students with the name containing '" + substring +"':");
        StudentsDto allStudents = restTemplate.getForObject(URL + "students/filterName/{filter}", StudentsDto.class, substring);
        System.out.println(allStudents);
    }

    private static void addStudent() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        // Declare and initialize the parameters for the service
        String serialNumber = "";
        String name = "";
        int group = 0;
        // Read the parameters
        try {
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
            StudentDto savedStudent = restTemplate.postForObject(
                    URL + "students",
                    new StudentDto(serialNumber, name, group),
                    StudentDto.class);
            System.out.println("savedStudent: " + savedStudent);
        } catch (ValidatorException e) {
            System.out.println(e.toString());
        }
    }


    private static void printAllStudents(RestTemplate restTemplate) {
        StudentsDto allStudents = restTemplate.getForObject(URL + "students", StudentsDto.class);
        for (StudentDto s: allStudents.getStudents()) {
            System.out.println(s);
        }
    }
}
