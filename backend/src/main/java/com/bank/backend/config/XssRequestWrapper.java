package com.bank.backend.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

/**
 * XssRequestWrapper：包裝原始HttpServletRequest，攔截所有取得輸入資料的方法，並防止XSS攻擊
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

	public XssRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	/**
	 * 攔截多個參數
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		// 對每一個值進行XSS清洗
		String[] encodedValues = new String[values.length];

		for (int i = 0; i < values.length; i++) {
			encodedValues[i] = sanitize(values[i]);
		}

		return encodedValues;
	}

	/**
	 * 攔截單一參數
	 */
	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);

		if (value == null) {
			return null;
		}

		// 回傳經過過濾的內容
		return sanitize(value);
	}

	/**
	 * 攔截藏在HTTP Header的攻擊
	 */
	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);

		if (value == null) {
			return null;
		}

		return sanitize(value);
	}

	/**
	 * XSS 清洗方法： 將可能造成XSS攻擊的字元或語法轉換或移除
	 */
	private String sanitize(String value) {
		if (value == null) {
			return null;
		}

		/*
		 * 移除所有script標籤及其包含的程式碼內容 (?i) 代表不區分大小寫，.*?
		 * 為非全碼比對，確保精準配對到最近的</script>結束標籤，避免誤刪正常內容
		 */
		value = value.replaceAll("(?i)<script.*?>.*?</script>", "");

		// 移除字串中出現的javascript:
		// 防止攻擊者利用超連結（href="javascript:..."）或圖片（src="javascript:..."）等屬性
		value = value.replaceAll("(?i)javascript:", "");

		return value;
	}
}