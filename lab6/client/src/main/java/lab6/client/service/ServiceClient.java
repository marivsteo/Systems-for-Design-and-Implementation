package lab6.client.service;

import lab6.client.tcp.TcpClient;
import lab6.common.Socket.Message;
import lab6.common.Socket.Service;

import java.lang.reflect.Member;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public class ServiceClient implements Service {
    private ExecutorService executorService;
    private TcpClient tcpClient;

    public ServiceClient(ExecutorService executorService, TcpClient tcpClient) {
        this.executorService = executorService;
        this.tcpClient = tcpClient;
    }

    @Override
    public CompletableFuture<String> sendMessage(String name) {
//        return executorService.submit(() -> {
//            //create a request
//            //send request to server
//            //get response
//
//            Message request = new Message(Service.SEND_MESSAGE, name);
//            //System.out.println("sending request: "+request);
//            Message response = tcpClient.sendAndReceive(request);
//            //System.out.println("received response: "+response);
//
//            return response.getBody();
//        });

        return CompletableFuture.supplyAsync(() ->  {
            Message request = new Message(Service.SEND_MESSAGE, name);
            Message response = tcpClient.sendAndReceive(request);

            return response.getBody();
        });
    }
}
