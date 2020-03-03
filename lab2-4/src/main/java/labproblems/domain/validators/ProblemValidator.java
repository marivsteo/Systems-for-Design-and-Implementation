package labproblems.domain.validators;

import labproblems.domain.Problem;

public class ProblemValidator implements Validator<Problem> {
    @Override
    public void validate(Problem entity) throws ValidatorException {
        try{
            assert(entity.getNumber() != 0);
            assert(entity.getText()!=null);
        }catch (AssertionError error){
            throw new ValidatorException("Problem fields not valid.");
        }

    }
}
