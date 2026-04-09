package com.bank.backend.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class LoggingFilter implements Filter {

	private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;

		// 1. 先嘗試從 Authorization Header 拿 (標準做法)
		String user = req.getHeader("Authorization");

		// 2. 如果 Header 是空的，嘗試從 Query Parameter "account" 拿 (符合您目前的實作)
		if (user == null || user.isEmpty()) {
			user = req.getParameter("account");
		}

		// 3. 如果還是空的，可以標示為 "Anonymous" 或保持 null
		if (user == null) {
			user = "Anonymous";
		}

		log.info("User={} {} {} from IP={}", user, req.getMethod(), req.getRequestURI(), req.getRemoteAddr());
		
		try {
			UserContext.setUser(user);
			chain.doFilter(request, response);
		} finally {
			UserContext.clear();
		}
	}

}
