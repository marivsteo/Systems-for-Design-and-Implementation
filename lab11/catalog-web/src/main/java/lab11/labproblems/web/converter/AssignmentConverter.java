package lab11.labproblems.web.converter;

import org.springframework.stereotype.Component;
import lab11.labproblems.core.model.entities.Assignment;
import lab11.labproblems.web.dto.AssignmentDto;

@Component
public class AssignmentConverter extends BaseConverter<Assignment, AssignmentDto> {
    @Override
    public Assignment convertDtoToModel(AssignmentDto dto) {
        Assignment assignment = Assignment.builder()
                .name(dto.getName())
                .student(dto.getStudent())
                .problem(dto.getProblem())
                .grade(dto.getGrade())
                .build();
        assignment.setId(dto.getId());
        return assignment;
    }

    @Override
    public AssignmentDto convertModelToDto(Assignment assignment) {
        AssignmentDto dto = AssignmentDto.builder()
                .name(assignment.getName())
                .student(assignment.getStudent())
                .problem(assignment.getProblem())
                .grade(assignment.getGrade())
                .build();
        dto.setId(assignment.getId());
        return dto;
    }
}
