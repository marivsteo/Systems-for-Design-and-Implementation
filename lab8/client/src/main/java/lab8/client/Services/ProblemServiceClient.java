package lab8.client.Services;

import lab8.common.Entities.Problem;
import lab8.common.Exceptions.ValidatorException;
import lab8.common.Services.ProblemService;
import lab8.common.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class ProblemServiceClient implements ProblemService{
    @Autowired
    private ProblemService problemService;

    @Override
    public void addProblem(Long _id, int _number, String _text) throws ValidatorException {
        problemService.addProblem(_id, _number, _text);
    }

    @Override
    public Iterable<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    @Override
    public Set<Problem> filterProblemsByText(String _substring) {
        return problemService.filterProblemsByText(_substring);
    }

    @Override
    public void removeProblem(Long _id) throws NoSuchElementException {
        problemService.removeProblem(_id);
    }

    @Override
    public void updateProblem(Long _id, int _number, String _text) throws Exception {
        problemService.updateProblem(_id, _number, _text);
    }

    @Override
    public Optional<Problem> findProblem(Long id) {
        return problemService.findProblem(id);
    }
}
