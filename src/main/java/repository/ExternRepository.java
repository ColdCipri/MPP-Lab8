package repository;


import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import domain.BaseEntity;
import domain.validator.Validator;
import domain.validator.ValidatorException;

public class ExternRepository <T extends BaseEntity> implements Repository<UUID, T> {
	private Map<UUID, T> entities;
	private Validator<T> validator;

	    @Override
	    public Optional<T> findOne(UUID id) {
	        if (id == null) {
	            throw new IllegalArgumentException("id must not be null");
	        }
	        return Optional.ofNullable(entities.get(id));
	    }

	    @Override
	    public Iterable<T> findAll() {
	        Set<T> allEntities = entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
	        return allEntities;
	    }

	    @Override
	    public Optional<T> save(T entity) throws ValidatorException {
	        return Optional.empty();
	    }

	    @Override
	    public Optional<T> delete(UUID id) {
	        return Optional.empty();
	    }

	    @Override
	    public Optional<T> update(T entity) throws ValidatorException {
	        return Optional.empty();
	    }
}
