package labproblems.domain.validators;

/**
 * @author Marius
 */

public class ValidatorException extends ProblemsException {

    public ValidatorException(String message) {
        super(message);
    }

    public ValidatorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidatorException(Throwable cause) {
        super(cause);
    }
}
