package lab8.common.Exceptions;

/**
 * @author Marius
 * Main Exception class in the project, extending RuntimeException, later used for the ValidatorException class
 */
public class ProblemsException extends RuntimeException{

    public ProblemsException(String message) {super(message);}

    public ProblemsException(String message, Throwable cause) {super(message, cause);}

    public ProblemsException(Throwable cause) {super(cause);}
}
