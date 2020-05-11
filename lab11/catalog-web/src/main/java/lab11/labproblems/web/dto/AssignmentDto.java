package lab11.labproblems.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class AssignmentDto extends BaseDto {
    private String name;
    private Long student;
    private Long problem;
    private float grade;
}
