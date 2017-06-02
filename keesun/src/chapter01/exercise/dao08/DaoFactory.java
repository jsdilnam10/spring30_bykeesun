package chapter01.exercise.dao08;


public class DaoFactory {

	public UserDao userDao(){
		UserDao userDao = new UserDao(connectionMaker());
		return userDao;
	}

	private ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
	
}
