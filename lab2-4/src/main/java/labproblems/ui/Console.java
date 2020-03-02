package labproblems.ui;

import labproblems.domain.Student;
import labproblems.domain.validators.ValidatorException;
import labproblems.service.StudentService;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

/**
 * @author Marius
 * Console class, part of the UI
 */
public class Console {
    private StudentService studentService;

    public Console(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * <p>Method that is used for running the console</p>
     */
    public void runConsole() {
        addStudents();
        printAllStudents();
        filterStudents();
    }

    /**
     * Method used for filtering students based on name
     */
    //TODO make it receive a parameter and work with it
    private void filterStudents() {
        System.out.println("filtered students (name containing 's2'):");
        Set<Student> students = studentService.filterStudentsByName("s2");
        students.stream().forEach(System.out::println);
    }

    private void printAllStudents() {
        Set<Student> students = studentService.getAllStudents();
        students.stream().forEach(System.out::println);
    }

    /**
     * If a student is not null, it adds it
     */
    private void addStudents() {
        while (true) {
            Student student = readStudent();
            if (student == null || student.getId() < 0) {
                break;
            }
            try {
                studentService.addStudent(student);
            } catch (ValidatorException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Function that reads a student from a BufferedReader
     * @return student read from the BufferedReader
     */
    private Student readStudent() {
        System.out.println("Read student {id,serialNumber, name, group}");

        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
        try {
            //Long id = Long.parseLong(Optional.ofNullable(bufferRead.readLine()).orElseGet(() -> "-1"));// ...
            Long id = NumberUtils.toLong(bufferRead.readLine(), 0L);
            String serialNumber = bufferRead.readLine();
            String name = bufferRead.readLine();
            int group = NumberUtils.toInt(bufferRead.readLine());// ...

            Student student = new Student(serialNumber, name, group);
            student.setId(id);

            return student;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
