package labproblems.repository;

import labproblems.domain.BaseEntity;
import labproblems.domain.validators.Validator;
import labproblems.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Marius
 * class that implements the Repository interface
 */
public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    private Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    /**
     * Finds the entity with the specified id
     * @param id must be not null.
     * @return an Optional, the Student if exists, null if not
     */
    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Returns all students
     * @return a Set containing all the students
     */
    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return allEntities;
    }

    /**
     * Saves an student
     * @param entity must not be null.
     * @return Optional, the student that was added, null otherwise
     * @throws ValidatorException if the student is not valid
     */
    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        try {
            validator.validate(entity);
        } catch (ValidatorException e) {
            System.out.println("Student attributes are not valid, can not add it");
            throw e; }
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Deletes a student
     * @param id must not be null.
     * @return Optional, the student that was removed, null otherwise
     */
    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates a student
     * @param entity must not be null.
     * @return Optional, the student that was updated, null otherwise
     * @throws ValidatorException if the student is not valid
     */
    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
