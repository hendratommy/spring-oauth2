package com.jgpid.oauth2jwt.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundStatusOKException extends PartialException {
	private static final long serialVersionUID = 1L;

	public ItemNotFoundStatusOKException() {
		super();
	}
	public ItemNotFoundStatusOKException(String message) {
		super(message);
	}
	public ItemNotFoundStatusOKException(String message, Throwable cause) {
		super(message, cause);
	}
	public ItemNotFoundStatusOKException(Throwable cause) {
		super(cause);
	}

	public ItemNotFoundStatusOKException(Map<String, Object> responseProperties) {
		super(responseProperties);
	}
	public ItemNotFoundStatusOKException(Map<String, Object> responseProperties, Throwable cause) {
		super(responseProperties, cause);
	}
}
