package com.cgi.recipe.management.system.exception;

import org.springframework.http.HttpStatus;

public class CustomRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final String message;
	private final String detail;
	private final HttpStatus httpStatus;

	public String getMessage() {
		return message;
	}

	public String getDetail() {
		return detail;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public CustomRuntimeException(String message, String detail, HttpStatus httpStatus) {
		super(message);
		this.message = message;
		this.detail = detail;
		this.httpStatus = httpStatus;
	}
}