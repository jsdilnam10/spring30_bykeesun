package chapter01.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
	
	public void add(User user) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/spring30", "spring30", "spring30");
		
		PreparedStatement ps = connection.prepareStatement("insert into users(id, name, password) values (?, ?, ?)");
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		ps.execute();
		
		ps.close();
		connection.close();
	}
	
	public User get(String id) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/spring30", "spring30", "spring30");
		
		PreparedStatement ps = connection.prepareStatement("select * from users where id =?");
		ps.setString(1, id);
		ResultSet rs = ps.executeQuery();
		User user = new User();
		if(rs.next()){
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		return user;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		UserDao userDao = new UserDao();
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		userDao.add(user);
		
		System.out.println("확인!");
		
		User loadedUser = userDao.get("whiteship");
		System.out.println(loadedUser.getId());
		System.out.println(loadedUser.getName());
		System.out.println(loadedUser.getPassword());
		
		System.out.println("확인!!");
	}
	
}
