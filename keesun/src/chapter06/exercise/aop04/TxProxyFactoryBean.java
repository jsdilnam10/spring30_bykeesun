package chapter06.exercise.aop04;

import java.lang.reflect.Proxy;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

public class TxProxyFactoryBean implements FactoryBean<Object>{

	Object target;
	Class<?> serviceInterface;
	String pattern;
	PlatformTransactionManager transactionManager;
	
	public void setTarget(Object target) {
		this.target = target;
	}

	public void setServiceInterface(Class<?> serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public Object getObject() throws Exception {
		TransactionHandler handler = new TransactionHandler();
		handler.setTarget(target);
		handler.setPattern(pattern);
		handler.setTransactionManager(transactionManager);
		return Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{serviceInterface}, handler);
	}

	public Class<?> getObjectType() {
		return serviceInterface;
	}

	public boolean isSingleton() {
		return false;
	}
	

}
