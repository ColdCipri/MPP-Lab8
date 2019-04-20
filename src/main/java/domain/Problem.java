package domain;

public class Problem extends BaseEntity {
	private String problemDescription;
	private String problemName;
	private int problemDifficulty;
	
	
	
	public Problem() { }

	public Problem(String problemDescription, String problemName, int problemDifficulty) {
		this.problemDescription = problemDescription;
		this.problemName = problemName;
		this.problemDifficulty = problemDifficulty;
	}
	
	public Problem(Problem prob) {
		this.problemDescription = prob.problemDescription;
		this.problemName = prob.problemName;
		this.problemDifficulty = prob.problemDifficulty;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getProblemName() {
		return problemName;
	}

	public void setProblemName(String problemName) {
		this.problemName = problemName;
	}

	public int getProblemDifficulty() {
		return problemDifficulty;
	}

	public void setProblemDifficulty(int problemDifficulty) {
		this.problemDifficulty = problemDifficulty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = problemDescription.hashCode();
		result = prime * result + problemName.hashCode();
		result = prime * result + problemDifficulty;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Problem prob = (Problem) obj;
		
		if (problemDifficulty != prob.problemDifficulty) return false;

		if (!problemDescription.equals(prob.problemDescription)) return false;

		return problemName.equals(prob.problemName);
	}
}
