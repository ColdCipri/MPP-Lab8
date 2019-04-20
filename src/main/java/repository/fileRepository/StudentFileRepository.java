package repository.fileRepository;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import domain.Student;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.InMemoryRepository;

public class StudentFileRepository extends InMemoryRepository<Student>{
	private String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        this.loadData();
    }

	private void loadData() {
		Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
               List<String> items = Arrays.asList(line.split(","));

               String serialNumber = items.get(1);
               String name = items.get(2);
               int group = Integer.parseInt(items.get(3));

               Student student = new Student(serialNumber, name, group);
               student.setId(Long.parseLong(items.get(0)));
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
        saveToFile();
        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.delete(id);
        saveToFile();
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            super.findAll().forEach(entity -> {
                try {
                    bufferedWriter.write(
                            entity.getId() + "," + entity.getSerialNumber() + "," + entity.getName() + "," + entity.getGroup());
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
