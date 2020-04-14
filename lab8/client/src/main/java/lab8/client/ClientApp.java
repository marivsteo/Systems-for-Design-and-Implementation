package lab8.client;

import lab8.client.UI.Console;
import lab8.common.Services.StudentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ClientApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "lab8.client.Config"
                );

        Console console = context.getBean(Console.class);

        console.printAll();

        console.runConsole();

        //StudentService studentService = context.getBean(StudentService.class);
    }
}
