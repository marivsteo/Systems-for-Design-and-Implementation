package labproblems.repository.fileRepositories;

import labproblems.domain.entities.Problem;
import labproblems.domain.exceptions.ValidatorException;
import labproblems.repository.inMemoryRepository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ProblemFileRepository extends InMemoryRepository<Long,Problem> {
    private String fileName;

    public ProblemFileRepository(String _fileName){
        this.fileName = _fileName;
        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                int number = Integer.parseInt(items.get(1));
                String text = items.get(2);

                Problem problem = new Problem(number,text);
                problem.setId(id);
                System.out.println(problem);
                //TODO Delete print of every problem loaded from file
                try {
                    super.save(problem);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Optional<Problem> save(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Problem entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getNumber() + "," + entity.getText());
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
        Iterable<Problem> allEntities = (Set<Problem>) super.findAll();
        String allProblems = "";
        for(Problem problem: allEntities){
            allProblems += problem.toFileString();
        }

        flushFile();
        Path path = Paths.get(this.fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(allProblems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Problem> delete(Long id) {
        Optional<Problem> optional = super.delete(id);
        updateFile();
        if( optional.isPresent())
            return optional;
        return optional;
    }

    public Optional<Problem> update(Problem entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The entity must not be null.");
        }

        Map<Long,Problem> entities = super.getEntities();

        if(entities.containsKey(entity.getId())) {
            entities.computeIfPresent(entity.getId(), (k, v) -> entity);
            updateFile();
            return null;
        }
        return Optional.ofNullable(entity);
    }
}
