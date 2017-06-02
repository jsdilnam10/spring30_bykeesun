package chapter01.exercise.dao11;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class IoCTest {

	@Test
	public void ioc(){
		DaoFactory daoFactory = new DaoFactory();
		System.out.println(daoFactory.userDao());
		System.out.println(daoFactory.userDao());
		
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(DaoFactory.class);
		System.out.println(applicationContext.getBean("userDao", UserDao.class));
		System.out.println(applicationContext.getBean("userDao", UserDao.class));
	}
	
}
