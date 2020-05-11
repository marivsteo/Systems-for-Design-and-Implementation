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
public class Assignment extends BaseEntity<Long> {

    private String name;
    private Long student;
    private Long problem;
    private float grade;

}
