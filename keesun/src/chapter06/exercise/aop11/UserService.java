package chapter06.exercise.aop11;

import java.util.List;

public interface UserService {

	void upgradeLevels() throws Exception;

	void add(User user);

	User get(String id);

	List<User> getAll();

	void deleteAll();

	void update(User user);

}