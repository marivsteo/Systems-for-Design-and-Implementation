package lab9.labproblems.service;

import lab9.labproblems.model.entities.Problem;
import lab9.labproblems.repository.ProblemRepository;
import lab9.labproblems.repository.ProblemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public Optional<Problem> findProblem(Long id) {
        log.trace("findProblem - method entered");

        return problemRepository.findById(id);
    }

    public List<Problem> getAllProblems() {
        log.trace("getAllProblems - method entered");
        return problemRepository.findAll();
    }

    public void saveProblem(Problem problem) {
        log.trace("saveProblem - method entered");

        problemRepository.save(problem);
    }

    @Transactional
    public void updateProblem(Problem problem) {
        log.trace("updateProblem - method entered: problem={}", problem);
        problemRepository.findById(problem.getId())
                .ifPresent(p -> {
                    p.setNumber(problem.getNumber());
                    p.setText(problem.getText());
                    log.debug("updateProblem - updated: p={}", p);
                });
        log.trace("updateProblem - method finished");
    }

    public void deleteById(Long id) {
        log.trace("deleteProblem - method entered");

        problemRepository.deleteById(id);
    }

    public Set<Problem> filterProblemsByText(String _substring) {
        log.trace("filterProblemsByText - method entered");

        Iterable<Problem> problems = problemRepository.findAll();
        Set<Problem> filteredProblems = new HashSet<>();
        problems.forEach(filteredProblems::add);
        // Remove the problems that do not have the given substring in their name
        filteredProblems.removeIf(problem -> !problem.getText().contains(_substring));
        return filteredProblems;
    }

}
