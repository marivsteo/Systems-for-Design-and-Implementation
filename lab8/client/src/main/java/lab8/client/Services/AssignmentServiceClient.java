package lab8.client.Services;

import lab8.common.Entities.Assignment;
import lab8.common.Exceptions.ValidatorException;
import lab8.common.Services.AssignmentService;
import lab8.common.Services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class AssignmentServiceClient implements AssignmentService{
    @Autowired
    private AssignmentService assignmentService;

    @Override
    public float getAvgGradeOfAssignmentsfOfStudentWithProblem(Long _sid, Long _pid) {
        return assignmentService.getAvgGradeOfAssignmentsfOfStudentWithProblem(_sid, _pid);
    }

    @Override
    public Set<Assignment> getAssignmentsfOfStudentWithProblem(Long _sid, Long _pid) {
        return assignmentService.getAssignmentsfOfStudentWithProblem(_sid, _pid);
    }

    @Override
    public void addAssignment(Long _id, String _name, Long _student, Long _problem, float _grade) throws ValidatorException {
        assignmentService.addAssignment(_id, _name, _student, _problem, _grade);
    }

    @Override
    public Set<Long> getStudentsWhoFailed() {
        return assignmentService.getStudentsWhoFailed();
    }

    @Override
    public Long getProblemAssignedMostTimes() {
        return assignmentService.getProblemAssignedMostTimes();
    }

    @Override
    public void getStudentWithTotalPoints() {
        assignmentService.getStudentWithTotalPoints();
    }

    @Override
    public Iterable<Assignment> getAllAssignments() {
        return assignmentService.getAllAssignments();
    }

    @Override
    public Set<Assignment> filterAssignmentsByName(String _substring) {
        return assignmentService.filterAssignmentsByName(_substring);
    }

    @Override
    public Set<Assignment> filterAssignmentsByStudent(Long _student) {
        return assignmentService.filterAssignmentsByStudent(_student);
    }

    @Override
    public void removeAssignment(Long _id) throws NoSuchElementException {
        assignmentService.removeAssignment(_id);
    }

    @Override
    public void updateAssignment(Long _id, String _newName, Long _newStudent, Long _newProblem, float _newGrade) throws Exception {
        assignmentService.updateAssignment(_id, _newName, _newStudent, _newProblem, _newGrade);
    }
}
