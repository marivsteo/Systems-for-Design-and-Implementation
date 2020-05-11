package lab11.labproblems.core.service;

import lab11.labproblems.core.model.entities.Assignment;
import lab11.labproblems.core.model.exceptions.ValidatorException;
import lab11.labproblems.core.repository.AssignmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProblemService problemService;

    public Optional<Assignment> findAssignment(Long id) {
        log.trace("findAssignment - method entered");

        return assignmentRepository.findById(id);
    }

    public List<Assignment> getAllAssignments() {
        log.trace("getAllAssignments - method entered");
        return assignmentRepository.findAll();
    }

    public Assignment saveAssignment(Assignment assignment) {
        log.trace("saveAssignment - method entered");
        if (studentService.findStudent(assignment.getStudent()).isPresent() && problemService.findProblem(assignment.getProblem()).isPresent())
            return assignmentRepository.save(assignment);
        return null;
    }

    @Transactional
    public Assignment updateAssignment(Assignment assignment) {
        log.trace("updateAssignment - method entered: assignment={}", assignment);
        Assignment a = assignmentRepository.findById(assignment.getId()).orElse(assignment);
        a.setStudent(assignment.getStudent());
        a.setName(assignment.getName());
        a.setProblem(assignment.getProblem());
        a.setGrade(assignment.getGrade());
        log.trace("updateAssignment - method finished");

        return a;
    }

    public void deleteById(Long id) {
        log.trace("deleteAssignment - method entered");

        assignmentRepository.deleteById(id);
    }

    public void validateIds(Long studentId, Long problemId) throws ValidatorException {
        log.trace("validateIds - method entered");

        if( !this.studentService.findStudent(studentId).isPresent()
                ||  !this.problemService.findProblem(problemId).isPresent()){
            throw new ValidatorException("The given ids for the student or/and problem is/are not valid.");
        }
    }

    public float getAvgGradeOfAssignmentsfOfStudentWithProblem(Long _sid, Long _pid) {
        log.trace("getAvgGradeOfAssignmentsOfStudentWithProblem - method entered");

        List<Assignment> assignments1 = this.assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignments1);
        Set<Assignment> assignmentSet = assignments.stream().filter(assignment -> assignment.getStudent().equals(_sid))
                .filter(assignment -> assignment.getProblem().equals(_pid))
                .collect(Collectors.toSet());
        int nr = assignmentSet.size();
        float sumOfGrades = assignmentSet.stream().map(assignment -> assignment.getGrade()).reduce((float) 0, (subtotal, assignment) -> subtotal + assignment);
        return sumOfGrades/nr;
    }

    public Set<Assignment> getAssignmentsfOfStudentWithProblem(Long _sid, Long _pid){
        log.trace("getAssignmentsOfStudentWithProblem - method entered");

        List<Assignment> assignments1 = this.assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignments1);
        return assignments.stream().filter(assignment -> assignment.getStudent().equals(_sid))
                .filter(assignment -> assignment.getProblem().equals(_pid))
                .collect(Collectors.toSet());
    }

    public void synchronizeStudentsInAssignments(Long _id){
        log.trace("synchronizeStudentsInAssignments - method entered");

        List<Assignment> assignments1 = this.assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignments1);
        Set<Long> assignmentsIds= assignments.stream().filter(assignment -> assignment.getStudent().equals(_id))
                .map(Assignment::getId)
                .collect(Collectors.toSet());
        assignmentsIds.forEach(this.assignmentRepository::deleteById);
    }

    public void synchronizeProblemsInAssignments(Long _id){
        log.trace("synchronizeProblemsInAssignments - method entered");

        List<Assignment> assignments1 = this.assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignments1);
        Set<Long> assignmentsIds= assignments.stream().filter(assignment -> assignment.getProblem().equals(_id))
                .map(Assignment::getId)
                .collect(Collectors.toSet());
        assignmentsIds.forEach(this.assignmentRepository::deleteById);
    }

    public Set<Long> getStudentsWhoFailed(){
        log.trace("getStudentsWhoFailed - method entered");

        List<Assignment> assignmentList = assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignmentList);
        assignments.removeIf(assignment -> assignment.getGrade() >= 5);
        return assignments.stream().map(Assignment::getStudent).collect(Collectors.toSet());
    }

    public Long getProblemAssignedMostTimes() {
        log.trace("getProblemAssignedMostTimes - method entered");

        List<Assignment> assignmentList = assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignmentList);
        Map<Long, Long> countForId = assignments.stream()
                .collect(Collectors.groupingBy(Assignment::getProblem, Collectors.counting()));
        Map<Long, Long> sortedByValue = countForId.entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
//        sortedByValue.entrySet().forEach(entry->{
//            System.out.println(entry.getKey() + " " + entry.getValue());});
        Optional<Map.Entry<Long, Long>> Problem = sortedByValue.entrySet().stream().findFirst();
        return Problem.get().getKey();
    }

    public void getStudentWithTotalPoints() {
        log.trace("getStudentWithTotalPoints - method entered");

        List<Assignment> assignments1 = this.assignmentRepository.findAll();
        Set<Assignment> assignments = new HashSet<Assignment>(assignments1);
        Map<Long, Double> studentsGrade = assignments.stream()
                .collect(Collectors.groupingBy(Assignment::getStudent,
                        Collectors.summingDouble(Assignment::getGrade)));

        studentsGrade.forEach((key, value) -> System.out.println(studentService.findStudent(key).get() + " Total Points: " + value));

    }

    public Set<Assignment> filterAssignmentsByName(String _substring) {
        log.trace("filterAssignmentsByName - method entered");

        Iterable<Assignment> assignments = assignmentRepository.findAll();
        Set<Assignment> filteredAssignments = new HashSet<>();
        assignments.forEach(filteredAssignments::add);
        // Remove the assignments that do not have the given substring in their name
        filteredAssignments.removeIf(assignment -> !assignment.getName().contains(_substring));
        return filteredAssignments;
    }

    public Set<Assignment> filterAssignmentsByStudent(Long _student){
        log.trace("filterAssignmentsByStudent - method entered");

        Iterable<Assignment> assignments = assignmentRepository.findAll();
        Set<Assignment> filteredAssignments = new HashSet<>();
        assignments.forEach(filteredAssignments::add);
        // Remove the assignments that do not have the given student
        filteredAssignments.removeIf(assignment -> !assignment.getStudent().equals(_student));
        return filteredAssignments;
    }

}
