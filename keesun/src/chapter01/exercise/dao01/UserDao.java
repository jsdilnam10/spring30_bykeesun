package chapter01.exercise.dao01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

	public void add(User user) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost/spring30", "spring30", "spring30");

		PreparedStatement statement = connection
				.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
		statement.setString(1, user.getId());
		statement.setString(2, user.getName());
		statement.setString(3, user.getPassword());
		statement.execute();

		statement.close();
		connection.close();
	}

	public User get(String id) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost/spring30", "spring30", "spring30");

		PreparedStatement statement = connection
				.prepareStatement("select * from users where id = ?");
		statement.setString(1, id);

		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		User user = new User();
		user.setId(resultSet.getString("id"));
		user.setName(resultSet.getString("name"));
		user.setPassword(resultSet.getString("password"));

		resultSet.close();
		statement.close();
		connection.close();

		return user;
	}

}
