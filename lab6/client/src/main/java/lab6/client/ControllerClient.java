package lab6.client;

import lab6.client.tcp.TcpClient;
import lab6.common.Controller.IController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ControllerClient implements IController {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ControllerClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public Future<String> send(String name) {
        return null;
    }

    @Override
    public Future<String> recieve(String name) {
        return null;
    }
}
