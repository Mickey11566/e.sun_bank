package com.bank.backend.config.security;

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
	 * 攔截取得多個參數值（例如 checkbox、multi-select）
	 */
	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);

		if (values == null) {
			return null;
		}

		// 對每一個值進行XSS清洗
		int count = values.length;
		String[] encodedValues = new String[count];

		for (int i = 0; i < count; i++) {
			encodedValues[i] = sanitize(values[i]);
		}

		return encodedValues;
	}

	/**
	 * 攔截取得單一參數值（例如表單欄位）
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
		if (value != null) {

			// < > 轉成HTML Entity，避免被當作HTML標籤執行
			value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

			// 防止JavaScript函數呼叫 ( )
			value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");

			// 防止字串注入
			value = value.replaceAll("'", "&#39;");

			// 移除eval(...)
			value = value.replaceAll("eval\\((.*)\\)", "");

			// 防止javascript:URL攻擊
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");

			// 移除script關鍵字
			value = value.replaceAll("script", "");
		}

		return value;
	}
}