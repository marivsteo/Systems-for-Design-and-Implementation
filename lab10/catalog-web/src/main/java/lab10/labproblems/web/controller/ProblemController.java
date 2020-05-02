package lab10.labproblems.web.controller;

import lab10.labproblems.core.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab10.labproblems.core.model.entities.Problem;
import lab10.labproblems.core.service.ProblemService;
import lab10.labproblems.web.converter.ProblemConverter;
import lab10.labproblems.web.dto.ProblemDto;
import lab10.labproblems.web.dto.ProblemsDto;

import java.util.List;


@RestController
public class ProblemController {
    public static final Logger log= LoggerFactory.getLogger(ProblemController.class);

    @Autowired
    private ProblemService problemService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private ProblemConverter problemConverter;


    @RequestMapping(value = "/problems", method = RequestMethod.GET)
    ProblemsDto getProblems() {
        log.trace("getAllProblems - method entered");

        return new ProblemsDto(problemConverter
                .convertModelsToDtos(problemService.getAllProblems()));

    }

    @RequestMapping(value = "/problems", method = RequestMethod.POST)
    ProblemDto saveProblem(@RequestBody ProblemDto problemDto) {
        log.trace("getAllProblems - method entered");
        return problemConverter.convertModelToDto(problemService.saveProblem(
                problemConverter.convertDtoToModel(problemDto)
        ));
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.PUT)
    ProblemDto updateProblem(@PathVariable Long id,
                             @RequestBody ProblemDto problemDto) {
        log.trace("getAllProblems - method entered");
        return problemConverter.convertModelToDto( problemService.updateProblem(
                problemConverter.convertDtoToModel(problemDto)));
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteProblem(@PathVariable Long id){
        log.trace("getAllProblems - method entered");

        assignmentService.synchronizeProblemsInAssignments(id);
        problemService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/problems/{id}", method = RequestMethod.GET)
    ProblemDto getProblem(@PathVariable Long id) {
        log.trace("getAllProblems - method entered");
        Problem problem = problemService.findProblem(id).get();
        return problemConverter.convertModelToDto(problem);
    }

    @RequestMapping(value = "/problems/filter/{filter}", method = RequestMethod.GET)
    ProblemsDto filterProblemsByText(@PathVariable String filter) {
        log.trace("getAllProblems - method entered");

        return new ProblemsDto(problemConverter
                .convertModelsToDtos(problemService.filterProblemsByText(filter)));
    }
}
