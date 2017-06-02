package chapter05.exercise.abstraction01;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("testDaoContext.xml")
public class UserDaoTest {
	
	@Autowired UserDao userDao;
	User user1;
	User user2;
	User user3;
	
	
	@Before
	public void setUp() throws ClassNotFoundException{
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
		assertThat(userGet1.getName(), is(user1.getName()));
		assertThat(userGet1.getPassword(), is(user1.getPassword()));
		
		User userGet2 = userDao.get("helols");
		assertThat(userGet2.getName(), is(user2.getName()));
		assertThat(userGet2.getPassword(), is(user2.getPassword()));
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
		userDao.get("someone");
	}
	
	@Test
	public void getAll(){
		userDao.deleteAll();
		
		userDao.add(user1);
		List<User> userList1 = userDao.getAll();
		assertThat(userList1.size(), is(1));
		checkSameUser(user1, userList1.get(0));
		
		userDao.add(user2);
		List<User> userList2 = userDao.getAll();
		assertThat(userList2.size(), is(2));
		checkSameUser(user2, userList2.get(0));
		checkSameUser(user1, userList2.get(1));

		userDao.add(user3);
		List<User> userList3 = userDao.getAll();
		assertThat(userList3.size(), is(3));
		checkSameUser(user2, userList3.get(0));
		checkSameUser(user3, userList3.get(1));
		checkSameUser(user1, userList3.get(2));
	}
	
	private void checkSameUser(User user, User loadedUser){
		assertThat(user.getId(), is(loadedUser.getId()));
		assertThat(user.getName(), is(loadedUser.getName()));
		assertThat(user.getPassword(), is(loadedUser.getPassword()));
	}
	
}
