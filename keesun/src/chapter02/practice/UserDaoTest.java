package chapter02.practice;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDaoTest {
	
	UserDao userDao;
	
	User user1;
	User user2;
	User user3;
	
	@Before
	public void setUp(){
		user1 = new User("whiteship", "백기선", "married");
		user2 = new User("helols", "김성윤", "isYoon");
		user3 = new User("miracle", "김정우", "fallInLove");
	}

	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));
		
		userDao.add(user1);
		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));

		User userGet1 = userDao.get("whiteship");
		confirmUser(userGet1, user1);

		User userGet2 = userDao.get("helols");
		confirmUser(userGet2, user2);
	}

	private void confirmUser(User userGet1, User user) {
		assertThat(userGet1.getId(), is(user.getId()));
		assertThat(userGet1.getName(), is(user.getName()));
		assertThat(userGet1.getPassword(), is(user.getPassword()));
	}

	@Test
	public void count() throws SQLException {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));

		userDao.add(user1);
		assertThat(userDao.getCount(), is(1));

		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));

		userDao.add(user3);
		assertThat(userDao.getCount(), is(3));
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException {
		userDao.deleteAll();
		userDao.get("sldkfjsldkfs");
	}

}
