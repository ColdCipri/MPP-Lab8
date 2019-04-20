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

import domain.Problem;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.InMemoryRepository;

public class ProblemFileRepository extends InMemoryRepository<Problem>{
	private String fileName;

    public ProblemFileRepository(Validator<Problem> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        this.loadData();
    }

	private void loadData() {
		Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
               List<String> items = Arrays.asList(line.split(","));

               String problemDescription = items.get(1);
               String problemName = items.get(2);
               int problemDifficulty = Integer.parseInt(items.get(3));

               Problem problem = new Problem(problemDescription, problemName, problemDifficulty);
               problem.setId(Long.parseLong(items.get(0)));
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
	
	@Override
    public Optional<Problem> save(Problem entity) throws ValidatorException {
        Optional<Problem> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile();
        return Optional.empty();
    }

    @Override
    public Optional<Problem> delete(Long id) {
        Optional<Problem> optional = super.delete(id);
        saveToFile();
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            super.findAll().forEach(entity -> {
                try {
                    bufferedWriter.write(
                            entity.getId() + "," + entity.getProblemDescription() + "," + entity.getProblemName() + "," + entity.getProblemDifficulty());
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

