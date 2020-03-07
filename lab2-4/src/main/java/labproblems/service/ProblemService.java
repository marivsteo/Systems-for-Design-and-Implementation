package labproblems.service;

import labproblems.domain.Problem;
import labproblems.domain.validators.ProblemValidator;
import labproblems.domain.validators.ValidatorException;
import labproblems.repository.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Andreas
 * Service class for the Problem entity.
 */
public class ProblemService {
    private Repository<Long, Problem> repository;
    public ProblemService(Repository<Long,Problem> _repository) { this.repository = _repository;};

    /**
     * The method adds a problem with the given attributes.
     * @param _id the id of the problem which is to be added
     * @param _number the number of the problem which is to be added
     * @param _text the text of the problem which is to be added
     * @throws ValidatorException if the given attributes are invalid
     */
    public void addProblem(Long _id, int _number, String _text) throws ValidatorException{
        try {
            Problem problem = new Problem(_number, _text);
            problem.setId(_id);
            ProblemValidator validator = new ProblemValidator();
            validator.validate(problem);
            this.repository.save(problem);
        } catch( ValidatorException exception){
            throw exception;
        }
    }

    /**
     * The method collects all the problems in a Set.
     * @return a Set containing all problems in the repository
     */
    public Set<Problem> getAllProblems(){
        Iterable<Problem> problems = this.repository.findAll();
        return StreamSupport.stream(problems.spliterator(),false).collect(Collectors.toSet());
    }

    /**
     * The method returns all problems whose text contain the given string.
     * @param _substring the string we look for in the text
     * @return a Set of problems whose texts contain the string _substring
     */
    public Set<Problem> filterProblemsByText(String _substring){
        Iterable<Problem> problems = this.repository.findAll();
        Set<Problem> filteredProblems = new HashSet<>();
        problems.forEach(filteredProblems::add);
        // Remove the problems that do not have the given substring in their text
        filteredProblems.removeIf(problem -> !problem.getText().contains(_substring));
        return filteredProblems;
    }
}
