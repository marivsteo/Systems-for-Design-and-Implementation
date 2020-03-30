package lab6.server.TcpServer;


import lab6.common.Socket.HelloServiceException;
import lab6.common.Socket.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

/**
 * Created by radu.
 */
public class TcpServer {
    private ExecutorService executorService;
    private Map<String, UnaryOperator<Message>> methodHandlers;

    public TcpServer(ExecutorService executorService) {
        this.methodHandlers = new HashMap<>();
        this.executorService = executorService;
    }

    public void addHandler(String methodName, UnaryOperator<Message> handler) {
        methodHandlers.put(methodName, handler);
    }

    public void startServer() {
        try (var serverSocket = new ServerSocket(Message.PORT)) {
            while (true) {
                Socket client = serverSocket.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            throw new HelloServiceException("error connecting clients", e);
        }
    }

    private class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket client) {
            this.socket = client;
        }

        @Override
        public void run() {
            try (var is = socket.getInputStream();
                 var os = socket.getOutputStream()) {
                Message request = new Message();
                request.readFrom(is);
                System.out.println("received request: " + request);

//                System.out.println(request.getHeader());
//                System.out.println(request.getBody());
                //efvpsekojg

                //given the request header? (method name) and body (method args),
                // execute a handler and get the result of the method execution (of type message)

                Message response = methodHandlers.get(request.getHeader())
                        .apply(request);
//                Message response = new Message();
                response.writeTo(os);

            } catch (IOException e) {
                throw new HelloServiceException("error processing client", e);
            }
        }
    }
}