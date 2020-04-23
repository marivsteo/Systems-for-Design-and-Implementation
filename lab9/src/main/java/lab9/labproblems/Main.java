package lab9.labproblems;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import lab9.labproblems.ui.Console;

public class Main {
    public static void main(String[] args) {
        System.out.println("hello");

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "lab9.labproblems"
                );

        context.getBean(Console.class).runConsole();

        System.out.println("bye");
    }
}
