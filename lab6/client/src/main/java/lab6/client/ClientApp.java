package lab6.client;

import lab6.client.tcp.TcpClient;
import lab6.client.ui.Console;
import lab6.common.Controller.IController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        IController controller = new ControllerClient(executorService, tcpClient);
        Console console = new Console(controller);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}
