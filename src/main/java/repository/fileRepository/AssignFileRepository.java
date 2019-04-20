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

import domain.AssignProblem;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.InMemoryRepository;

public class AssignFileRepository extends InMemoryRepository<AssignProblem>{
	private String fileName;

    public AssignFileRepository(Validator<AssignProblem> validator, String fileName) {
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

               AssignProblem assignProblem = new AssignProblem(problemId, studentId);
               assignProblem.setId(Long.parseLong(items.get(0)));
                try {
                    super.save(assignProblem);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
    public Optional<AssignProblem> save(AssignProblem entity) throws ValidatorException {
        Optional<AssignProblem> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile();
        return Optional.empty();
    }

    @Override
    public Optional<AssignProblem> delete(Long id) {
        Optional<AssignProblem> optional = super.delete(id);
        saveToFile();
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            super.findAll().forEach(entity -> {
                try {
                    bufferedWriter.write(
                            entity.getId() + "," + entity.getProblemId() + "," + entity.getStudentId() + "," + entity.getFrequency());
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

