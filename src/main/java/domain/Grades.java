package domain;

public class Grades extends BaseEntity {
	 private int problemId;
	    private int studentId;
	    private int grade;

	    public Grades()
	    {
	    }

	    public Grades(int problemId, int studentId, int grade)
	    {
	        this.grade = grade;
	        this.problemId = problemId;
	        this.studentId = studentId;
	    }

	    public Grades(Grades grade)
	    {
	        this.grade = grade.grade;
	        this.problemId = grade.problemId;
	        this.studentId = grade.studentId;
	    }

	    public int GetGrade() {return grade;}
	    
	    public void SetGrade(int gr) {this.grade = gr;}

	    public void SetId(Long id) {this.setId(id);}

	    public int GetProblemId() {return problemId;}

	    public int GetStudentId() {return studentId;}
	    
	    public void SetProblemId(int id) {this.problemId = id;}
	    
	    public void SetStudentId(int id) {this.studentId = id;}

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        Grades gradeObj = (Grades) o;

	        if (problemId != gradeObj.problemId) return false;
	        if (studentId != gradeObj.studentId) return false;
	        if (grade != gradeObj.grade) return false;

	        return true;
	    }

	    @Override
	    public int hashCode() {
	        int result = problemId;
	        result = 31 * result + studentId;
	        result = 31 * result + grade;
	        return result;
	    }

	    @Override
	    public String toString() {
	        return "Grade{" +
	                "problemId ='" + problemId + '\'' +
	                ", studentId ='" + studentId + '\'' +
	                ", grade =" + grade +
	                "} " + super.toString();
	    }
	}
