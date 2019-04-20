package Service;

import domain.Student;
import repository.Repository;
import domain.validator.ValidatorException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentService {
    private Repository<Long, Student> repository;

    public StudentService(Repository<Long, Student> repository) {
        this.repository = repository;
    }

    public void addStudent(Student student) throws ValidatorException {
        repository.save(student);
    }

    public Set<Student> getAllStudents() {
        Iterable<Student> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    /**
     * Returns all students whose name contain the given string.
     *
     * @param s
     * @return
     */
    public Set<Student> filterStudentsByName(String s) {
        Iterable<Student> students = repository.findAll();

        Set<Student> filteredStudents = StreamSupport.stream(students.spliterator(), false)
                .filter(student -> student.getName().contains(s)).collect(Collectors.toSet());

        return filteredStudents;
    }
}
