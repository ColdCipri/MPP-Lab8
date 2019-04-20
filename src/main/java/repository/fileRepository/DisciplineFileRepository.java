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

import domain.Discipline;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.InMemoryRepository;

public class DisciplineFileRepository extends InMemoryRepository<Discipline>{
	private String fileName;

    public DisciplineFileRepository(Validator<Discipline> validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        this.loadData();
    }

	private void loadData() {
		Path path = Paths.get(fileName);

        try {
            Files.lines(path).forEach(line -> {
               List<String> items = Arrays.asList(line.split(","));

               String title = items.get(1);
               int credits = Integer.parseInt(items.get(2));

               Discipline discipline = new Discipline(title, credits);
               discipline.setId(Long.parseLong(items.get(0)));
                try {
                    super.save(discipline);
                } catch (ValidatorException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	@Override
    public Optional<Discipline> save(Discipline entity) throws ValidatorException {
        Optional<Discipline> optional = super.save(entity);
        if (optional.isPresent()) {
            return optional;
        }
        saveToFile();
        return Optional.empty();
    }

    @Override
    public Optional<Discipline> delete(Long id) {
        Optional<Discipline> optional = super.delete(id);
        saveToFile();
        return optional;
    }

    private void saveToFile() {
        Path path = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            super.findAll().forEach(entity -> {
                try {
                    bufferedWriter.write(
                            entity.getId() + "," + entity.getTitle() + "," + entity.getCredits());
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

