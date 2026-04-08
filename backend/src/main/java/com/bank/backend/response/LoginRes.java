package com.bank.backend.response;

public class LoginRes extends BasicRes {

	private String id;
	private String name;
	private String account;
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LoginRes() {
		super();
	}

	public LoginRes(int code, String message) {
		super(code, message);
	}

	public LoginRes(int code, String message, String id, String name, String account, String email) {
		super(code, message);
		this.id = id;
		this.name = name;
		this.account = account;
		this.email = email;
	}
}
