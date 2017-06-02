package chapter06.exercise.aop02;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class UserServiceTx implements UserService {

	UserService userService;
	PlatformTransactionManager transactionManager;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void add(User user) {
		userService.add(user);
	}

	public void upgradeLevels() throws Exception {
		TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try{
			userService.upgradeLevels();
			transactionManager.commit(ts);
		} catch(RuntimeException e) {
			transactionManager.rollback(ts);
			throw e;
		}
	}

}
