package lab6.common.validators;

import lab6.common.Entities.Problem;
import lab6.common.exceptions.ValidatorException;

/**
 * @author Andreas
 * A Validator class for the problem class, implementing the Validator interface
 */
public class ProblemValidator implements Validator<Problem> {
    /**
     * Checks if the attributes of a problem are valid
     * @param entity an object of type Problem
     * @throws ValidatorException if the attributes are not valid
     */
    @Override
    public void validate(Problem entity) throws ValidatorException {
        if( entity.getId() <= 0 || entity.getNumber() <= 0 || entity.getText() == null || entity.getText().equals(""))
            throw new ValidatorException("ProblemValidator > validate:  Not all of the fields are valid.");
    }
}
