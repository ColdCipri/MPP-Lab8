package repository.dbrepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import domain.Student;
import domain.validator.Validator;
import domain.validator.ValidatorException;
import repository.Repository;

public class StudentDbRepository implements Repository<Long, Student>{

	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USERNAME = "postgres"; //System.getProperty("username");
    private static final String PASSWORD = "password"; //System.getProperty("password");
    
    private Validator<Student> validator;

    public StudentDbRepository(Validator<Student> validator) {
        this.validator = validator;
    }

	@Override
	public Optional<Student> findOne(Long id) {
		Student student = null;
		String sql = "select * from student" +
                	" where id=?";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id.toString());
			ResultSet resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				Long uniqueId = Long.valueOf(resultSet.getString("id"));
				String serialNumber = resultSet.getString("serialNumber");
				String name = resultSet.getString("name");
				int group = resultSet.getInt("\"group\"");
				
				student = new Student(serialNumber, name, group);
				student.setId(uniqueId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(student);
	}

	@Override
	public Iterable<Student> findAll() {
		List<Student> students = new ArrayList<>();
		String sql = "select * from student";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				Long uniqueId = Long.valueOf(resultSet.getString("id"));
				String serialNumber = resultSet.getString("serialNumber");
				String name = resultSet.getString("name");
				int group = resultSet.getInt("group");
				
				Student student = new Student(serialNumber, name, group);
				student.setId(uniqueId);
				students.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public Optional<Student> save(Student entity) throws ValidatorException {
		validator.validate(entity);
		
		String sql = "insert into student(id, serialNumber, name, \"group\")" +
				"values (?,?,?,?)";
		
		try (Connection connection = DriverManager.getConnection(URL, USERNAME,
				PASSWORD)){
			PreparedStatement statement = connection.prepareStatement(sql);
	
			statement.setLong(1, entity.getId());
			statement.setString(2, entity.getSerialNumber());
			statement.setString(3, entity.getName());
			statement.setInt(4, entity.getGroup());
			
			System.out.println(statement.toString());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
		return Optional.of(entity);
	}

	@Override
	public Optional<Student> delete(Long id) {
		String sql = "delete from student" +
					"where id=?";
		try (Connection connection = DriverManager.getConnection(URL, USERNAME,
				PASSWORD);
			PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, String.valueOf(id));
			statement.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		return Optional.empty();
	}

	@Override
	public Optional<Student> update(Student entity) throws ValidatorException {
		String sql = "update student set serialNumber=?, name=?, group=? where id=?";
        try (Connection connection = DriverManager.getConnection(URL, USERNAME,
                PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, entity.getSerialNumber());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getGroup());
            statement.setLong(4, entity.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(entity);
	}

}