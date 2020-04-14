package lab8.server.Repository;

import lab8.common.Entities.BaseEntity;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * @author andreas.
 */
public interface ISortingRepository<ID extends Serializable,
        T extends BaseEntity<ID>>
        extends lab8.server.Repository.Repository<ID, T> {

    Iterable<T> findAll(Sort sort);

}

