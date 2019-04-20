package domain;

public class AssignProblem extends BaseEntity {
	 private int problemId;
	    private int studentId;
	    private int frequency;

	    public AssignProblem()
	    {
	        frequency = 0;
	    }

	    public AssignProblem(int pid, int sid)
	    {
	        this.problemId = pid;
	        this.studentId = sid;
	        this.frequency = 0;
	    }

	    public AssignProblem(AssignProblem ap)
	    {
	        this.problemId = ap.problemId;
	        this.studentId = ap.studentId;
	        this.frequency = ap.frequency;
	        //this.setId(ap.getId());
	    }

	    public int getProblemId() {
			return problemId;
		}

		public void setProblemId(int problemId) {
			this.problemId = problemId;
		}

		public int getStudentId() {
			return studentId;
		}

		public void setStudentId(int studentId) {
			this.studentId = studentId;
		}

		public int getFrequency() {
			return frequency;
		}

		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}

		public void IncFrequency() {this.frequency++;}


	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        AssignProblem ap = (AssignProblem) o;

	        if (problemId != ap.problemId) return false;
	        if (studentId != ap.studentId) return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int result = problemId;
	        result = 31 * result + studentId;
	        return result;
	    }

	    @Override
	    public String toString() {
	        return "AssignedProblem { Problem = " + problemId + " | Student = " + studentId + " }" + super.toString();
	    }
	}
