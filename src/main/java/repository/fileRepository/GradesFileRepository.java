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

import domain.Grades;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.InMemoryRepository;

public class GradesFileRepository extends InMemoryRepository<Grades>{
	private String fileName;

    public GradesFileRepository(Validator<Grades> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        this.loadData();
    }

	private void loadData() {
		Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
               List<String> items = Arrays.asList(line.split(","));

               int problemId = Integer.parseInt(items.get(1));
               int studentId = Integer.parseInt(items.get(2));
               int grade = Integer.parseInt(items.get(3));

               Grades grades = new Grades(problemId, studentId, grade);
               grades.setId(Long.parseLong(items.get(0)));
                try {
                    super.save(grades);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
    public Optional<Grades> save(Grades entity) throws ValidatorException {
        Optional<Grades> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile();
        return Optional.empty();
    }

    @Override
    public Optional<Grades> delete(Long id) {
        Optional<Grades> optional = super.delete(id);
        saveToFile();
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            super.findAll().forEach(entity -> {
                try {
                    bufferedWriter.write(
                            entity.getId() + "," + entity.GetProblemId() + "," + entity.GetStudentId() + "," + entity.GetGrade());
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

