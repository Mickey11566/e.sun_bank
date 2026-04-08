package com.bank.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.backend.request.LoginReq;
import com.bank.backend.request.UserAddReq;
import com.bank.backend.response.BasicRes;
import com.bank.backend.service.UserService;

import org.springframework.web.bind.annotation.RequestBody;

/**
 * 使用者控制器 (Presentation Layer)
 * 提供外部 API 呼叫：註冊與登入功能
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	UserService userService;

	/**
	 * 用戶註冊
	 */
	@PostMapping
	public BasicRes createUser(@RequestBody UserAddReq req) throws Exception {
		return userService.addUser(req) ;
	}

	/**
	 * 用戶登入
	 */
	@PostMapping("/login")
	public BasicRes login(@RequestBody LoginReq req) {
		return userService.login(req);
	}

}
