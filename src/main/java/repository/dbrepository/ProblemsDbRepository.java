package repository.dbrepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Problem;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.Repository;

public class ProblemsDbRepository implements Repository<Long, Problem>{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";
    
    private Validator<Problem> validator;

    public ProblemsDbRepository(Validator<Problem> validator) {
        this.validator = validator;
    }

	@Override
	public Optional<Problem> findOne(Long id) {
		Problem problem = null;
		String sql = "select * from problem" +
                	" where id=?";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Long uniqueId = resultSet.getLong("id");
				String problemDescription = resultSet.getString("problemDescription");
				String problemName = resultSet.getString("problemName");
				int problemDifficulty = resultSet.getInt("problemDifficulty");
				
				problem = new Problem(problemDescription, problemName, problemDifficulty);
				problem.setId(uniqueId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(problem);
	}

	@Override
	public Iterable<Problem> findAll() {
		List<Problem> problems = new ArrayList<>();
		String sql = "select * from problem";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Long uniqueId = resultSet.getLong("id");
				String problemDescription = resultSet.getString("problemDescription");
				String problemName = resultSet.getString("problemName");
				int problemDifficulty = resultSet.getInt("problemDifficulty");
				
				Problem problem = new Problem(problemDescription, problemName, problemDifficulty);
				problem.setId(uniqueId);
				problems.add(problem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return problems;
	}

	@Override
	public Optional<Problem> save(Problem entity) throws ValidatorException {
		validator.validate(entity);
		
		String sql = "insert into problem(id, problemDescription, problemName, problemDifficulty)" +
				"values (?,?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setLong(1, entity.getId());
			statement.setString(2, entity.getProblemDescription());
			statement.setString(3, entity.getProblemName());
			statement.setInt(4, entity.getProblemDifficulty());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(entity);
	}

	@Override
	public Optional<Problem> delete(Long id) {
		String sql = "delete from problem" +
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
	public Optional<Problem> update(Problem entity) throws ValidatorException {
		String sql = "update problem set problemDescription=?, problemName=?, problemDifficulty=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setString(1, entity.getProblemDescription());
			statement.setString(2, entity.getProblemName());
			statement.setInt(3, entity.getProblemDifficulty());
			statement.setLong(4, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(entity);
	}

}