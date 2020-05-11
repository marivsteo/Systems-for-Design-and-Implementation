package lab11.labproblems.core.model.entities;

import lombok.*;

import javax.persistence.Entity;

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
