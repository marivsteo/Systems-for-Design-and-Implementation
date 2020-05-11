package lab11.labproblems.web.controller;

import lab11.labproblems.web.converter.AssignmentConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab11.labproblems.core.model.entities.Assignment;
import lab11.labproblems.core.service.AssignmentService;
import lab11.labproblems.web.dto.AssignmentDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class AssignmentController {
    public static final Logger log= LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentConverter assignmentConverter;


    @RequestMapping(value = "/assignments", method = RequestMethod.GET)
    List<AssignmentDto> getAssignments() {

        log.trace("getAllAssignments - method entered");

        List<Assignment> assignments = assignmentService.getAllAssignments();

        return new ArrayList<>(assignmentConverter.convertModelsToDtos(assignments));

    }

    @RequestMapping(value = "/assignments", method = RequestMethod.POST)
    AssignmentDto saveAssignment(@RequestBody AssignmentDto assignmentDto) {
        log.trace("getAllAssignments - method entered");
        Assignment a = assignmentService.saveAssignment(assignmentConverter.convertDtoToModel(assignmentDto));
        if (a == null)
            return null;
        return assignmentConverter.convertModelToDto(a);
    }

    @RequestMapping(value = "/assignments/{id}", method = RequestMethod.PUT)
    AssignmentDto updateAssignment(@PathVariable Long id,
                             @RequestBody AssignmentDto assignmentDto) {
        log.trace("getAllAssignments - method entered");
        return assignmentConverter.convertModelToDto( assignmentService.updateAssignment(
                assignmentConverter.convertDtoToModel(assignmentDto)));
    }

    @RequestMapping(value = "/assignments/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteAssignment(@PathVariable Long id){
        log.trace("getAllAssignments - method entered");

        assignmentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/assignments/{id}", method = RequestMethod.GET)
    AssignmentDto getAssignment(@PathVariable Long id) {
        log.trace("getAllAssignments - method entered");
        Assignment assignment = assignmentService.findAssignment(id).get();
        return assignmentConverter.convertModelToDto(assignment);
    }

    @RequestMapping(value = "/assignments/filterName/{filter}", method = RequestMethod.GET)
    List<AssignmentDto> filterAssignmentsByName(@PathVariable String filter) {
        log.trace("getAllAssignments - method entered");

        Set<Assignment> assignments = assignmentService.filterAssignmentsByName(filter);

        return new ArrayList<>(assignmentConverter.convertModelsToDtos(assignments));
    }

    @RequestMapping(value = "/assignments/filterStudent/{student}", method = RequestMethod.GET)
    List<AssignmentDto> filterAssignmentsByStudent(@PathVariable Long student) {
        log.trace("getAllAssignments - method entered");

        Set<Assignment> assignments = assignmentService.filterAssignmentsByStudent(student);

        return new ArrayList<>(assignmentConverter.convertModelsToDtos(assignments));
    }
}
