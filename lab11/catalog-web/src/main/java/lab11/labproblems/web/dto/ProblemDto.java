package lab11.labproblems.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class ProblemDto extends BaseDto {
    private int number;
    private String text;
}
