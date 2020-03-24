package labproblems.repository.sortRepositories.Comparators.StudentComparators;

import labproblems.domain.entities.Student;

import java.util.Comparator;

public class StudentNameComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
