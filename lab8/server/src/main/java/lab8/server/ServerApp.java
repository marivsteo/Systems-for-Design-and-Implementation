package lab8.server;

import lab8.common.Entities.Student;
import lab8.server.Repository.DatabaseAssignmentsRepository;
import lab8.server.Repository.DatabaseProblemsRepository;
import lab8.server.Repository.DatabaseStudentsRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class ServerApp {
    public static void main(String[] args) {
        System.out.println("server starting");

        AnnotationConfigApplicationContext context=
                new AnnotationConfigApplicationContext(
                        "lab8.server.Config"
                );

//        DatabaseStudentsRepository studentRepository =
//             (DatabaseStudentsRepository) context.getBean(DatabaseStudentsRepository.class);
//
//        Student student = new Student("sn3", "s3", 99);
//        student.setId(3L);
//
//        try {
//            studentRepository.loadData();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        Iterable<Student> all = studentRepository.findAll();
//        all.forEach(System.out::println);
//
//        studentRepository.save(student);
//
//        Iterable<Student> all2 = studentRepository.findAll();
//        all2.forEach(System.out::println);


//        DatabaseStudentsRepository studentRepository =
//                (DatabaseStudentsRepository) context.getBean(DatabaseStudentsRepository.class);
//
//        DatabaseProblemsRepository problemRepository =
//                (DatabaseProblemsRepository) context.getBean(DatabaseProblemsRepository.class);
//
//        DatabaseAssignmentsRepository assignmentRepository =
//                (DatabaseAssignmentsRepository) context.getBean(DatabaseAssignmentsRepository.class);

    }
}
