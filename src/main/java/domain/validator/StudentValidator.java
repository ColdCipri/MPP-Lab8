package domain.validator;

import domain.Student;

public class StudentValidator implements Validator<Student> {

	@Override
	public void validate(Student entity) throws ValidatorException {
		if (entity.getId() == 0 || entity.getId() < 0) {
			throw new ValidatorException("Incorrect or negative id");
		}
		if (entity.getSerialNumber()=="")
        {
            throw  new ValidatorException("Incorrect serial number", new Throwable("Serial number empty"));
        }
        if (entity.getName()=="")
        {
            throw new ValidatorException(new Throwable("Name empty"));
        }
		
	}

}
