package domain.validator;

import domain.Discipline;

public class DisciplineValidator implements Validator<Discipline> {
	@Override
    public void validate(Discipline entity) throws ValidatorException
    {
        if (entity.getId() <= 0)
        {
            throw new ValidatorException("Incorrect or negative id");
        }
    }
}
