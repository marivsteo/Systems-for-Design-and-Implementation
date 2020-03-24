package labproblems.repository.sortRepositories.Comparators.StudentComparators;

import labproblems.domain.entities.Student;

import java.util.Comparator;

public class StudentGroupNoComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getGroup() - o2.getGroup();
    }
}
