package lab11.labproblems.core.service;

import lab11.labproblems.core.model.entities.Student;
import lab11.labproblems.core.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Marius.
 */
@Slf4j
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;


    public Optional<Student> findStudent(Long id) {
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudents() {
        log.trace("getAllStudents - method entered");
        //return studentRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        //return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "groupNumber", "name"));
    }

    public Student saveStudent(Student student) {
        log.trace("saveStudent - method entered");

        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(Student student) {
        log.trace("updateStudent - method entered: student={}", student);
        Student update = studentRepository.findById(student.getId()).orElse(student);
        update.setSerialNumber(student.getSerialNumber());
        update.setName(student.getName());
        update.setGroupNumber(student.getGroupNumber());
        log.trace("updateStudent - method finished");

        return update;
    }

    public void deleteById(Long id) {
        log.trace("deleteStudent - method entered");

        studentRepository.deleteById(id);
    }

    public Set<Student> filterStudentsByName(String _substring) {
        log.trace("filterStudentsByName - method entered");

        Iterable<Student> students = studentRepository.findAll();
        Set<Student> filteredStudents = new HashSet<>();
        students.forEach(filteredStudents::add);
        // Remove the students that do not have the given substring in their name
        filteredStudents.removeIf(student -> !student.getName().contains(_substring));
        return filteredStudents;
    }

    public Set<Student> filterStudentsBySerialNumber(String _substring){
        log.trace("filterStudentsBySerialNumber - method entered");

        Iterable<Student> students = studentRepository.findAll();
        Set<Student> filteredStudents = new HashSet<>();
        students.forEach(filteredStudents::add);
        // Remove the students that do not have the given substring in their serial number
        filteredStudents.removeIf(student -> !student.getSerialNumber().contains(_substring));
        return filteredStudents;
    }
}
