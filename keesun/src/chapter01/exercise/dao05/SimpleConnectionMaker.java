package chapter01.exercise.dao05;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMaker {

	public Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost/spring30", "spring30", "spring30");
		return connection;
	}

}
