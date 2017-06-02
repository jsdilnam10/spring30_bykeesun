package chapter03.exercise.template08;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementStrategy {
	
	PreparedStatement makePreparedStatement(Connection c) throws SQLException;

}
