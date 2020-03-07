package labproblems.domain;

import java.util.Objects;

public class Assignment extends BaseEntity<Long> {

    private String name;
    private Student student;
    private Problem problem;

    public Assignment() {}

    public Assignment(String name, Student student, Problem problem) {
        this.name = name;
        this.student = student;
        this.problem = problem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return name.equals(that.name) &&
                student.equals(that.student) &&
                problem.equals(that.problem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, student, problem);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", student=" + student +
                ", problem=" + problem +
                '}';
    }
}
