package lab9.labproblems.model.validators;

import lab9.labproblems.model.entities.Student;
import lab9.labproblems.model.exceptions.ValidatorException;

/**
 * @author Marius
 * A Validator class for the student class, implementing the Validator interface
 */
public class StudentValidator implements Validator<Student> {
    /**
     * Checks if the attributes of a student are valid
     * @param entity an object of type Student
     * @throws ValidatorException if the attributes are no valid
     */
    @Override
    public void validate(Student entity) throws ValidatorException {
        if( entity.getName() == null || entity.getSerialNumber() == null || entity.getId() <= 0 || entity.getGroupNumber() <= 0
                || entity.getName().equals("") || entity.getSerialNumber().equals("") )
            throw new ValidatorException("StudentValidator > validate: Not all of the fields are valid.");
    }
}
