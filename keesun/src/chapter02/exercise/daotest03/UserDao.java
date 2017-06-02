package chapter02.exercise.daotest03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UserDao {
	
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void deleteAll() throws SQLException{
		Connection connection = dataSource.getConnection();

		PreparedStatement statement = connection.prepareStatement("delete from users");
		statement.executeUpdate();
		
		statement.close();
		connection.close();
	}
	
	public int getCount() throws SQLException {
		Connection connection = dataSource.getConnection();
		
		PreparedStatement statement = connection.prepareStatement("select count(*) from users");
		
		ResultSet resultSet = statement.executeQuery();
		resultSet.next();
		int count = resultSet.getInt(1);
		
		resultSet.close();
		statement.close();
		connection.close();
		
		return count;
	}

	public void add(User user) throws ClassNotFoundException, SQLException {
		Connection connection = dataSource.getConnection();

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
		Connection connection = dataSource.getConnection();

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
