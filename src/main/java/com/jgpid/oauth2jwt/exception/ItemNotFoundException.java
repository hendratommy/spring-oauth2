package com.jgpid.oauth2jwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ItemNotFoundException() {
		super();
	}
	public ItemNotFoundException(String message) {
		super(message);
	}
	public ItemNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	public ItemNotFoundException(Throwable cause) {
		super(cause);
	}
}
