package com.bank.backend.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bank.backend.entity.User;

import java.util.List;

@Repository
public class UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/** 
	 * 新增用戶
	 */
	public int addUser(String user_id, String email, String password, String name, String account) {
		return jdbcTemplate.update("CALL sp_add_user(?, ?, ?, ?, ?)", 
				user_id, email, password, name, account);
	}

	/** 
	 * 透過 Email 查詢用戶
	 */
	public User getUserByEmail(String email) {
		List<User> users = jdbcTemplate.query("CALL sp_get_user_by_email(?)", 
				new BeanPropertyRowMapper<>(User.class), email);
		return users.isEmpty() ? null : users.get(0);
	}
}
