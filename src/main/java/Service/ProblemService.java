package Service;

import domain.Problem;
import domain.validator.ValidatorException;
import repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProblemService {
    private Repository<Long, Problem> repository;

    public ProblemService(Repository<Long, Problem> repository) {
        this.repository = repository;
    }

    public void addProblem(Problem prob) throws ValidatorException {
        repository.save(prob);
    }

    public Set<Problem> getAllProblems() {
        Iterable<Problem> problems = repository.findAll();
        return StreamSupport.stream(problems.spliterator(), false).collect(Collectors.toSet());
    }


    /**
     * Returns all problems whose name contain the given string.
     *
     * @param s
     * @return
     */
    public Set<Problem> filterProblemByName(String s) {
        Iterable<Problem> problems = repository.findAll();
        Set<Problem> filteredProblems = StreamSupport.stream(problems.spliterator(), false)
                .filter(student -> student.getProblemName().contains(s)).collect(Collectors.toSet());

//        problems.forEach(filteredProblem::add);
//        filteredProblem.removeIf(problem -> !problem.getProblemName().contains(s));

        return filteredProblems;
    }

    public Set<Problem> filterProblemByDifficulty(int diff)
    {
        Iterable<Problem> problems = repository.findAll();

        Set<Problem> filteredProblems = StreamSupport.stream(problems.spliterator(), false)
                .filter(problem -> problem.getProblemDifficulty()==diff).collect(Collectors.toSet());

//        problems.forEach(filteredProblem::add);
//        filteredProblem.removeIf(problem -> !(problem.getProblemDifficulty() == diff));

        return filteredProblems;
    }

    public Set<Problem> filterProblemByDescription(String s)
    {
        Iterable<Problem> problems = repository.findAll();

        Set<Problem> filteredProblem = StreamSupport.stream(problems.spliterator(), false)
                .filter(problem -> problem.getProblemDescription().contains(s)).collect(Collectors.toSet());

        //problems.forEach(filteredProblem::add);
        //filteredProblem.removeIf(problem -> !(problem.getProblemDescription().contains(s)));

        return filteredProblem;
    }
}
