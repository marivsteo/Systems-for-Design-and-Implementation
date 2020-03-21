package labproblems.repository.fileRepositories;

import labproblems.domain.entities.Assignment;
import labproblems.domain.exceptions.ValidatorException;
import labproblems.repository.inMemoryRepository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class AssignmentFileRepository extends InMemoryRepository<Long, Assignment> {
    private String fileName;

    public AssignmentFileRepository(String _fileName){
        this.fileName = _fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String name = items.get(1);
                Long studentId  = Long.valueOf(items.get(2));
                Long problemId = Long.valueOf(items.get(3));
                float grade = Float.parseFloat(items.get(4));

                Assignment assignment = new Assignment(name,studentId,problemId,grade);
                assignment.setId(id);
                System.out.println(assignment);
                //TODO Delete print of every assignment loaded from file
                try {
                    super.save(assignment);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Assignment> save(Assignment entity) throws ValidatorException {
        Optional<Assignment> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Assignment entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getName() + "," + entity.getStudent() + "," + entity.getProblem() + "," + entity.getGrade());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void flushFile(){
        Path path = Paths.get(this.fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
            bufferedWriter.write("");
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFile(){
        Iterable<Assignment> allEntities = (Set<Assignment>) super.findAll();
        String allAssignments = "";
        for(Assignment assignment: allEntities){
            allAssignments += assignment.toFileString();
        }

        flushFile();
        Path path = Paths.get(this.fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(allAssignments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Assignment> delete(Long id) {
        Optional<Assignment> optional = super.delete(id);
        updateFile();
        if( optional.isPresent())
            return optional;
        return optional;
    }

    public Optional<Assignment> update(Assignment entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The entity must not be null.");
        }

        Map<Long,Assignment> entities = super.getEntities();

        if(entities.containsKey(entity.getId())) {
            entities.computeIfPresent(entity.getId(), (k, v) -> entity);
            updateFile();
            return null;
        }
        return Optional.ofNullable(entity);
    }
}
