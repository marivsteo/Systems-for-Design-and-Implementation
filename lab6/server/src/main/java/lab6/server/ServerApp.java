package lab6.server;

import lab6.common.Entities.Assignment;
import lab6.common.Entities.Problem;
import lab6.common.Entities.Student;
import lab6.common.Socket.Message;
import lab6.common.validators.AssignmentValidator;
import lab6.common.validators.ProblemValidator;
import lab6.common.validators.StudentValidator;
import lab6.server.TcpServer.TcpServer;
import lab6.server.service.AssignmentService;
import lab6.server.service.ProblemService;
import lab6.server.service.StudentService;
import lab6.server.sortRepositories.DatabaseAssignmentsRepository;
import lab6.server.sortRepositories.DatabaseProblemsRepository;
import lab6.server.sortRepositories.DatabaseStudentsRepository;
import lab6.server.sortRepositories.ISortingRepository;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerApp {
    public static void main(String[] args) {

        System.out.println("Server started.");

        // postgresql
        ProblemValidator problemValidator = new ProblemValidator();
        AssignmentValidator assignmentValidator = new AssignmentValidator();
        StudentValidator studentValidator = new StudentValidator();

        try {

            ISortingRepository<Long, Student> studentRepository1 = new DatabaseStudentsRepository();
            ISortingRepository<Long, Problem> problemRepository1 = new DatabaseProblemsRepository();
            ISortingRepository<Long, Assignment> assignmentRepository1 = new DatabaseAssignmentsRepository();

            StudentService studentService = new StudentService(studentRepository1);
            ProblemService problemService = new ProblemService(problemRepository1);
            AssignmentService assignmentService = new AssignmentService(assignmentRepository1, assignmentValidator, studentService, problemService);

            ExecutorService executorService = Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors()
            );

            ControllerServer controller = new ControllerServer(executorService);
            //Controller controller = new Controller(studentService,problemService,assignmentService);

            TcpServer tcpServer = new TcpServer(executorService);

            tcpServer.addHandler(ControllerServer.SAY_HELLO, (request) -> {
                String name = request.getBody();
                Future<String> future = ControllerServer.send(name);
                try {
                    String result = future.get();
                    return new Message("ok", result); //fixme: hardcoded str
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                    return new Message("error", e.getMessage());//fixme: hardcoded str
                }

            });

            tcpServer.startServer();

            executorService.shutdown();

        } catch (Exception exception){
            System.out.println(exception.toString());
        }
    }
}
