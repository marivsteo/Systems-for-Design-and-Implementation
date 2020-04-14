package lab8.client.Services;

import lab8.common.Entities.Student;
import lab8.common.Exceptions.ValidatorException;
import lab8.common.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceClient implements StudentService{
    @Autowired
    private StudentService studentService;

    @Override
    public void addStudent(Long id, String _serialNumber, String _name, int _group) throws ValidatorException {
        studentService.addStudent(id, _serialNumber, _name, _group);
    }

    @Override
    public Iterable<Student> getAllStudents() {
        System.out.println("studentServiceClient -> getAllStudents");
        return studentService.getAllStudents();
    }

    @Override
    public Set<Student> filterStudentsByName(String _substring) {
        return studentService.filterStudentsByName(_substring);
    }

    @Override
    public Set<Student> filterStudentsBySerialNumber(String _substring) {
        return studentService.filterStudentsBySerialNumber(_substring);
    }

    @Override
    public void removeStudent(Long _id) throws NoSuchElementException {
        studentService.removeStudent(_id);
    }

    @Override
    public void updateStudent(Long _id, String _newSerialNumber, String _newName, int _newGroup) throws Exception {
        studentService.updateStudent(_id, _newSerialNumber, _newName, _newGroup);
    }

    @Override
    public Optional<Student> findStudent(Long id) {
        return studentService.findStudent(id);
    }
}
