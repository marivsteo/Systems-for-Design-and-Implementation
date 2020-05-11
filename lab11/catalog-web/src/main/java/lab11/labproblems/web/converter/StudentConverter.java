package lab11.labproblems.web.converter;

import org.springframework.stereotype.Component;
import lab11.labproblems.core.model.entities.Student;
import lab11.labproblems.web.dto.StudentDto;

@Component
public class StudentConverter extends BaseConverter<Student, StudentDto> {
    @Override
    public Student convertDtoToModel(StudentDto dto) {
        Student student = Student.builder()
                .serialNumber(dto.getSerialNumber())
                .name(dto.getName())
                .groupNumber(dto.getGroupNumber())
                .build();
        student.setId(dto.getId());
        return student;
    }

    @Override
    public StudentDto convertModelToDto(Student student) {
        StudentDto dto = StudentDto.builder()
                .serialNumber(student.getSerialNumber())
                .name(student.getName())
                .groupNumber(student.getGroupNumber())
                .build();
        dto.setId(student.getId());
        return dto;
    }
}
