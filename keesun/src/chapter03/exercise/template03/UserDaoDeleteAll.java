package chapter03.exercise.template03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends UserDao{

	protected PreparedStatement makeStatement(Connection connection)
			throws SQLException {
		return connection.prepareStatement("delete from users");
	}

}
