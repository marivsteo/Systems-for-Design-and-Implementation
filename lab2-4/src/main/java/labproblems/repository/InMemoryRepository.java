package labproblems.repository;

import labproblems.domain.BaseEntity;
import labproblems.domain.validators.Validator;
import labproblems.domain.validators.ValidatorException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
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

    public InMemoryRepository() {
        entities = new HashMap<>();
    }

    /**
     * Finds the entity with the specified id
     * @param id must be not null.
     * @return an Optional, the Student if exists, null if not
     */
    @Override
    public Optional<T> findOne(ID id) throws IllegalArgumentException{

        try{
            assert(id!=null);
        }
        catch (AssertionError error){
            throw new IllegalArgumentException("InMemoryRepository > findOne : ID must not be null.");
        }
        return Optional.ofNullable(entities.get(id));
    }

    /**
     * Returns all students
     * @return a Set containing all the students
     */
    @Override
    public Iterable<T> findAll() {
        Set<T> allEntities = entities.values().stream().collect(Collectors.toSet());
        return allEntities;
    }

    public Map<ID, T> getEntities() {
        return entities;
    }

    /**
     * Saves an student
     * @param entity must not be null.
     * @return Optional, the student that was added, null otherwise
     */
    @Override
    public Optional<T> save(T entity) throws IllegalArgumentException {
        try{
            assert (entity!=null);
        } catch(AssertionError error) {
            throw new IllegalArgumentException("InMemoryRepository > save: ID must not be null.");
        }
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    /**
     * Deletes a student
     * @param id must not be null.
     * @return Optional if the student that was removed, null otherwise
     */
    @Override
    public Optional<T> delete(ID id) {
        try{
            assert(id!=null);
        } catch (AssertionError error){
            throw new IllegalArgumentException("InMemoryRepository > delete : id must not be null.");
        }

        return Optional.ofNullable(entities.remove(id));
    }

    /**
     * Updates a student
     * @param entity must not be null.
     * @return Optional, null if the entity was updated, the entity otherwise
     * @throws ValidatorException if the entity is not valid
     * @throws IllegalArgumentException if the entity is null
     */
    @Override
    public Optional<T> update(T entity) throws IllegalArgumentException, ParserConfigurationException, IOException, SAXException, TransformerException {

        if (entity == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The entity must not be null.");
        }

        if(entities.containsKey(entity.getId())) {
            entities.computeIfPresent(entity.getId(), (k, v) -> entity);
            return null;
        }
        return Optional.ofNullable(entity);
    }
}
