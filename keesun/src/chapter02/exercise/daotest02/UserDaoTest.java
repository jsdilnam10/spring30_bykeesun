package chapter02.exercise.daotest02;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class UserDaoTest {
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext applicationContext = new GenericXmlApplicationContext("/chapter02/exercise/daotest02/daoContext.xml");
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		
		userDao.add(user);
		User user2 = userDao.get("whiteship");
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
	
	public static void main(String[] args) {
		JUnitCore.main("chapter02.exercise.daotest02.UserDaoTest");
	}

}
