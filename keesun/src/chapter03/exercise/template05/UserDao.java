package chapter03.exercise.template05;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {
	
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void jdbcContextWithStatementStrategy(StatementStrategy strategy) throws SQLException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try{
			connection = dataSource.getConnection();
			statement = strategy.makePreparedStatement(connection);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
		
	}
	
	public void deleteAll() throws SQLException{
		StatementStrategy strategy = new DeleteAllStatement();
		jdbcContextWithStatementStrategy(strategy);
	}
	
	public int getCount() throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement("select count(*) from users");
			resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if(resultSet != null){
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	public void add(User user) throws SQLException {
		StatementStrategy strategy = new AddStatement(user);
		jdbcContextWithStatementStrategy(strategy);
	}

	public User get(String id) throws SQLException {
		Connection connection = dataSource.getConnection();

		PreparedStatement statement = connection
				.prepareStatement("select * from users where id = ?");
		statement.setString(1, id);

		ResultSet resultSet = statement.executeQuery();
		User user = null;
		if(resultSet.next()){
			user = new User();
			user.setId(resultSet.getString("id"));
			user.setName(resultSet.getString("name"));
			user.setPassword(resultSet.getString("password"));
		}

		resultSet.close();
		statement.close();
		connection.close();

		if(user == null) throw new EmptyResultDataAccessException(1);
		
		return user;
	}

}
