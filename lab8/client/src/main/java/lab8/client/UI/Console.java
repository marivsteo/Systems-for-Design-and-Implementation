package lab8.client.UI;

import lab8.common.Services.AssignmentService;
import lab8.common.Services.ProblemService;
import lab8.common.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.ls.LSOutput;

import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class Console {
    @Autowired
    private StudentService studentService;

    @Autowired
    private ProblemService problemService;

    @Autowired
    private AssignmentService assignmentService;

    public void runConsole() {
        String choice = "";
        while (choice != "0") {
            System.out.println("Enter a choice:\n 0.Exit\n 1.Add student\n 2.Show all students\n 3.Show filtered students by name\n" +
                    " 4.Add problem\n 5.Show all problems\n 6.Show filtered problems by text\n 7.Delete student\n 8.Update student" +
                    "\n 9.Delete problem\n10.Update problem\n11.Show filtered student by serial number\n12.Add assignment" +
                    "\n13.Show all assignments\n14.Update assignment\n15.Delete assignment\n16.Show the students who failed an assignment" +
                    "\n17.Show the student along with their total points\n18.Show most assigned problem" +
                    "\n19.Show all assignments for the given student and problem along with the weighted average");
            Scanner keyboard = new Scanner(System.in);
            String name = keyboard.nextLine();
            String parameters = "";
            switch (name) {
                case "1":
                    System.out.println("Enter <id> long, <serial number> string, <name> string, <group> int: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "2":
                    this.studentService.getAllStudents().forEach(System.out::println);
                    break;
                case "3":
                    System.out.println("Enter <substring> String: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "4":
                    System.out.println("Enter <id> long, <number> int, <text> String: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "5":
                    break;
                case "6":
                    System.out.println("Enter <substring> String: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "7":
                    System.out.println("Enter <id> Long: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "8":
                    System.out.println("Enter <id> long, <new serial number> string, <new name> string, <new group> int: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "9":
                    System.out.println("Enter <id> Long: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "10":
                    System.out.println("Enter <id> long, <new number> int, <new text> String: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "11":
                    System.out.println("Enter <substring> String: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "12":
                    System.out.println("Enter <id> long, <name> String, <studentId> long, <problemId> long, <grade> float: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "13":
                    break;
                case "14":
                    System.out.println("Enter <id> long, <new name> String, <new studentId> long, <new problemId> long, <new grade> float: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "15":
                    System.out.println("Enter <id> Long: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "16":
                    break;
                case "17":
                    break;
                case "18":
                    break;
                case "19":
                    System.out.println("Enter <studentId> long, <problemId> long: ");
                    parameters = keyboard.nextLine();
                    name = name + ";" + parameters;
                    break;
                case "0":
                    System.exit(0);
                    break;
            }
        }
    }

    public void printAll() {
        this.studentService.getAllStudents().forEach(System.out::println);
        this.problemService.getAllProblems().forEach(System.out::println);
        this.assignmentService.getAllAssignments().forEach(System.out::println);
    }
}
