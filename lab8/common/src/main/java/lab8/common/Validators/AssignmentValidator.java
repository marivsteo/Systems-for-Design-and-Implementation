package lab8.common.Validators;

import lab8.common.Entities.Assignment;
import lab8.common.Exceptions.ValidatorException;

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
