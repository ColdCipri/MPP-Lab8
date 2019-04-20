package repository.dbrepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.AssignProblem;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.Repository;

public class AssignProblemDbRepository implements Repository<Long, AssignProblem>{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";
    
    private Validator<AssignProblem> validator;

    public AssignProblemDbRepository(Validator<AssignProblem> validator) {
        this.validator = validator;
    }

	@Override
	public Optional<AssignProblem> findOne(Long id) {
		AssignProblem assignProblem = null;
		String sql = "select * from AssignProblems" +
                	" where id=?";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Long uniqueId = resultSet.getLong("id");
				int problemId = resultSet.getInt("problemId");
				int studentId = resultSet.getInt("studentId");
				
				assignProblem = new AssignProblem(problemId, studentId);
				assignProblem.setId(uniqueId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(assignProblem);
	}

	@Override
	public Iterable<AssignProblem> findAll() {
		List<AssignProblem> assignProblems = new ArrayList<>();
		String sql = "select * from AssignProblems";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {

				Long uniqueId = resultSet.getLong("id");
				int problemId = resultSet.getInt("problemId");
				int studentId = resultSet.getInt("studentId");
				
				AssignProblem assignProblem = new AssignProblem(problemId, studentId);
				assignProblem.setId(uniqueId);
				assignProblems.add(assignProblem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return assignProblems;
	}

	@Override
	public Optional<AssignProblem> save(AssignProblem entity) throws ValidatorException {
		validator.validate(entity);
		
		String sql = "insert into AssignProblems(id, problemId, studentId)" +
				"values (?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setLong(1, entity.getId());
			statement.setInt(2, entity.getProblemId());
			statement.setInt(3, entity.getStudentId());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(entity);
	}

	@Override
	public Optional<AssignProblem> delete(Long id) {
		String sql = "delete from AssignProblems" +
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
	public Optional<AssignProblem> update(AssignProblem entity) throws ValidatorException {
		String sql = "update AssignProblems set problemId=?, studentId=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, entity.getProblemId());
            statement.setInt(2, entity.getStudentId());
            statement.setLong(3, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(entity);
	}

}