package labproblems.repository.sortRepositories;

import labproblems.domain.entities.BaseEntity;
import labproblems.domain.exceptions.ValidatorException;
import java.io.Serializable;
import java.util.Optional;

/**
 * Interface for generic CRUD operations on a repository for a specific type.
 *
 * @author andreas.
 */
public interface CrudRepository<ID extends Serializable,
        T extends BaseEntity<ID>> {
    /**
     * Find the entity with the given {@code id}.
     *
     * @param id must be not null.
     * @return an {@code Optional} encapsulating the entity with the given id.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional<T> findOne(Optional<ID> id);

    /**
     * @return all entities.
     */
    Iterable<T> findAll();

    /**
     * Saves the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was saved otherwise
     * (e.g. id already exists) returns the entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    Optional<T> save(Optional<T> entity) throws ValidatorException;

    /**
     * Removes the entity with the given id.
     *
     * @param id must not be null.
     * @return an {@code Optional} - null if there is no entity with the
     * given id,
     * otherwise the removed entity.
     * @throws IllegalArgumentException if the given id is null.
     */
    Optional<T> delete(Optional<ID> id);

    /**
     * Updates the given entity.
     *
     * @param entity must not be null.
     * @return an {@code Optional} - null if the entity was updated otherwise
     * (e.g. id does not exist) returns the
     * entity.
     * @throws IllegalArgumentException if the given entity is null.
     * @throws ValidatorException       if the entity is not valid.
     */
    Optional<T> update(Optional<T> entity) throws ValidatorException;
}
