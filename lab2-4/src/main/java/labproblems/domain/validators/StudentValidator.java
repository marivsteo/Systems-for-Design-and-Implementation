package labproblems.domain.validators;

import labproblems.domain.entities.Student;
import labproblems.domain.exceptions.ValidatorException;

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
        if( entity.getName() == null || entity.getSerialNumber() == null || entity.getId() <= 0 || entity.getGroup() <= 0
                || entity.getName().equals("") || entity.getSerialNumber().equals("") )
            throw new ValidatorException("StudentValidator > validate: Not all of the fields are valid.");
    }
}
