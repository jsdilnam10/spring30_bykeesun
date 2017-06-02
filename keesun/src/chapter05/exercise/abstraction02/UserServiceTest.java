package chapter05.exercise.abstraction02;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("testDaoContext.xml")
public class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;

	List<User> users;

	@Before
	public void setUp() {
		users = Arrays.asList(new User("whiteship", "백기선", "p1", Level.BASIC,
				49, 0), new User("helols", "김성윤", "p2", Level.BASIC, 50, 0),
				new User("miracle", "김정우", "p3", Level.SILVER, 60, 29),
				new User("princekey7", "이진서", "p4", Level.SILVER, 60, 30),
				new User("arawn", "박용권", "p5", Level.GOLD, 100, 100));
	}

	@Test
	public void upgradeLevels() {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		userService.upgradeLevels();

		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), Level.SILVER);
		checkLevel(users.get(2), Level.SILVER);
		checkLevel(users.get(3), Level.GOLD);
		checkLevel(users.get(4), Level.GOLD);
	}

	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.get(user.getId());
		assertThat(userUpdate.getLevel(), is(expectedLevel));
	}
	
	@Test  
	public void add() { 
		userDao.deleteAll();

		User userWithLevel = users.get(4);
		User userWithoutLevel = users.get(0);
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		User userWithLevelRead = userDao.get(userWithLevel.getId());
		User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		assertThat(userWithoutLevelRead.getLevel(), is(Level.BASIC));
	} 

}
