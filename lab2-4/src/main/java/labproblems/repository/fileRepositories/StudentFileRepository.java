package labproblems.repository.fileRepositories;

import labproblems.domain.entities.Student;
import labproblems.domain.exceptions.ValidatorException;
import labproblems.repository.inMemoryRepository.InMemoryRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * @author radu.
 */
public class StudentFileRepository extends InMemoryRepository<Long, Student> {
    private String fileName;

    public StudentFileRepository( String fileName) {
        this.fileName = fileName;

        loadData();
    }

    private void loadData() {
        Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
                List<String> items = Arrays.asList(line.split(","));

                Long id = Long.valueOf(items.get(0));
                String serialNumber = items.get(1);
                String name = items.get((2));
                int group = Integer.parseInt(items.get(3));

                //TODO Delete print of every student loaded from file
                Student student = new Student(serialNumber, name, group);
                student.setId(id);
                System.out.println(student);
                try {
                    super.save(student);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {
        Optional<Student> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile(entity);
        return Optional.empty();
    }

    private void saveToFile(Student entity) {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    entity.getId() + "," + entity.getSerialNumber() + "," + entity.getName() + "," + entity.getGroup());
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
        Iterable<Student> allEntities = (Set<Student>) super.findAll();
        String allStudents = "";
        for(Student student: allEntities){
            allStudents += student.toFileString();
        }

        flushFile();
        Path path = Paths.get(this.fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(allStudents);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.delete(id);
        updateFile();
        if( optional.isPresent())
            return optional;
        return optional;
    }

    public Optional<Student> update(Student entity) throws IllegalArgumentException {

        if (entity == null) {
            throw new IllegalArgumentException("InMemoryRepository > update: The entity must not be null.");
        }

        Map<Long,Student> entities = super.getEntities();

        if(entities.containsKey(entity.getId())) {
            entities.computeIfPresent(entity.getId(), (k, v) -> entity);
            updateFile();
            return null;
        }
        return Optional.ofNullable(entity);
    }
}
