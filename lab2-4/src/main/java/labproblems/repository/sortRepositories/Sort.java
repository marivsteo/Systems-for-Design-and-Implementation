package labproblems.repository.sortRepositories;


import labproblems.domain.entities.Assignment;
import labproblems.domain.entities.BaseEntity;
import labproblems.domain.entities.Problem;
import labproblems.domain.entities.Student;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
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

    public void sort(BaseEntity b){

        String className = b.getClass().getName();
        Field[] fields = b.getClass().getDeclaredFields();
        fields[1].

        if( this.parameters.contains("name")) {
            if( this.parameters.contains("DESC") )
                list = list.stream().sorted(Comparator.comparing(fields[1].).reversed()).collect(Collectors.toList());
            else list = list.stream().sorted(Comparator.comparing(Student::getName)).collect(Collectors.toList());
        }
        if( this.parameters.contains("serialNr")) {
            if( this.parameters.contains("DESC") )
                list = list.stream().sorted(Comparator.comparing(Student::getSerialNumber).reversed()).collect(Collectors.toList());
            else list = list.stream().sorted(Comparator.comparing(Student::getSerialNumber)).collect(Collectors.toList());
        }
        if( this.parameters.contains("group")) {
            if( this.parameters.contains("DESC") )
                list = list.stream().sorted(Comparator.comparing(Student::getGroup).reversed()).collect(Collectors.toList());
            list = list.stream().sorted(Comparator.comparing(Student::getGroup)).collect(Collectors.toList());
        }
    }

//    public void sortStudents(List<Student> list){
//        if( this.parameters.contains("name")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Student::getName).reversed()).collect(Collectors.toList());
//            else list = list.stream().sorted(Comparator.comparing(Student::getName)).collect(Collectors.toList());
//        }
//        if( this.parameters.contains("serialNr")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Student::getSerialNumber).reversed()).collect(Collectors.toList());
//            else list = list.stream().sorted(Comparator.comparing(Student::getSerialNumber)).collect(Collectors.toList());
//        }
//        if( this.parameters.contains("group")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Student::getGroup).reversed()).collect(Collectors.toList());
//            list = list.stream().sorted(Comparator.comparing(Student::getGroup)).collect(Collectors.toList());
//        }
//    }
//
//    public void sortProblems(List<Problem> list){
//        if( this.parameters.contains("number")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Problem::getNumber).reversed()).collect(Collectors.toList());
//            else list = list.stream().sorted(Comparator.comparing(Problem::getNumber)).collect(Collectors.toList());
//        }
//        if( this.parameters.contains("text")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Problem::getText).reversed()).collect(Collectors.toList());
//            else list = list.stream().sorted(Comparator.comparing(Problem::getText)).collect(Collectors.toList());
//        }
//    }
//
//    public void sortAssignments(List<Assignment> list){
//        if( this.parameters.contains("name")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Assignment::getName).reversed()).collect(Collectors.toList());
//            else list = list.stream().sorted(Comparator.comparing(Assignment::getName)).collect(Collectors.toList());
//        }
//        if( this.parameters.contains("student")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Assignment::getStudent).reversed()).collect(Collectors.toList());
//            else list = list.stream().sorted(Comparator.comparing(Assignment::getStudent)).collect(Collectors.toList());
//        }
//        if( this.parameters.contains("problem")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Assignment::getProblem).reversed()).collect(Collectors.toList());
//            list = list.stream().sorted(Comparator.comparing(Assignment::getProblem)).collect(Collectors.toList());
//        }
//        if( this.parameters.contains("grade")) {
//            if( this.parameters.contains("DESC") )
//                list = list.stream().sorted(Comparator.comparing(Assignment::getGrade).reversed()).collect(Collectors.toList());
//            list = list.stream().sorted(Comparator.comparing(Assignment::getGrade)).collect(Collectors.toList());
//        }
//    }

}
