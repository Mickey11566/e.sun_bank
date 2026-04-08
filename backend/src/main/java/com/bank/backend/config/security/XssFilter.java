package com.bank.backend.config.security;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * XssFilter:用來攔截所有進入系統的HTTP Request，並在進入Controller前進行防止XSS攻擊 
 */
@Component // 讓Spring自動註冊這個Filter
public class XssFilter implements Filter {

	/**
	 * 初始化方法（系統啟動時呼叫）
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// 目前沒有需要初始化的內容
	}

	/**
	 * 核心方法：每一個HTTP Request都會經過這裡
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 將原本的 HttpServletRequest 包裝成自訂的 XssRequestWrapper
		// 目的：在取得參數時自動進行XSS過濾
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		XssRequestWrapper wrappedRequest = new XssRequestWrapper(httpRequest);

		// 將「已包裝（已防護）」的 request 傳遞給後續流程（Controller）
		chain.doFilter(wrappedRequest, response);
	}

	/**
	 * 銷毀方法（系統關閉時呼叫）
	 */
	@Override
	public void destroy() {
		// 目前沒有需要釋放的資源
	}
}