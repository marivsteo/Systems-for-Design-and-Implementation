package lab8.common.Services;

import lab8.common.Entities.Student;
import lab8.common.Exceptions.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public interface StudentService {

    public void addStudent(Long _id, String _serialNumber, String _name, int _group) throws ValidatorException;

    public Iterable<Student> getAllStudents();

    public Set<Student> filterStudentsByName(String _substring);

    public Set<Student> filterStudentsBySerialNumber(String _substring);

    public void removeStudent(Long _id) throws NoSuchElementException;

    public void updateStudent(Long _id, String _newSerialNumber, String _newName, int _newGroup) throws Exception;

    public Optional<Student> findStudent(Long id);

}
