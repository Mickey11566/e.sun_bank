package com.bank.backend.constants;

public enum ResMessage {
	SUCCESS(200, "成功"), 
	REGISTRATION_ERROR(400, "註冊失敗"),
	EMAIL_EXITS(400, "信箱已被註冊"),
	PASSWORD_ERROR(400,"密碼錯誤"),
	USER_NOT_FOUND(404, "查無此用戶");

	private int code;

	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;
	}

}
