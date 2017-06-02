package chapter06.exercise.aop07;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionAdvice implements MethodInterceptor {
	
	PlatformTransactionManager transactionManager;
	
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		TransactionStatus ts = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			Object ret = invocation.proceed();
			transactionManager.commit(ts);
			return ret;
		} catch (RuntimeException e) {
			transactionManager.rollback(ts);
			throw e;
		}
	}

}
