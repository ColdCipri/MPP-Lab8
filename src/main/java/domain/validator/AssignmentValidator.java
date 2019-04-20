package domain.validator;

import domain.AssignProblem;

public class AssignmentValidator implements Validator<AssignProblem> {
	 @Override
	    public void validate(AssignProblem entity) throws ValidatorException {
	        if (entity.getId() <= 0)
	        {
	            throw new ValidatorException("Incorrect or negative id");
	        }
	    }
}
