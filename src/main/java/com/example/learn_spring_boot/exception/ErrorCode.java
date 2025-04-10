package com.example.learn_spring_boot.exception;

public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Exception"),
	USER_EXISTED(1001, "User already existed"),
	USERNAME_INVALID(1002, "Username must be at least 6 characters"),
	PASSWORD_INVALID(1003, "Password must be at least 8 characters"),
	INVALID_KEY(1004, "Invalid message key"),
	USER_NOT_EXISTED(1005, "User not existed"),
	;

	ErrorCode(int code, String msg) {
		this.code = code;
		this.message = msg;
	}

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}