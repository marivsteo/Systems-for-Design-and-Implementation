package lab6.common.Controller;

import java.util.concurrent.Future;

public interface IController {

    //TODO here we put strings representing method calls (the ones that the client sends and the server receives)
    String SAY_HELLO = "sayHello";

    Future<String> send(String name);

    Future<String> receive(String name);

}
