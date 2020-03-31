package lab6.client;

import lab6.client.tcp.TcpClient;
import lab6.common.Controller.IController;
import lab6.common.Socket.Message;

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

        return executorService.submit(() -> {
            //create a request
            //send request to server
            //get response

            Message request = new Message(IController.SAY_HELLO, name);
            System.out.println("sending request: "+request);
            Message response = tcpClient.sendAndReceive(request);
            System.out.println("received response: "+response);

            return response.getBody();
        });
    }

    @Override
    public Future<String> receive(String name) {
        return null;
    }
}
