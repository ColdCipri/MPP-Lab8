package Service;

import domain.AssignProblem;
import repository.Repository;
import domain.validator.ValidatorException;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AssignmentService
{
    private Repository<Long, AssignProblem> repository;

    public AssignmentService(Repository<Long, AssignProblem> repository) {
        this.repository = repository;
    }

    public void addAP(AssignProblem ap) throws ValidatorException {
        Iterable<AssignProblem> aps = repository.findAll();
        for (AssignProblem aps2 : aps)
        {
            if (aps2.getProblemId() == ap.getProblemId())
            {
                aps2.IncFrequency();
                return;
            }
        }

        repository.save(ap);
    }

    public Set<AssignProblem> getAllStudents() {
        Iterable<AssignProblem> students = repository.findAll();
        return StreamSupport.stream(students.spliterator(), false).collect(Collectors.toSet());
    }

    public Optional<AssignProblem> filterAssignments() {
        Iterable<AssignProblem> aps = repository.findAll();

        Optional<AssignProblem> filterAP = StreamSupport.stream(aps.spliterator(), false)
                .max((a, b) -> a.getFrequency() - b.getFrequency());

        return filterAP;
    }
}
