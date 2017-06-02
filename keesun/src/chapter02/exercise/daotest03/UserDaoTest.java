package chapter02.exercise.daotest03;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext applicationContext = new GenericXmlApplicationContext("/chapter02/exercise/daotest03/daoContext.xml");
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));
		
		User user = new User();
		user.setId("whiteship");
		user.setName("백기선");
		user.setPassword("married");
		
		userDao.add(user);
		assertThat(userDao.getCount(), is(1));
		
		User user2 = userDao.get("whiteship");
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
	
}
