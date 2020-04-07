package lab6.server.service;

import lab6.common.Entities.Assignment;
import lab6.common.Entities.Problem;
import lab6.common.Entities.Student;
import lab6.server.services.AssignmentService;
import lab6.server.services.ProblemService;
import lab6.server.services.StudentService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class Controller {
    private AssignmentService assignmentService;
    private StudentService studentService;
    private ProblemService problemService;

    public Controller(AssignmentService assignmentService, StudentService studentService, ProblemService problemService){
        this.assignmentService = assignmentService;
        this.studentService = studentService;
        this.problemService = problemService;
    }

    public String runCommand(String command){
        String commandResult = "";
        String[] parts = command.split(";");

        if(parts[0].equals("13")) {
            System.out.println("yay");
            Iterable<Assignment> iterable = assignmentService.getAllAssignments();

            for(Assignment assignment: iterable) {
                commandResult = commandResult + assignment.toString() + ";";
            }
        }

        if (parts[0].equals("2")) {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {}
            System.out.println("You will get all the students");
            Iterable<Student> students = studentService.getAllStudents();

            for(Student student: students) {
                commandResult = commandResult + student.toString() + ";";
            }
        }

        if (parts[0].equals("3")) {
            System.out.println("You will get all the filtered students by name");

            Set<Student> students = studentService.filterStudentsByName(parts[1]);

            for(Student student: students) {
                commandResult = commandResult + student.toString() + ";";
            }
        }

        if (parts[0].equals("5")) {
            System.out.println("You will get all the problems");
            Iterable<Problem> problems = problemService.getAllProblems();

            for(Problem problem: problems) {
                commandResult = commandResult + problem.toString() + ";";
            }
        }

        if (parts[0].equals("6")) {
            System.out.println("You will get all the problems filtered by text");
            //Iterable<Problem> problems = problemService.getAllProblems();
            Set<Problem> problems = problemService.filterProblemsByText(parts[1]);

            for(Problem problem: problems) {
                commandResult = commandResult + problem.toString() + ";";
            }
        }

        if (parts[0].equals("7")) {
            System.out.println("You will delete a student");
            //Iterable<Student> students = studentService.getAllStudents();

            Student student = studentService.findStudent(Long.parseLong(parts[1])).get();

            studentService.removeStudent(Long.parseLong(parts[1]));
            assignmentService.synchronizeStudentsInAssignments(Long.parseLong(parts[1]));

            commandResult = commandResult + student.toString() + ";";
        }

        if (parts[0].equals("8")) {
            System.out.println("You will update a student");
            //Iterable<Student> students = studentService.getAllStudents();

            try {
                studentService.updateStudent(Long.parseLong(parts[1]), parts[2], parts[3], Integer.parseInt(parts[4]));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Student student = studentService.findStudent(Long.parseLong(parts[1])).get();

            commandResult = commandResult + student.toString() + ";";
        }

        if (parts[0].equals("9")) {
            System.out.println("You will delete a problem");
            Problem problem = problemService.findProblem(Long.parseLong(parts[1])).get();

            problemService.removeProblem(Long.parseLong(parts[1]));
            assignmentService.synchronizeProblemsInAssignments(Long.parseLong(parts[1]));

            commandResult = commandResult + problem.toString() + ";";
        }

        if (parts[0].equals("10")) {
            System.out.println("You will update a problem");

            try {
                problemService.updateProblem(Long.parseLong(parts[1]), Integer.parseInt(parts[2]), parts[3]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Problem problem = problemService.findProblem(Long.parseLong(parts[1])).get();

            commandResult = commandResult + problem.toString() + ";";
        }

        if (parts[0].equals("11")) {
            System.out.println("You will get all the filtered students by serial number");

            Set<Student> students = studentService.filterStudentsBySerialNumber(parts[1]);

            for(Student student: students) {
                commandResult = commandResult + student.toString() + ";";
            }
        }

        if(parts[0].equals("12")) {
            System.out.println("you will add an assignment");

            Assignment assignment = new Assignment(parts[2], Long.parseLong(parts[3]), Long.parseLong(parts[4]), Float.parseFloat(parts[5]));
            assignment.setId(Long.parseLong(parts[1]));

            assignmentService.addAssignment(Long.parseLong(parts[1]), parts[2], Long.parseLong(parts[3]), Long.parseLong(parts[4]), Float.parseFloat(parts[5]));


            commandResult = commandResult + assignment.toString() + ";";
        }

        if(parts[0].equals("14")) {
            System.out.println("you will update an assignment");

            try {
                assignmentService.updateAssignment(Long.parseLong(parts[1]), parts[2], Long.parseLong(parts[3]), Long.parseLong(parts[4]), Float.parseFloat(parts[5]));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(parts[0].equals("15")) {
            System.out.println("you will delete an assignment");

            assignmentService.removeAssignment(Long.parseLong(parts[1]));
        }

        if(parts[0].equals("16")) {
            System.out.println("you will get the students that failed an assignment");

            Set<Long> studentIds = assignmentService.getStudentsWhoFailed();

            for(Long id: studentIds) {
                Student student = studentService.findStudent(id).get();
                commandResult = commandResult + student.toString() + ";";
            }
        }

        if(parts[0].equals("17")) {
            System.out.println("you will get the students with their total points");

            assignmentService.getStudentWithTotalPoints();
        }

        if(parts[0].equals("18")) {
            System.out.println("you will get the problem that was assigned most times");

            Long id = assignmentService.getProblemAssignedMostTimes();

            Problem problem = problemService.findProblem(id).get();

            commandResult = commandResult + problem.toString() + ";";
        }

        if(parts[0].equals("19")) {
            System.out.println("you will get the problem and the student along with the average");

            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            Long sid = Long.parseLong(parts[1]);
            Long pid = Long.parseLong(parts[2]);

            Set<Assignment> assignments = this.assignmentService.getAssignmentsfOfStudentWithProblem(sid,pid);
            //assignments.forEach(System.out::println);
            for(Assignment assignment: assignments) {
                commandResult = commandResult + assignment.toString() + ";";
            }

            commandResult += "The weighted average for these assignments is: " + this.assignmentService.getAvgGradeOfAssignmentsfOfStudentWithProblem(sid,pid);
        }

        if (parts[0].equals("1")) {
            System.out.println("You will add a student");
            //Iterable<Student> students = studentService.getAllStudents();

            Student student = new Student(parts[2], parts[3], Integer.parseInt(parts[4]));
            student.setId(Long.parseLong(parts[1]));

            studentService.addStudent(Long.parseLong(parts[1]), parts[2], parts[3], Integer.parseInt(parts[4]));

            commandResult = commandResult + student.toString() + ";";
        }

        if (parts[0].equals("4")) {
            System.out.println("You will add a problem");
            //Iterable<Problem> problems = problemService.getAllProblems();

            Problem problem = new Problem(Integer.parseInt(parts[2]), parts[3]);
            problem.setId(Long.parseLong(parts[1]));

            problemService.addProblem(Long.parseLong(parts[1]), Integer.parseInt(parts[2]), parts[3]);

            commandResult = commandResult + problem.toString() + ";";
        }

        return commandResult;
    }
}
