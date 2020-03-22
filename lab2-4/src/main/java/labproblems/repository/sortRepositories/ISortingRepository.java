package labproblems.repository.sortRepositories;

import labproblems.domain.entities.BaseEntity;
import labproblems.repository.Repository;

import java.io.Serializable;

/**
 * @author andreas.
 */
public interface ISortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}

