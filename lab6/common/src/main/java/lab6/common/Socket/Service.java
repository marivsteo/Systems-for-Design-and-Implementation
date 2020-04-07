package lab6.common.Socket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Created by radu.
 */
public interface Service {
    String SEND_MESSAGE = "sendMessage";
    String SAY_BYE = "sayBye";

    Future<String> sendMessage(String name);

//    Future<String> sayBye(String name);


}
