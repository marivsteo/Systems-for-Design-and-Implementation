package labproblems.domain.validators;

/**
 * @author Marius
 * @param <T>
 */
public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}
