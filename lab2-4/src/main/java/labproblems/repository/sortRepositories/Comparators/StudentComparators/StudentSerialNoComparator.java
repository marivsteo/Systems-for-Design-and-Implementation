package labproblems.repository.sortRepositories.Comparators.StudentComparators;

import labproblems.domain.entities.Student;

import java.util.Comparator;

public class StudentSerialNoComparator implements Comparator<Student> {
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getSerialNumber().compareTo(o2.getSerialNumber());
    }
}
