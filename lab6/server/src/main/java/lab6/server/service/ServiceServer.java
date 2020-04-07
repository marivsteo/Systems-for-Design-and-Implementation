package lab6.server.service;

import lab6.common.Socket.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ServiceServer implements Service {
    private ExecutorService executorService;

    public ServiceServer(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public Future<String> sayHello(String name) {
        return executorService.submit(() -> name);
    }

//    @Override
//    public Future<String> sayBye(String name) {
//        return executorService.submit(() -> "Bye " + name);
//    }
}
