package chapter03.exercise.template10;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {

	DataSource dataSource;
	JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void deleteAll() {
		jdbcTemplate.update("delete from users");
	}

	public int getCount() {
		return jdbcTemplate.queryForInt("select count(*) from users");
	}

	public void add(final User user) {
		jdbcTemplate.update(
				"insert into users(id, name, password) values (?, ?, ?)", user
						.getId(), user.getName(), user.getPassword());
	}

	public User get(String id) {
		return jdbcTemplate.queryForObject("select * from users where id = ?",
				new Object[] { id }, new RowMapper<User>() {
					@Override
					public User mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setName(rs.getString("name"));
						user.setPassword(rs.getString("password"));
						return user;
					}
				});
	}

}
