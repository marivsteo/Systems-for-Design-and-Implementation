package labproblems.domain.validators;

import labproblems.domain.entities.Assignment;
import labproblems.domain.exceptions.ValidatorException;

public class AssignmentValidator implements Validator<Assignment> {
    /**
     * Checks if the attributes of a student are valid
     * @param entity an object of type Student
     * @throws ValidatorException if the attributes are no valid
     */
    @Override
    public void validate(Assignment entity) throws ValidatorException {
        if( entity.getName() == null || entity.getStudent() == null || entity.getProblem() == null || entity.getId() <= 0
                || entity.getName().equals(""))
            throw new ValidatorException("AssignmentValidator > validate: Not all of the fields are valid.");
    }
}
