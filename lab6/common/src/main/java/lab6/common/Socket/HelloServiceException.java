package lab6.common.Socket;

/**
 * Created by radu.
 */
public class HelloServiceException extends RuntimeException {

    public HelloServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public HelloServiceException(Throwable cause) {
        super(cause);
    }
}
