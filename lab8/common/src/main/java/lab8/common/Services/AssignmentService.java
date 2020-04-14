package lab8.common.Services;

import lab8.common.Entities.Assignment;
import lab8.common.Exceptions.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Set;

public interface AssignmentService {
    public float getAvgGradeOfAssignmentsfOfStudentWithProblem(Long _sid, Long _pid);

    public Set<Assignment> getAssignmentsfOfStudentWithProblem(Long _sid, Long _pid);

    public void addAssignment(Long _id, String _name, Long _student, Long _problem, float _grade) throws ValidatorException;

    public Set<Long> getStudentsWhoFailed();

    public Long getProblemAssignedMostTimes();

    public void getStudentWithTotalPoints();

    public Iterable<Assignment> getAllAssignments();

    public Set<Assignment> filterAssignmentsByName(String _substring);

    public Set<Assignment> filterAssignmentsByStudent(Long _student);

    public void removeAssignment(Long _id) throws NoSuchElementException;

    public void updateAssignment(Long _id, String _newName, Long _newStudent, Long _newProblem, float _newGrade) throws Exception;

    }
