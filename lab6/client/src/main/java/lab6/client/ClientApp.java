package lab6.client;

import lab6.client.service.ServiceClient;
import lab6.client.tcp.TcpClient;
import lab6.client.ui.Console;
import lab6.common.Controller.IController;
import lab6.common.Socket.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientApp {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        TcpClient tcpClient = new TcpClient();
        Service helloService = new ServiceClient(executorService, tcpClient);
        Console console = new Console(helloService);
        console.runConsole();

        executorService.shutdown();

        System.out.println("bye client");
    }
}