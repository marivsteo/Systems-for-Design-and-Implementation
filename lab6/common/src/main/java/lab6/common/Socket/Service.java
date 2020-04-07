package lab6.common.Socket;

import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public interface Service {
    String SAY_HELLO = "sayHello";
    String SAY_BYE = "sayBye";

    Future<String> sayHello(String name);

//    Future<String> sayBye(String name);


}
