package lab9.labproblems.ui;

import lab9.labproblems.model.entities.Assignment;
import lab9.labproblems.model.entities.Problem;
import lab9.labproblems.model.entities.Student;
import lab9.labproblems.model.exceptions.ValidatorException;
import lab9.labproblems.service.AssignmentService;
import lab9.labproblems.service.ProblemService;
import lab9.labproblems.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.math.NumberUtils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class Console {
    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProblemService problemService;

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
                case "0":
                    System.exit(0);
            }
        }
    }

    private void updateAssignment() {
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
            Assignment a = assignmentService.findAssignment(id).get();
            a.setGrade(grade);
            a.setName(name);
            a.setStudent(studentId);
            a.setProblem(problemId);
            assignmentService.updateAssignment(a);
        } catch( Exception exception ){
            System.out.println(exception.toString());
        }

    }

    private void updateProblem() {
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
            Problem p = problemService.findProblem(id).get();
            p.setNumber(number);
            p.setText(text);
            problemService.updateProblem(p);
        } catch( Exception exception ){
            System.out.println(exception.toString());
        }
    }

    private void showAssignmentsfOfStudentWithProblem() {
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

    private void getMostAssignedProblems() {
        Long problemID = this.assignmentService.getProblemAssignedMostTimes();
        Problem problem = this.problemService.findProblem(problemID).get();
        System.out.println(problem);
    }

    private void showStudentWithTotalPoints() {
        this.assignmentService.getStudentWithTotalPoints();
    }

    private void showStudentsWhoFailed() {
        Set<Long> students = this.assignmentService.getStudentsWhoFailed();
        System.out.println("The students who failed at least one assignment are:");
        for(Long student: students){
            System.out.println("Student with studentId = " + student.toString());
        }
    }

    private void deleteAssignment() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            assignmentService.deleteById(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private void printAllAssignments() {
        System.out.println("Listing all the assignments:");
        Iterable<Assignment> assignments = assignmentService.getAllAssignments();
        assignments.forEach(System.out::println);
    }

    private void filterStudentBySerialNumber() {
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

    private void addAssignment() {
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
            assignmentService.saveAssignment(new Assignment(name,studentId,problemId,grade));
        } catch (ValidatorException e) {
            System.out.println(e.toString());
        }
    }

    private void updateStudent() {
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
            Student s = studentService.findStudent(id).get();
            s.setSerialNumber(serialNumber);
            s.setName(name);
            s.setGroupNumber(group);
            studentService.updateStudent(s);
        } catch( Exception exception){
            exception.printStackTrace();
            //System.out.println(exception.toString());
        }
    }

    private void deleteProblem() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            problemService.deleteById(id);
            assignmentService.synchronizeProblemsInAssignments(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private void deleteStudent() {
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        Long id = 0L;
        try {
            System.out.println("Enter the id (Long) >>");
            id = NumberUtils.toLong(bufferRead.readLine(), 0L);
        }catch(IOException exception){
            System.out.println(exception.toString());
        }
        try{
            studentService.deleteById(id);
            assignmentService.synchronizeStudentsInAssignments(id);
        }catch (NoSuchElementException exception){
            System.out.println(exception.toString());
        }
    }

    private void filterProblems() {
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

    private void printAllProblems() {
        System.out.println("Listing all the problems:");
        Iterable<Problem> problems = problemService.getAllProblems();
        problems.forEach(System.out::println);
    }

    private void addProblem() {
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
            problemService.saveProblem(new Problem(number,text));
        } catch (ValidatorException e){
            System.out.println(e.toString());
        }
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

    private void addStudent() {
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
            studentService.saveStudent(new Student(serialNumber, name, group));
        } catch (ValidatorException e) {
            System.out.println(e.toString());
        }
    }

    private void printAllStudents() {
        System.out.println("Listing all the students:");
        List<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }

}
