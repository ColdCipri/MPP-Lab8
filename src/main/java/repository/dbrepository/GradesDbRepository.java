package repository.dbrepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Grades;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.Repository;

public class GradesDbRepository implements Repository<Long, Grades>{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "password";
    
    private Validator<Grades> validator;

    public GradesDbRepository(Validator<Grades> validator) {
        this.validator = validator;
    }

	@Override
	public Optional<Grades> findOne(Long id) {
		Grades grades = null;
		String sql = "select * from grades" +
                	" where id=?";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Long uniqueId = resultSet.getLong("id");
				int problemId = resultSet.getInt("problemId");
				int studentId = resultSet.getInt("studentId");
				int grade = resultSet.getInt("\"grade\"");
				
				grades = new Grades(problemId, studentId, grade);
				grades.setId(uniqueId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(grades);
	}

	@Override
	public Iterable<Grades> findAll() {
		List<Grades> allGrades = new ArrayList<>();
		String sql = "select * from grades";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {

				Long uniqueId = resultSet.getLong("id");
				int problemId = resultSet.getInt("problemId");
				int studentId = resultSet.getInt("studentId");
				int grade = resultSet.getInt("grade");
				
				Grades grades = new Grades(problemId, studentId, grade);
				grades.setId(uniqueId);
				allGrades.add(grades);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allGrades;
	}

	@Override
	public Optional<Grades> save(Grades entity) throws ValidatorException {
		validator.validate(entity);
		
		String sql = "insert into grades(id, problemId, studentId, \"grade\")" +
				"values (?,?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setLong(1, entity.getId());
			statement.setInt(2, entity.GetProblemId());
			statement.setInt(3, entity.GetStudentId());
			statement.setInt(4, entity.GetGrade());
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(entity);
	}

	@Override
	public Optional<Grades> delete(Long id) {
		String sql = "delete from grades" +
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
	public Optional<Grades> update(Grades entity) throws ValidatorException {
		String sql = "update grades set problemId=?, studentId=?, grade=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

        	statement.setLong(1, entity.getId());
			statement.setInt(2, entity.GetProblemId());
			statement.setInt(3, entity.GetStudentId());
			statement.setInt(4, entity.GetGrade());
			
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(entity);
	}

}