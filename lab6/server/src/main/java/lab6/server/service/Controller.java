package lab6.server.service;

import lab6.common.Entities.Assignment;
import lab6.server.services.AssignmentService;
import lab6.server.services.ProblemService;
import lab6.server.services.StudentService;

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
        return commandResult;
    }
}
