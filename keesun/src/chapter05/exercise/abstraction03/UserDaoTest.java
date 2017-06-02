package chapter05.exercise.abstraction03;

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
		user1 = new User("whiteship", "백기선", "married", Level.BASIC, 1, 0);
		user2 = new User("helols", "김성윤", "isYoon", Level.SILVER, 55, 10);
		user3 = new User("miracle", "김정우", "fallInLove", Level.GOLD, 100, 40);
	}
	
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		userDao.deleteAll();
		assertThat(userDao.getCount(), is(0));
		
		userDao.add(user1);
		userDao.add(user2);
		assertThat(userDao.getCount(), is(2));
		
		User userGet1 = userDao.get("whiteship");
		checkSameUser(user1, userGet1);
		User userGet2 = userDao.get("helols");
		checkSameUser(user2, userGet2);
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
	
	@Test
	public void update(){
		userDao.deleteAll();
		
		userDao.add(user1);
		userDao.add(user2);
		
		user1.setName("모나리자");
		user1.setPassword("123");
		user1.setLevel(Level.GOLD);
		user1.setLogin(1000);
		user1.setRecommend(500);
		userDao.update(user1);
		
		User updatedUser = userDao.get(user1.getId());
		checkSameUser(user1, updatedUser);
		User user2same = userDao.get(user2.getId());
		checkSameUser(user2, user2same);
	}
	
	private void checkSameUser(User user, User loadedUser){
		assertThat(user.getId(), is(loadedUser.getId()));
		assertThat(user.getName(), is(loadedUser.getName()));
		assertThat(user.getPassword(), is(loadedUser.getPassword()));
		assertThat(user.getLevel(), is(loadedUser.getLevel()));
		assertThat(user.getLogin(), is(loadedUser.getLogin()));
		assertThat(user.getRecommend(), is(loadedUser.getRecommend()));
	}
	
}
