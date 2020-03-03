package labproblems.domain.validators;

import labproblems.domain.Student;

/**
 * @author Marius
 * A Validator class for the student class, implementing the Validator interface
 */
public class StudentValidator implements Validator<Student> {

    /**
     * Checks if the name and serial number of a student are valid
     * @param entity an object of type T
     * @throws ValidatorException if the students name or serial number is null
     */
    @Override
    public void validate(Student entity) throws ValidatorException {

        //TODO: replace this if with an Optional, using orElseThrow

//        entity.nameIsNull(e -> {throw new ValidatorException("Name is null");});
//
//        entity.serialNoIsNull(e -> {throw new ValidatorException("Serial Number is null");});
//
//        entity.groupIsNull(e -> {throw new ValidatorException("Group is null");});
        try {
            assert (entity.getName() != null);
            assert (entity.getSerialNumber() != null);
        } catch (AssertionError ignored) {
            throw new ValidatorException("One of the fields is null");
        }

    }
}
