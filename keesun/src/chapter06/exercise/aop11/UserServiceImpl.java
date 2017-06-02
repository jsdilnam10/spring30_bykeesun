package chapter06.exercise.aop11;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class UserServiceImpl implements UserService {

	UserDao userDao;
	MailSender mailSender;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void upgradeLevels() throws Exception {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}

	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		sendUpgradeEMail(user);
	}

	private void sendUpgradeEMail(User user) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("admin@ksug.org");
		mailMessage.setSubject("업그레이드 안내");
		mailMessage.setText(user.getId() + "님의 등급이 " + user.getLevel()
				+ "로 업그레이드 됩니다.");
		mailSender.send(mailMessage);
	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= 50);
		case SILVER:
			return (user.getRecommend() >= 30);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level: " + currentLevel);
		}
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.add(user);
	}

	public void deleteAll() {
		userDao.deleteAll();
	}

	public User get(String id) {
		return userDao.get(id);
	}

	public List<User> getAll() {
		return userDao.getAll();
	}

	public void update(User user) {
		userDao.update(user);
	}

}
