package chapter06.exercise.aop12;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("testDaoContext.xml")
public class UserServiceTest {

	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;

	@Autowired
	UserService testUserService;

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
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		userService.upgradeLevels();

		checkLevelUpgraded(users.get(0), false);
		checkLevelUpgraded(users.get(1), true);
		checkLevelUpgraded(users.get(2), false);
		checkLevelUpgraded(users.get(3), true);
		checkLevelUpgraded(users.get(4), false);
	}

	private void checkLevelUpgraded(User user, boolean upgraded) {
		User userUpdate = userDao.get(user.getId());
		if (upgraded) {
			assertThat(userUpdate.getLevel(), is(user.getLevel().nextLevel()));
		} else {
			assertThat(userUpdate.getLevel(), is(user.getLevel()));
		}
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

	@Test
	public void upgradeAllOrNothing() throws Exception {
		userDao.deleteAll();
		for (User user : users)
			userDao.add(user);

		try {
			testUserService.upgradeLevels();
			fail("TestUserServiceException expected");
		} catch (TestUserServiceException e) {
		}

		checkLevelUpgraded(users.get(1), false);
	}
	
	@Test(expected=DataAccessException.class)
	public void readOnlyTransactionAttribute() {
		testUserService.getAll();
	}

	static class TestUserServiceImpl extends UserServiceImpl {
		private String id = "princekey7";

		protected void upgradeLevel(User user) {
			if (user.getId().equals(this.id))
				throw new TestUserServiceException();
			super.upgradeLevel(user);
		}

		public List<User> getAll() {
			for (User user : super.getAll()) {
				super.update(user); //여기서 에러 나야함.
			}
			return null;
		}
	}

	@SuppressWarnings("serial")
	static class TestUserServiceException extends RuntimeException {
	}

}
