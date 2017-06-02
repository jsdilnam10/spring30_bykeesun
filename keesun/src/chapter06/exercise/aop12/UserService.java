package chapter06.exercise.aop12;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserService {

	void upgradeLevels() throws Exception;

	void add(User user);

	@Transactional(readOnly = true)
	User get(String id);

	@Transactional(readOnly = true)
	List<User> getAll();

	void deleteAll();

	void update(User user);

}