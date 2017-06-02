package chapter03.exercise.template03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public abstract class UserDao {
	
	DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public void executeQuery() throws SQLException{
		Connection connection = null;
		PreparedStatement statement = null;

		try{
			connection = dataSource.getConnection();
			statement = makeStatement(connection);
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

	protected abstract PreparedStatement makeStatement(Connection connection)
			throws SQLException;
	
}
