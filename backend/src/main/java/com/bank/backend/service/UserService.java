package com.bank.backend.service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.backend.constants.ResMessage;
import com.bank.backend.dao.UserDao;
import com.bank.backend.entity.User;
import com.bank.backend.request.LoginReq;
import com.bank.backend.request.UserAddReq;
import com.bank.backend.response.BasicRes;
import com.bank.backend.response.LoginRes;

import jakarta.transaction.Transactional;

/**
 * 使用者業務邏輯層 (Business Logic)
 * 負責處理用戶註冊、身份驗證及帳號資訊管理
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	/**
	 * 用戶註冊
	 * 1. 生成唯一 UserID (UUID)
	 * 2. 隨機生成 10 位數銀行帳號
	 * 3. 使用 BCrypt 對標密碼進行加密
	 * 4. 呼叫 DAO 層之預存程序寫入資料庫
	 */
	@Transactional(rollbackOn = Exception.class)
	public BasicRes addUser(UserAddReq req) throws Exception {
		String userId = UUID.randomUUID().toString();
		String email = req.getEmail();
		String password = req.getPassword();
		String name = req.getName();
		String account = String.valueOf(
				ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L));

		try {
			int res = userDao.addUser(userId, email, encoder.encode(password), name, account);
			if (res == 1) {
				return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
			} else {
				return new BasicRes(ResMessage.REGISTRATION_ERROR.getCode(), ResMessage.REGISTRATION_ERROR.getMessage());
			}
		} catch (Exception e) {
			if (e.getMessage().contains("EMAIL_ALREADY_EXISTS")) {
				return new BasicRes(ResMessage.EMAIL_EXITS.getCode(), ResMessage.EMAIL_EXITS.getMessage());
			}
			throw e;
		}
	}

	/**
	 * 用戶登入驗證
	 * 1. 透過 Email 查詢用戶資訊
	 * 2. 比對加密後的密碼
	 * 3. 回傳包含用戶基本資訊的 LoginRes
	 */
	public BasicRes login(LoginReq req) {
		String email = req.getEmail();
		String password = req.getPassword();

		User user = userDao.getUserByEmail(email);

		if (user == null) {
			return new LoginRes(ResMessage.USER_NOT_FOUND.getCode(), ResMessage.USER_NOT_FOUND.getMessage());
		}
		
		// 驗證輸入密碼與 DB 加密密碼
		if (!encoder.matches(password, user.getPassword())) {
			return new LoginRes(ResMessage.PASSWORD_ERROR.getCode(), ResMessage.PASSWORD_ERROR.getMessage());
		}

		// 登入成功，回傳封裝好的用戶資料
		return new LoginRes(ResMessage.SUCCESS.getCode(), 
				ResMessage.SUCCESS.getMessage(),
				user.getUserId(),
				user.getUserName(),
				user.getAccount(),
				user.getEmail());
	}
}
