package lab11.labproblems.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class StudentDto extends BaseDto {
    private String serialNumber;
    private String name;
    private int groupNumber;
}
