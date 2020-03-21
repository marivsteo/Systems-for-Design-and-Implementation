package labproblems.service;

import labproblems.domain.entities.Assignment;
import labproblems.domain.validators.AssignmentValidator;
import labproblems.domain.exceptions.ValidatorException;
import labproblems.repository.Repository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AssignmentService {
    private Repository<Long, Assignment> repository;
    private AssignmentValidator assignmentValidator;
    private StudentService studentService;
    private ProblemService problemService;

    public AssignmentService(Repository<Long, Assignment> repository, AssignmentValidator assignmentValidator,
                             StudentService studentService, ProblemService problemService) {
        this.repository = repository;
        this.assignmentValidator = assignmentValidator;
        this.studentService = studentService;
        this.problemService = problemService;
    }

    public void validateIds(Long studentId, Long problemId) throws ValidatorException{
        if( !this.studentService.findStudent(studentId).isPresent()
                ||  !this.problemService.findProblem(problemId).isPresent()){
            throw new ValidatorException("The given ids for the student or/and problem is/are not valid.");
        }
    }

    public float getAvgGradeOfAssignmentsfOfStudentWithProblem(Long _sid, Long _pid) {
        Set<Assignment> assignments = (Set<Assignment>) this.repository.findAll();
        Set<Assignment> assignmentSet = assignments.stream().filter(assignment -> assignment.getStudent().equals(_sid))
                .filter(assignment -> assignment.getProblem().equals(_pid))
                .collect(Collectors.toSet());
        int nr = assignmentSet.size();
        float sumOfGrades = assignmentSet.stream().map(assignment -> assignment.getGrade()).reduce((float) 0, (subtotal, assignment) -> subtotal + assignment);
        return sumOfGrades/nr;
    }

    public Set<Assignment> getAssignmentsfOfStudentWithProblem(Long _sid, Long _pid){
        Set<Assignment> assignments = (Set<Assignment>) this.repository.findAll();
        return assignments.stream().filter(assignment -> assignment.getStudent().equals(_sid))
                .filter(assignment -> assignment.getProblem().equals(_pid))
                .collect(Collectors.toSet());
    }


    /**
     * The method adds an assignment with the given attributes.
     * @param _id the id of the student which is to be added
     * @param _name Name of the assignment
     * @param _student the student which the assignment is assigned to
     * @param _problem the problem corresponding to this assignment
     * @param _grade the grade received for the assignment
     * @throws ValidatorException if the given attributes are invalid
     */
    public void addAssignment(Long _id, String _name, Long _student, Long _problem, float _grade) throws ValidatorException {
        try {
            // Create an assignment
            validateIds(_student,_problem);
            Assignment assignment = new Assignment(_name, _student, _problem, _grade);
            assignment.setId(_id);
            // Validate the assignment
            this.assignmentValidator.validate(assignment);
            // If it is valid, save it
            repository.save(assignment);
        } catch(ValidatorException exception){
            throw exception;
        }
    }

    public void synchronizeStudentsInAssignments(Long _id){
        Set<Assignment> assignments = (Set<Assignment>) this.repository.findAll();
        Set<Long> assignmentsIds= assignments.stream().filter(assignment -> assignment.getStudent().equals(_id))
                .map(Assignment::getId)
                .collect(Collectors.toSet());
        assignmentsIds.forEach(this.repository::delete);
    }

    public void synchronizeProblemsInAssignments(Long _id){
        Set<Assignment> assignments = (Set<Assignment>) this.repository.findAll();
        Set<Long> assignmentsIds= assignments.stream().filter(assignment -> assignment.getProblem().equals(_id))
                .map(Assignment::getId)
                .collect(Collectors.toSet());
        assignmentsIds.forEach(this.repository::delete);
    }

    /**
     * This method determines which are the students who did not fail any assignments.
     * @return a Set containing all student ids who did not fail any assignments.
     */
    public Set<Long> getStudentsWhoFailed(){
        Set<Assignment> assignments = (Set<Assignment>) repository.findAll();
        assignments.removeIf(assignment -> assignment.getGrade() >= 5);
        return assignments.stream().map(Assignment::getStudent).collect(Collectors.toSet());
    }

    public Long getProblemAssignedMostTimes() {
        Set<Assignment> assignments = (Set<Assignment>) repository.findAll();
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
        Set<Assignment> assignments = (Set<Assignment>) repository.findAll();
        Map<Long, Double> studentsGrade = assignments.stream()
                .collect(Collectors.groupingBy(Assignment::getStudent,
                        Collectors.summingDouble(Assignment::getGrade)));

        studentsGrade.forEach((key, value) -> System.out.println(studentService.findStudent(key).get() + " Total Points: " + value));

    }

    /**
     * The method collects all the assignments in a Set.
     * @return a Set containing all assignments in the repository
     */
    public Set<Assignment> getAllAssignments() {
        Iterable<Assignment> assignments = repository.findAll();
        return StreamSupport.stream(assignments.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * The method returns all assignments whose name contain the given string.
     * @param _substring the string that it is looked for in the names
     * @return a Set of assignments whose names contain the string _substring
     */
    public Set<Assignment> filterAssignmentsByName(String _substring) {
        Iterable<Assignment> assignments = repository.findAll();
        Set<Assignment> filteredAssignments = new HashSet<>();
        assignments.forEach(filteredAssignments::add);
        // Remove the assignments that do not have the given substring in their name
        filteredAssignments.removeIf(assignment -> !assignment.getName().contains(_substring));
        return filteredAssignments;
    }

    /**
     * The method returns all assignments for a given Student
     * @param _student the student for which the search is made
     * @return a Set of assignments that belong to the given student
     */
    public Set<Assignment> filterAssignmentsByStudent(Long _student){
        Iterable<Assignment> assignments = repository.findAll();
        Set<Assignment> filteredAssignments = new HashSet<>();
        assignments.forEach(filteredAssignments::add);
        // Remove the assignments that do not have the given student
        filteredAssignments.removeIf(assignment -> !assignment.getStudent().equals(_student));
        return filteredAssignments;
    }

    /**
     * The method deletes the assignment with the given id.
     * @param _id the id of the assignment that will be deleted
     * @throws NoSuchElementException if the given id does not correspond to any assignment
     */
    public void removeAssignment(Long _id) throws NoSuchElementException {
        Optional<Assignment> assignment = repository.findOne(_id);
        try {
            Assignment assignment1 = assignment.get();
            repository.delete(_id);
        } catch (NoSuchElementException exception){
            throw new NoSuchElementException("AssignmentService > removeAssignment: There is no assignment with given id = " + _id.toString());
        }
    }

    /**
     * The method updates an assignment with the given id.
     * @param _id the id of the assignment what will be updated
     * @param _newName the new name of the assignment
     * @param _newStudent the new student for the assignment
     * @param _newProblem the new problem for the assignment
     * @param _newGrade the new grade for the assignment
     * @throws Exception if either the student with the given id does not exist or the new attributes are not valid
     */
    public void updateAssignment(Long _id, String _newName, Long _newStudent, Long _newProblem, float _newGrade) throws Exception {
        Assignment assignment = new Assignment(_newName, _newStudent, _newProblem, _newGrade);
        assignment.setId(_id);

        try {
            this.assignmentValidator.validate(assignment);
        } catch (ValidatorException exception) {
            throw exception;
        }

        try {
            if (repository.update(assignment) != null ) {
                throw new Exception("AssignmentService > updateAssignment: There is no assignment with the given id = " + assignment.getId().toString());
            }
        } catch( Exception exception){
            throw exception;
        }
    }
}
