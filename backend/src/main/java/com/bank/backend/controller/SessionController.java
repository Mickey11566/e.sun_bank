package com.bank.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.backend.request.LoginReq;
import com.bank.backend.response.BasicRes;
import com.bank.backend.service.UserService;

/**
 * 使用者登入
 */
@CrossOrigin
@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private UserService userService;

    /**
     * 建立 Session
     */
    @PostMapping
    public BasicRes login(@RequestBody LoginReq req) {
        return userService.login(req);
    }
}
