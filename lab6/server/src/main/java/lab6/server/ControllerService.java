package lab6.server;

import lab6.common.Controller.IController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ControllerService implements IController {
    private ExecutorService executorService;

    public ControllerService(ExecutorService _executorService){
        this.executorService = _executorService;
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
