package labproblems.repository.sortRepositories;

import labproblems.domain.entities.BaseEntity;

import java.io.Serializable;

/**
 * @author andreas.
 */
public interface ISortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends CrudRepository<ID, T> {

    Iterable<T> findAll(Sort sort);

    //TODO: insert sorting-related code here
}

