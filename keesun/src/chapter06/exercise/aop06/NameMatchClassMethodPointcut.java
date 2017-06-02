package chapter06.exercise.aop06;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.util.PatternMatchUtils;

public class NameMatchClassMethodPointcut extends NameMatchMethodPointcut {

	public void setMappedClassName(String mappedClassName) {
		this.setClassFilter(new SimpleClassFileter(mappedClassName));
	}
	
	static class SimpleClassFileter implements ClassFilter {

		String mappedClassName;
		
		public SimpleClassFileter(String mappedClassName) {
			this.mappedClassName = mappedClassName;
		}

		public boolean matches(Class<?> clazz) {
			return PatternMatchUtils.simpleMatch(mappedClassName, clazz.getSimpleName());
		}
		
	}
	
}
