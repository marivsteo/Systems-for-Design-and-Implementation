package labproblems.domain.validators;

/**
 * @author Marius
 */
public class ProblemsException extends RuntimeException{

    public ProblemsException(String message) {super(message);}

    public ProblemsException(String message, Throwable cause) {super(message, cause);}

    public ProblemsException(Throwable cause) {super(cause);}
}
