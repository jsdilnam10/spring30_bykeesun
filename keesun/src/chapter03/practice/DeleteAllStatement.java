package chapter03.practice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{

	public PreparedStatement makeStatement(Connection conn) throws SQLException {
		return conn.prepareStatement("delete from useres");
	}

}
