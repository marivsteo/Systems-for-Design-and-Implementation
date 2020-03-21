package labproblems.domain.validators;

import labproblems.domain.exceptions.ValidatorException;

/**
 * @author Marius
 * Simple interface for a validator
 * @param <T> a generic type object
 */
public interface Validator<T> {
    /**
     * Validates an object
     * @param entity an object of type T
     * @throws ValidatorException if the object is not valid
     */
    void validate(T entity) throws ValidatorException;
}
