package domain.validator;

import domain.Problem;

public class ProblemValidator implements Validator<Problem> {
	@Override
    public void validate(Problem entity) throws ValidatorException {
        if (entity.getId() <= 0)
        {
            throw new ValidatorException("Incorrect or negative id");
        }
        if (entity.getProblemDescription()=="")
        {
            throw  new ValidatorException("Incorrect serial number", new Throwable("Serial number empty"));
        }
        if (entity.getProblemName()=="")
        {
            throw new ValidatorException(new Throwable("Name empty"));
        }
    }
}
