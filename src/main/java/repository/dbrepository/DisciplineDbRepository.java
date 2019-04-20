package repository.dbrepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Discipline;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.Repository;

public class DisciplineDbRepository implements Repository<Long, Discipline>{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";
    
    private Validator<Discipline> validator;

    public DisciplineDbRepository(Validator<Discipline> validator) {
        this.validator = validator;
    }

	@Override
	public Optional<Discipline> findOne(Long id) {
		Discipline discipline = null;
		String sql = "select * from Discipline" +
                	" where id=?";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Long uniqueId = resultSet.getLong("id");
				String title = resultSet.getString("title");
				int credits = resultSet.getInt("credits");
				
				discipline = new Discipline(title, credits);
				discipline.setId(uniqueId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(discipline);
	}

	@Override
	public Iterable<Discipline> findAll() {
		List<Discipline> disciplines = new ArrayList<>();
		String sql = "select * from Discipline";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Long uniqueId = resultSet.getLong("id");
				String title = resultSet.getString("title");
				int credits = resultSet.getInt("credits");
				
				Discipline discipline = new Discipline(title, credits);
				discipline.setId(uniqueId);
				disciplines.add(discipline);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return disciplines;
	}

	@Override
	public Optional<Discipline> save(Discipline entity) throws ValidatorException {
		validator.validate(entity);
		
		String sql = "insert into Discipline(id, title, credits)" +
				"values (?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setLong(1, entity.getId());
			statement.setString(2, entity.getTitle());
			statement.setInt(3, entity.getCredits());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(entity);
	}

	@Override
	public Optional<Discipline> delete(Long id) {
		String sql = "delete from Discipline" +
					"where id=?";
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setLong(1, id);
			statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return Optional.empty();
	}

	@Override
	public Optional<Discipline> update(Discipline entity) throws ValidatorException {
		String sql = "update Discipline set title=?, credits=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setLong(1, entity.getId());
			statement.setString(2, entity.getTitle());
			statement.setInt(3, entity.getCredits());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(entity);
	}

}