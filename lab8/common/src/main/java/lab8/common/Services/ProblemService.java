package lab8.common.Services;

import lab8.common.Entities.Problem;
import lab8.common.Exceptions.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public interface ProblemService {
    public void addProblem(Long _id, int _number, String _text) throws ValidatorException;

    public Iterable<Problem> getAllProblems();

    public Set<Problem> filterProblemsByText(String _substring);

    public void removeProblem(Long _id) throws NoSuchElementException;

    public void updateProblem(Long _id, int _number, String _text) throws Exception;

    public Optional<Problem> findProblem(Long id);

}
