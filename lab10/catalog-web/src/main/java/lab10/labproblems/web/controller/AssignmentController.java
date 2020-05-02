package lab10.labproblems.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab10.labproblems.core.model.entities.Assignment;
import lab10.labproblems.core.service.AssignmentService;
import lab10.labproblems.web.converter.AssignmentConverter;
import lab10.labproblems.web.dto.AssignmentDto;
import lab10.labproblems.web.dto.AssignmentsDto;

import java.util.List;

@RestController
public class AssignmentController {
    public static final Logger log= LoggerFactory.getLogger(AssignmentController.class);

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentConverter assignmentConverter;


    @RequestMapping(value = "/assignments", method = RequestMethod.GET)
    AssignmentsDto getAssignments() {
        log.trace("getAllAssignments - method entered");
        return new AssignmentsDto(assignmentConverter
                .convertModelsToDtos(assignmentService.getAllAssignments()));

    }

    @RequestMapping(value = "/assignments", method = RequestMethod.POST)
    AssignmentDto saveAssignment(@RequestBody AssignmentDto assignmentDto) {
        log.trace("getAllAssignments - method entered");
        return assignmentConverter.convertModelToDto(assignmentService.saveAssignment(
                assignmentConverter.convertDtoToModel(assignmentDto)
        ));
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
    AssignmentsDto filterAssignmentsByName(@PathVariable String filter) {
        log.trace("getAllAssignments - method entered");

        return new AssignmentsDto(assignmentConverter
                .convertModelsToDtos(assignmentService.filterAssignmentsByName(filter)));
    }

    @RequestMapping(value = "/assignments/filterStudent/{student}", method = RequestMethod.GET)
    AssignmentsDto filterAssignmentsByStudent(@PathVariable Long student) {
        log.trace("getAllAssignments - method entered");

        return new AssignmentsDto(assignmentConverter
                .convertModelsToDtos(assignmentService.filterAssignmentsByStudent(student)));
    }
}
