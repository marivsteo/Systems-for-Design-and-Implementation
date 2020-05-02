package lab10.labproblems.core.repository;

import lab10.labproblems.core.model.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by Marius.
 */
@NoRepositoryBean
public interface CatalogRepository<T extends BaseEntity<ID>, ID extends Serializable>
        extends JpaRepository<T, ID> {
}
