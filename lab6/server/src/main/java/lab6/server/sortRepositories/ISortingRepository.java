package lab6.server.sortRepositories;

import lab6.common.Entities.BaseEntity;

import java.io.Serializable;

/**
 * @author andreas.
 */
public interface ISortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends Repository<ID, T> {

    Iterable<T> findAll(Sort sort);
}

