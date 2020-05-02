package lab10.labproblems.core.model.entities;

import lombok.*;

import javax.persistence.Entity;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Problem extends BaseEntity<Long> {

    private int number;
    private String text;
}
