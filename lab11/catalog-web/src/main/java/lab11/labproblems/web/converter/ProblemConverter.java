package lab11.labproblems.web.converter;

import lab11.labproblems.web.dto.ProblemDto;
import org.springframework.stereotype.Component;
import lab11.labproblems.core.model.entities.Problem;

@Component
public class ProblemConverter extends BaseConverter<Problem, ProblemDto> {
    @Override
    public Problem convertDtoToModel(ProblemDto dto) {
        Problem problem = Problem.builder()
                .number(dto.getNumber())
                .text(dto.getText())
                .build();
        problem.setId(dto.getId());
        return problem;
    }

    @Override
    public ProblemDto convertModelToDto(Problem problem) {
        ProblemDto dto = ProblemDto.builder()
                .number(problem.getNumber())
                .text(problem.getText())
                .build();
        dto.setId(problem.getId());
        return dto;
    }
}
