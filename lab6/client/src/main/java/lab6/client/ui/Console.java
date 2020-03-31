package lab6.client.ui;

import lab6.common.Controller.IController;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Console {
    private IController controller;

    public Console(IController controller) {
        this.controller = controller;
    }

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
//            switch(choice) {
//                case "1":
//                    addStudent();
//                    break;
//                case "2":
//                    printAllStudents();
//                    break;
//                case "3":
//                    filterStudentsByName();
//                    break;
//                case "4":
//                    addProblem();
//                    break;
//                case "5":
//                    printAllProblems();
//                    break;
//                case "6":
//                    filterProblems();
//                    break;
//                case "7":
//                    deleteStudent();
//                    break;
//                case "8":
//                    updateStudent();
//                    break;
//                case "9":
//                    deleteProblem();
//                    break;
//                case "10":
//                    updateProblem();
//                    break;
//                case "11":
//                    filterStudentBySerialNumber();
//                    break;
//                case "12":
//                    addAssignment();
//                    break;
//                case "13":
//                    printAllAssignments();
//                    break;
//                case "14":
//                    updateAssignment();
//                    break;
//                case "15":
//                    deleteAssignment();
//                    break;
//                case "16":
//                    showStudentsWhoFailed();
//                    break;
//                case "17":
//                    showStudentWithTotalPoints();
//                    break;
//                case "18":
//                    getMostAssignedProblems();
//                    break;
//                case "19":
//                    showAssignmentsfOfStudentWithProblem();
//                    break;
//                case "99":
//                    studentService.addStudent(1L,"sn1","n1",1);
//                    problemService.addProblem(1L,1,"pb1");
//                    assignmentService.addAssignment(1L,"asdas",1L,1L,(float)5.69);
//                    break;
//                case "0":
//                    System.exit(0);
//            }
//        }
    }

    }
}
