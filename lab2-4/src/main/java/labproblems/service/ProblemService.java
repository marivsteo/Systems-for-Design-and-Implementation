package labproblems.service;

import labproblems.domain.Problem;
import labproblems.domain.validators.ValidatorException;
import labproblems.repository.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProblemService {
    private Repository<Long, Problem> repository;
    public ProblemService(Repository<Long,Problem> _repository) { this.repository = _repository;};

    public void addProblem(Problem _problem) throws ValidatorException{
        this.repository.save(_problem);
    }

    public Set<Problem> getAllProblems(){
        Iterable<Problem> problems = this.repository.findAll();
        return StreamSupport.stream(problems.spliterator(),false).collect(Collectors.toSet());
    }

    public Set<Problem> filterProblemsByText(String s){
        Iterable<Problem> problems = this.repository.findAll();

        Set<Problem> filteredProblems = new HashSet<>();
        problems.forEach(filteredProblems::add);
        filteredProblems.removeIf(problem -> !problem.getText().contains(s));

        return filteredProblems;
    }

}
