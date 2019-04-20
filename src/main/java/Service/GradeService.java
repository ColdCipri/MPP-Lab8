package Service;

import domain.Grades;
import domain.validator.ValidatorException;
import repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeService {
    private Repository<Long, Grades> repository;

    public GradeService(Repository<Long, Grades> repository) {
        this.repository = repository;
    }

    public void addGrade(Grades grade) throws ValidatorException {
        repository.save(grade);
    }

    public Set<Grades> getAllGrades() {
        Iterable<Grades> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all students whose name contain the given string.
     *
     * @param problemId
     * @return
     */
    public Set<Grades> filterStudentsByProblem(int problemId) {
        Iterable<Grades> grades = repository.findAll();

        Set<Grades> filteredGrades = StreamSupport.stream(grades.spliterator(), false)
                .filter(grade -> grade.GetProblemId() == problemId).collect(Collectors.toSet());


        return filteredGrades;
    }
}
