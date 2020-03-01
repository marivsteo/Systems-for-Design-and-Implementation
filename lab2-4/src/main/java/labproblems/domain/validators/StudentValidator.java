package labproblems.domain.validators;

import labproblems.domain.Student;

public class StudentValidator implements Validator<Student> {

    @Override
    public void validate(Student entity) throws ValidatorException {

        //TODO: replace this if with an Optional, using orElseThrow

//        entity.nameIsNull(e -> {throw new ValidatorException("Name is null");});
//
//        entity.serialNoIsNull(e -> {throw new ValidatorException("Serial Number is null");});
//
//        entity.groupIsNull(e -> {throw new ValidatorException("Group is null");});

        if(entity.getName() == null || entity.getSerialNumber() == null)
            throw new ValidatorException("One of the fields is null");

    }
}
