package lab6.common.Controller;

import java.util.concurrent.Future;

public interface IController {

    Future<String> send(String name);

    Future<String> recieve(String name);

}
