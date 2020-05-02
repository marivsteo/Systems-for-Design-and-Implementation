package lab10.labproblems.web.controller;

import lab10.labproblems.core.service.AssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab10.labproblems.core.model.entities.Student;
import lab10.labproblems.core.service.StudentService;
import lab10.labproblems.web.converter.StudentConverter;
import lab10.labproblems.web.dto.StudentDto;
import lab10.labproblems.web.dto.StudentsDto;

import java.util.List;

@RestController
public class StudentController {
    public static final Logger log= LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentConverter studentConverter;


    @RequestMapping(value = "/students", method = RequestMethod.GET)
    StudentsDto getStudents() {
        log.trace("getAllStudents - method entered");

        return new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.getAllStudents()));

    }

    @RequestMapping(value = "/students", method = RequestMethod.POST)
    StudentDto saveStudent(@RequestBody StudentDto studentDto) {
        log.trace("getAllStudents - method entered");

        return studentConverter.convertModelToDto(studentService.saveStudent(
                studentConverter.convertDtoToModel(studentDto)
        ));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.PUT)
    StudentDto updateStudent(@PathVariable Long id,
                             @RequestBody StudentDto studentDto) {
        log.trace("getAllStudents - method entered");

        return studentConverter.convertModelToDto( studentService.updateStudent(
                studentConverter.convertDtoToModel(studentDto)));
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteStudent(@PathVariable Long id){
        log.trace("getAllStudents - method entered");

        assignmentService.synchronizeStudentsInAssignments(id);
        studentService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/students/{id}", method = RequestMethod.GET)
    StudentDto getStudent(@PathVariable Long id) {
        log.trace("getAllStudents - method entered");

        Student student = studentService.findStudent(id).get();
        return studentConverter.convertModelToDto(student);
    }

    @RequestMapping(value = "/students/filterName/{filter}", method = RequestMethod.GET)
    StudentsDto filterStudentsByName(@PathVariable String filter) {
        log.trace("getAllStudents - method entered");

        return new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.filterStudentsByName(filter)));
    }

    @RequestMapping(value = "/students/filterSerialNumber/{filter}", method = RequestMethod.GET)
    StudentsDto filterStudentsBySerialNumber(@PathVariable String filter) {
        log.trace("getAllStudents - method entered");

        return new StudentsDto(studentConverter
                .convertModelsToDtos(studentService.filterStudentsBySerialNumber(filter)));
    }
}
