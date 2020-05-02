package lab10.labproblems.core.model.entities;

import lombok.*;

import javax.persistence.Entity;

/**
 * @author Marius
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Student extends BaseEntity<Long> {
    private String serialNumber;
    private String name;
    private int groupNumber;
}
