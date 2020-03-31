package lab6.server;

import lab6.common.Controller.IController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ControllerServer implements IController {
    private ExecutorService executorService;

    public ControllerServer(ExecutorService _executorService){
        this.executorService = _executorService;
    }

    @Override
    public Future<String> send(String name) {
        return null;
    }

    @Override
    public Future<String> receive(String name) {
        return null;
    }
}
