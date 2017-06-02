package chapter03.exercise.template09;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class JdbcContext {

	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void workWithStatementStrategy(StatementStrategy strategy)
			throws SQLException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = dataSource.getConnection();
			statement = strategy.makePreparedStatement(connection);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

	}
	
	public void executeSql(final String sql) throws SQLException {
		workWithStatementStrategy(new StatementStrategy() {
			public PreparedStatement makePreparedStatement(Connection c)
					throws SQLException {
				return c.prepareStatement(sql);
			}
		});
	}

}
