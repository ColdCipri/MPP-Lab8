package domain;

public class Discipline extends BaseEntity {
	private String title;
    private int credits;

    public Discipline() {
    }

    public Discipline(String title, int credits) {
        this.title = title;
        this.credits = credits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        //Discipline student = (Discipline) o;

        if (credits != ((Discipline) o).credits) return false;
        return true;
    }

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Discipline(Discipline disc)
    {
        this.title = disc.title;
        this.credits = disc.credits;
    }
    
    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + credits;
        return result;
    }

    @Override
    public String toString() {
        return "Discipline { Title = " + title + " | Credits='" + credits + "| }" + super.toString();
    }
}
