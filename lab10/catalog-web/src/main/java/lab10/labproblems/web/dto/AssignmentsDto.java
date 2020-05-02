package lab10.labproblems.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentsDto {
    private Set<AssignmentDto> assignments;
}
