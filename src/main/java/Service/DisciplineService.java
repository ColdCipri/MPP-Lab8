package Service;

import domain.Discipline;
import domain.validator.ValidatorException;
import repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class DisciplineService
{
    private Repository<Long, Discipline> repository;

    public DisciplineService(Repository<Long, Discipline> repository) {
        this.repository = repository;
    }

    public void addStudent(Discipline discipline) throws ValidatorException {
        repository.save(discipline);
    }

    public Set<Discipline> getAllDisciplines() {
        Iterable<Discipline> discipline = repository.findAll();
        return StreamSupport.stream(discipline.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all students whose name contain the given string.
     *
     * @param s
     * @return
     */
    public Set<Discipline> filterStudentsByName(String s) {
        Iterable<Discipline> students = repository.findAll();

        Set<Discipline> filteredStudents = StreamSupport.stream(students.spliterator(), false)
                .filter(discipline -> discipline.getTitle().contains(s)).collect(Collectors.toSet());

        return filteredStudents;
    }
}
