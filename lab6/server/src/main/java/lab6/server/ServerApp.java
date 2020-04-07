package lab6.server;

import lab6.common.Entities.Assignment;
import lab6.common.Entities.Problem;
import lab6.common.Entities.Student;
import lab6.common.Socket.Message;
import lab6.common.Socket.Service;
import lab6.common.validators.AssignmentValidator;
import lab6.common.validators.ProblemValidator;
import lab6.common.validators.StudentValidator;
import lab6.server.TcpServer.TcpServer;
import lab6.server.service.Controller;
import lab6.server.service.ServiceServer;
import lab6.server.services.AssignmentService;
import lab6.server.services.ProblemService;
import lab6.server.services.StudentService;
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
        try {
            System.out.println("server started");

            // postgresql
            ProblemValidator problemValidator = new ProblemValidator();
            AssignmentValidator assignmentValidator = new AssignmentValidator();
            StudentValidator studentValidator = new StudentValidator();

            ISortingRepository<Long, Student> studentRepository1 = new DatabaseStudentsRepository();
            ISortingRepository<Long, Problem> problemRepository1 = new DatabaseProblemsRepository();
            ISortingRepository<Long, Assignment> assignmentRepository1 = new DatabaseAssignmentsRepository();

            StudentService studentService = new StudentService(studentRepository1);
            ProblemService problemService = new ProblemService(problemRepository1);
            AssignmentService assignmentService = new AssignmentService(assignmentRepository1, assignmentValidator, studentService, problemService);

            try {
                ExecutorService executorService = Executors.newFixedThreadPool(
                        Runtime.getRuntime().availableProcessors()
                );
                Service helloService = new ServiceServer(executorService);

                TcpServer tcpServer = new TcpServer(executorService);
                Controller controller = new Controller(assignmentService,studentService,problemService);

                tcpServer.addHandler(Service.SEND_MESSAGE, (request) -> {
                    String name = request.getBody();

                    String commandResult = controller.runCommand(name);

                    Future<String> future = helloService.sendMessage(commandResult);
                    try {
                        String result = future.get();
                        return new Message("ok", result); //fixme: hardcoded str
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return new Message("error", e.getMessage());//fixme: hardcoded str
                    }

                });

//        tcpServer.addHandler(HelloService.SAY_BYE, (request) -> {
//            String name = request.getBody();
//            Future<String> future = helloService.sayBye(name);
//            try {
//                String result = future.get();
//                return new Message("ok", result);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//                return new Message("error", e.getMessage());
//            }
//        });

                tcpServer.startServer();

                executorService.shutdown();
            } catch (RuntimeException ex) {
                ex.printStackTrace();
            }
        } catch(Exception exception){
            exception.toString();
        }
    }
}
