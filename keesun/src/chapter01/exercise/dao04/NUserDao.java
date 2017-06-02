package chapter01.exercise.dao04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NUserDao extends UserDao {

	@Override
	protected Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(
				"jdbc:postgresql://localhost/spring30", "spring30", "spring30");
		return connection;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao userDao = new NUserDao();

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");

		userDao.add(user);

		System.out.println("User 등록 성공!");

		User user2 = userDao.get("whiteship");
		System.out.println(user2.getName());
		System.out.println(user2.getPassword());
		System.out.println(user2.getId() + " 조회 성공");
	}
	
}
