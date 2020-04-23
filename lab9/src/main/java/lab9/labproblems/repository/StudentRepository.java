package lab9.labproblems.repository;

import lab9.labproblems.model.entities.Student;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by Marius.
 */
public interface StudentRepository extends CatalogRepository<Student, Long> {
}
