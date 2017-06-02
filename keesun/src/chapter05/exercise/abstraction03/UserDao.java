package chapter05.exercise.abstraction03;

import java.util.List;

public interface UserDao {

	void deleteAll();

	int getCount();

	void add(User user);

	User get(String id);

	List<User> getAll();

	void update(User user);

}
