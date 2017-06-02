package chapter02.exercise.daotest04;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserDaoTest {

	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext applicationContext = new GenericXmlApplicationContext(
				"/chapter02/exercise/daotest04/daoContext.xml");
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		User user1 = new User("whiteship", "백기선", "married");
		User user2 = new User("helols", "김성윤", "isYoon");

		userDao.add(user1);
		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));

		User userGet1 = userDao.get("whiteship");
		assertThat(userGet1.getName(), is(user1.getName()));
		assertThat(userGet1.getPassword(), is(user1.getPassword()));

		User userGet2 = userDao.get("helols");
		assertThat(userGet2.getName(), is(user2.getName()));
		assertThat(userGet2.getPassword(), is(user2.getPassword()));
	}

	@Test
	public void count() throws SQLException {
		ApplicationContext applicationContext = new GenericXmlApplicationContext(
				"/chapter02/exercise/daotest04/daoContext.xml");
		UserDao userDao = applicationContext.getBean("userDao", UserDao.class);

		User user1 = new User("whiteship", "백기선", "married");
		User user2 = new User("helols", "김성윤", "실리콘밸리");
		User user3 = new User("miracle", "김정우", "fallInLove");

		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.add(user1);
		assertThat(userDao.getCount(), is(1));

		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));

		userDao.add(user3);
		assertThat(userDao.getCount(), is(3));
	}

}
