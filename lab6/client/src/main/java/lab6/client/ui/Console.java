package lab6.client.ui;

import lab6.common.Controller.IController;
import lab6.common.Socket.Message;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {
    private IController controller;

    public Console(IController controller) {
        this.controller = controller;
    }

    public void runConsole() {
        while (true) {
            Scanner keyboard = new Scanner(System.in);
            System.out.println("Enter a choice:\n 0.Exit\n 1.Add student\n 2.Show all students\n 3.Show filtered students by name\n" +
                    " 4.Add problem\n 5.Show all problems\n 6.Show filtered problems by text\n 7.Delete student\n 8.Update student" +
                    "\n 9.Delete problem\n10.Update problem\n11.Show filtered student by serial number\n12.Add assignment" +
                    "\n13.Show all assignments\n14.Update assignment\n15.Delete assignment\n16.Show the students who failed an assignment" +
                    "\n17.Show the student along with their total points\n18.Show most assigned problem" +
                    "\n19.Show all assignments for the given student and problem along with the weighted average");
            String choice = keyboard.nextLine();
            String parameters = "";
            switch(choice) {
                case "1":
                    System.out.println("Enter <id> long, <serial number> string, <name> string, <group> int: ");
                    parameters = keyboard.nextLine();
                    break;
                case "2":
                    break;
                case "3":
                    System.out.println("Enter <substring> String: ");
                    parameters = keyboard.nextLine();
                    break;
                case "4":
                    System.out.println("Enter <id> long, <number> int, <text> String: ");
                    parameters = keyboard.nextLine();
                    break;
                case "5":
                    break;
                case "6":
                    System.out.println("Enter <substring> String: ");
                    parameters = keyboard.nextLine();
                    break;
                case "7":
                    System.out.println("Enter <id> Long: ");
                    parameters = keyboard.nextLine();
                    break;
                case "8":
                    System.out.println("Enter <id> long, <new serial number> string, <new name> string, <new group> int: ");
                    parameters = keyboard.nextLine();
                    break;
                case "9":
                    System.out.println("Enter <id> Long: ");
                    parameters = keyboard.nextLine();
                    break;
                case "10":
                    System.out.println("Enter <id> long, <new number> int, <new text> String: ");
                    parameters = keyboard.nextLine();
                    break;
                case "11":
                    System.out.println("Enter <substring> String: ");
                    parameters = keyboard.nextLine();
                    break;
                case "12":
                    System.out.println("Enter <id> long, <name> String, <studentId> long, <problemId> long, <grade> float: ");
                    parameters = keyboard.nextLine();
                    break;
                case "13":
                    break;
                case "14":
                    System.out.println("Enter <id> long, <new name> String, <new studentId> long, <new problemId> long, <new grade> float: ");
                    parameters = keyboard.nextLine();
                    break;
                case "15":
                    System.out.println("Enter <id> Long: ");
                    parameters = keyboard.nextLine();
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
                    break;
                case "0":
                    System.exit(0);
            }
            Message message = new Message(choice,parameters);
            System.out.println(message);
        }
    }
}
