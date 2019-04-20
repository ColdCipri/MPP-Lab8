package domainTest;

import domain.Problem;

public class testProblem
{
    public void testProb()
    {
        Problem problem = new Problem("123ab","Alin",1);
        //problem.setId((long)10);
        assert (problem.getClass() == Problem.class);
        assert (problem.getProblemName().equals("Alin"));
        assert (problem.getProblemDescription().equals("123ab"));
        assert (problem.getProblemDifficulty()== 1);
        //assert (problem.getId() == (long)10);
    }
}
