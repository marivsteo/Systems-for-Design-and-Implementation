package labproblems.service;

import labproblems.domain.Student;
import labproblems.domain.validators.StudentValidator;
import labproblems.domain.validators.ValidatorException;
import labproblems.repository.Repository;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Marius
 * Service class for the Student entity.
 */
public class StudentService {
    private Repository<Long, Student> repository;

    public StudentService(Repository<Long, Student> repository) {
        this.repository = repository;
    }

    /**
     * The method adds a student with the given attributes.
     * @param _id the id of the student which is to be added
     * @param _serialNumber the serial number of the student which is to be added
     * @param _name the name of the student which is to be added
     * @param _group the group of the student which is to be added
     * @throws ValidatorException if the given attributes are invalid
     */
    public void addStudent(Long _id, String _serialNumber, String _name, int _group) throws ValidatorException {
        try {
            // Create a student
            Student student = new Student(_serialNumber, _name, _group);
            student.setId(_id);
            // Validate the student
            StudentValidator validator = new StudentValidator();
            validator.validate(student);
            // If it is valid, save it
            repository.save(student);
        } catch(ValidatorException exception){
            throw exception;
        }
    }

    /**
     * The method collects all the students in a Set.
     * @return a Set containing all students in the repository
     */
    public Set<Student> getAllStudents() {
        Iterable<Student> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * The method returns all students whose name contain the given string.
     * @param _substring the string we look for in the names
     * @return a Set of students whose names contain the string _substring
     */
    public Set<Student> filterStudentsByName(String _substring) {
        Iterable<Student> students = repository.findAll();
        Set<Student> filteredStudents= new HashSet<>();
        students.forEach(filteredStudents::add);
        // Remove the students that do not have the given substring in their name
        filteredStudents.removeIf(student -> !student.getName().contains(_substring));
        return filteredStudents;
    }

    /**
     * The method deletes a student with the given id.
     * @param _id the id of the student that will be deleted
     * @throws NoSuchElementException if the given id does not correspond to any student
     */
    public void removeStudent(Long _id) throws NoSuchElementException {
        Optional<Student> student = repository.findOne(_id);
        try {
            Student student1 = student.get();
            repository.delete(_id);
        } catch (NoSuchElementException exception){
            throw new NoSuchElementException("StudentService > removeStudent: There is no student with given id = " + _id.toString());
        }
    }

    /**
     * The method updates a student with the given id.
     * @param _id the id of the student what will be updated
     * @param _newSerialNumber the new serial number of the updated student
     * @param _newName the new name of the updated student
     * @param _newGroup the new group of the updated student
     * @throws Exception if either the student with the given id does not exist
     */
    public void updateStudent(Long _id, String _newSerialNumber, String _newName, int _newGroup) throws Exception {
        Student student = new Student(_newSerialNumber,_newName,_newGroup);
        student.setId(_id);
        StudentValidator studentValidator = new StudentValidator();

        try {
            studentValidator.validate(student);
        } catch (ValidatorException exception) {
            throw exception;
        }

        try {
            if (repository.update(student) != null ) {
                throw new Exception("StudentService > updateStudent: There is no student with the given id = " + student.getId().toString());
            }
        } catch( Exception exception){
            throw exception;
        }
    }
}
