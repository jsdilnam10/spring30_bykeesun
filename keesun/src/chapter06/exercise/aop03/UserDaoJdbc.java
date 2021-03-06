package chapter06.exercise.aop03;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDaoJdbc implements UserDao {

	JdbcTemplate jdbcTemplate;

	private RowMapper<User> userMapper = new RowMapper<User>() {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
			user.setLevel(Level.valueOf(rs.getInt("level")));
			user.setLogin(rs.getInt("login"));
			user.setRecommend(rs.getInt("recommend"));
			user.setEmail(rs.getString("email"));
			return user;
		}
	};

	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void deleteAll() {
		jdbcTemplate.update("delete from users");
	}

	public int getCount() {
		return jdbcTemplate.queryForInt("select count(*) from users");
	}

	public void add(final User user) {
		jdbcTemplate
				.update(
						"insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)",
						user.getId(), user.getName(), user.getPassword(), user
								.getLevel().intValue(), user.getLogin(), user
								.getRecommend(), user.getEmail());
	}

	public User get(String id) {
		return jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] { id }, userMapper);
	}

	public List<User> getAll() {
		return jdbcTemplate
				.query("select * from users order by id", userMapper);
	}

	public void update(User user) {
		jdbcTemplate.update(
				"update users set name = ?, password = ?, level = ?, login = ?,"
						+ "recommend = ?, email = ? where id = ? ", user.getName(), user
						.getPassword(), user.getLevel().intValue(), user.getLogin(), user
						.getRecommend(), user.getEmail(), user.getId());
	}

}
