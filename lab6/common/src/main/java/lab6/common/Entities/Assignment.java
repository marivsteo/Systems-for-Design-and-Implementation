package lab6.common.Entities;

import java.util.Objects;

public class Assignment extends BaseEntity<Long> {

    private String name;
    private Long student;
    private Long problem;
    private float grade;

    public Assignment() {}

    public Assignment(String name, Long student, Long problem, float grade) {
        this.name = name;
        this.student = student;
        this.problem = problem;
        this.grade = grade;
    }

    public Assignment(String name, Long student, Long problem) {
        this.name = name;
        this.student = student;
        this.problem = problem;
        this.grade = (float) 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getProblem() {
        return problem;
    }

    public void setProblem(Long problem) {
        this.problem = problem;
    }

    public float getGrade() {
        return grade;
    }

    public void setGrade(float grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return grade == that.grade &&
                name.equals(that.name) &&
                student.equals(that.student) &&
                problem.equals(that.problem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, student, problem, grade);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "name='" + name + '\'' +
                ", student=" + student +
                ", problem=" + problem +
                ", grade=" + grade +
                '}' + super.toString();
    }

    public String toFileString(){
        return this.getId() + "," + this.getName() + "," + this.getStudent() + "," + this.getProblem() + "," + this.getGrade() + "\n";
    }
}
