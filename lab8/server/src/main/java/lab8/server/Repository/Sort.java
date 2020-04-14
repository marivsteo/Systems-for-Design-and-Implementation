package lab8.server.Repository;

import lab8.common.Entities.Assignment;
import lab8.common.Entities.Problem;
import lab8.common.Entities.Student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author andreas.
 */
public class Sort{

    private List<String> parameters;

    public Sort(String... params){
        this.parameters = Arrays.asList(params);
    }

    public boolean contains(String string){
        return this.parameters.contains(string);
    }

    public Iterable<Student> sorts(List<Student> allEntities){
        if( this.contains("Name")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Student::getName).reversed()).collect(Collectors.toList());
            else allEntities = allEntities.stream().sorted(Comparator.comparing(Student::getName)).collect(Collectors.toList());
        }
        if( this.contains("SerialNumber")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Student::getSerialNumber).reversed()).collect(Collectors.toList());
            else allEntities = allEntities.stream().sorted(Comparator.comparing(Student::getSerialNumber)).collect(Collectors.toList());
        }
        if( this.contains("Group")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Student::getGroup).reversed()).collect(Collectors.toList());
            allEntities = allEntities.stream().sorted(Comparator.comparing(Student::getGroup)).collect(Collectors.toList());
        }
        return allEntities;
    }

    public Iterable<Assignment> sorta(List<Assignment> allEntities ){
        if( contains("Name")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getName).reversed()).collect(Collectors.toList());
            else allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getName)).collect(Collectors.toList());
        }
        if( contains("Grade")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getGrade).reversed()).collect(Collectors.toList());
            else allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getGrade)).collect(Collectors.toList());
        }
        if( contains("Student")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getStudent).reversed()).collect(Collectors.toList());
            allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getStudent)).collect(Collectors.toList());
        }
        if( contains("Problem")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getProblem).reversed()).collect(Collectors.toList());
            allEntities = allEntities.stream().sorted(Comparator.comparing(Assignment::getProblem)).collect(Collectors.toList());
        }
        return allEntities;
    }

    public Iterable<Problem> sortp(List<Problem> allEntities){
        if( contains("Number")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Problem::getNumber).reversed()).collect(Collectors.toList());
            else allEntities = allEntities.stream().sorted(Comparator.comparing(Problem::getNumber)).collect(Collectors.toList());
        }
        if( contains("Text")) {
            if( contains("DESC") )
                allEntities = allEntities.stream().sorted(Comparator.comparing(Problem::getText).reversed()).collect(Collectors.toList());
            else allEntities = allEntities.stream().sorted(Comparator.comparing(Problem::getText)).collect(Collectors.toList());
        }
        return allEntities;
    }

}
