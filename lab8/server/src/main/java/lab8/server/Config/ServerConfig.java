package lab8.server.Config;

import lab8.common.Entities.Assignment;
import lab8.common.Services.AssignmentService;
import lab8.common.Services.ProblemService;
import lab8.common.Services.StudentService;
import lab8.server.Service.AssignmentServiceImpl;
import lab8.server.Service.ProblemServiceImpl;
import lab8.server.Service.StudentServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

@ComponentScan({"lab8.server.Repository",
        "lab8.server.Service"})
@Configuration
public class ServerConfig {
    @Bean
    RmiServiceExporter rmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("StudentService");
        rmiServiceExporter.setServiceInterface(StudentService.class);
        rmiServiceExporter.setService(studentService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporter2() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ProblemService");
        rmiServiceExporter.setServiceInterface(ProblemService.class);
        rmiServiceExporter.setService(problemService());
        return rmiServiceExporter;
    }

    @Bean
    RmiServiceExporter rmiServiceExporter3() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("AssignmentService");
        rmiServiceExporter.setServiceInterface(AssignmentService.class);
        rmiServiceExporter.setService(assignmentService());
        return rmiServiceExporter;
    }

    @Bean
    AssignmentService assignmentService() {
        return new AssignmentServiceImpl();
    }

    @Bean
    ProblemService problemService() {
        return new ProblemServiceImpl();
    }

    @Bean
    StudentService studentService() {
        return new StudentServiceImpl();
    }


}
