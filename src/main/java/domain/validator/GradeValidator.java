package domain.validator;

import domain.Grades;

public class GradeValidator implements Validator<Grades> {
	@Override
	public void validate(Grades entity) throws ValidatorException {
	    if (entity.getId() <= 0)
	    {
	        throw new ValidatorException("Incorrect or negative id");
	    }
	}
}
