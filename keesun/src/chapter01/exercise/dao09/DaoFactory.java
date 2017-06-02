package chapter01.exercise.dao09;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

	@Bean
	public UserDao userDao(){
		UserDao userDao = new UserDao(connectionMaker());
		return userDao;
	}

	@Bean
	public ConnectionMaker connectionMaker() {
		return new NConnectionMaker();
	}
	
}
