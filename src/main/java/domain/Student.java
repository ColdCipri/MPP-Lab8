package domain;

import Service.DisciplineService;
import domain.validator.DisciplineValidator;
import domain.validator.Validator;
import repository.InMemoryRepository;
import repository.Repository;

public class Student extends BaseEntity{

	private String serialNumber;
	private String name;
	private int group;
	
	Validator<Discipline> disciplineValidator;
	Repository<Long, Discipline> repo;
	DisciplineService disciplineService;
	
	public Student() {
		disciplineValidator = new DisciplineValidator();
		repo = new InMemoryRepository(disciplineValidator);
		disciplineService = new DisciplineService(repo);
	}
	
	public Student(String serialNumber, String name, int group) {
		this.serialNumber = serialNumber;
		this.name = name;
		this.group = group;
		disciplineValidator = new DisciplineValidator();
		repo = new InMemoryRepository(disciplineValidator);
		disciplineService = new DisciplineService(repo);
	}
	
	public Student(Student stud) {
		this.serialNumber = stud.serialNumber;
		this.name = stud.name;
		this.group = stud.group;
		this.disciplineService = stud.disciplineService;
		this.disciplineValidator = stud.disciplineValidator;
		this.repo = stud.repo;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = serialNumber.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + group;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (obj == null || getClass() != obj.getClass()) return false;
		
		Student student = (Student) obj;
		
		if (group != student.group) return false;
		
		if (!serialNumber.equals(student.serialNumber)) return false;
		
		return (!name.equals(student.name));
	}

	@Override
	public String toString() {
		return "Student {serialNumber = " + serialNumber + " | name = " + name + "| group = " + group + "}" + super.toString();
	}
	
	
}
