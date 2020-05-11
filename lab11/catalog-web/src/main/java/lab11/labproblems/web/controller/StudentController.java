package lab11.labproblems.web.controller;

import lab11.labproblems.core.service.AssignmentService;
import lab11.labproblems.web.converter.StudentConverter;
import lab11.labproblems.web.dto.StudentDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lab11.labproblems.core.model.entities.Student;
import lab11.labproblems.core.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    List<StudentDto> getStudents() {
        log.trace("getAllStudents - method entered");

        List<Student> students = studentService.getAllStudents();

        log.trace("getStudents: students={}", students);

        return new ArrayList<>(studentConverter.convertModelsToDtos(students));

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
    List<StudentDto> filterStudentsByName(@PathVariable String filter) {
        log.trace("getAllStudents - method entered");

        Set<Student> students = studentService.filterStudentsByName(filter);

        return new ArrayList<>(studentConverter.convertModelsToDtos(students));

    }

    @RequestMapping(value = "/students/filterSerialNumber/{filter}", method = RequestMethod.GET)
    List<StudentDto> filterStudentsBySerialNumber(@PathVariable String filter) {
        log.trace("getAllStudents - method entered");

        Set<Student> students = studentService.filterStudentsByName(filter);

        return new ArrayList<>(studentConverter.convertModelsToDtos(students));
    }
}
