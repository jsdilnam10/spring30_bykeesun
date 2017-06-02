package chapter03.exercise.template04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy {

	public PreparedStatement makePreparedStatement(Connection c)
			throws SQLException {
		return c.prepareStatement("delete from users");
	}

}
