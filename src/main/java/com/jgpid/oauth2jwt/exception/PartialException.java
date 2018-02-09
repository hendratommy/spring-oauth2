package com.jgpid.oauth2jwt.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public abstract class PartialException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> responseProperties = new HashMap<String, Object>();

	public PartialException() {
		super();
	}
	public PartialException(String message) {
		super(message);
	}
	public PartialException(String message, Throwable cause) {
		super(message, cause);
	}
	public PartialException(Throwable cause) {
		super(cause);
	}

	public PartialException(Map<String, Object> responseProperties) {
		super();
		this.responseProperties = responseProperties;
	}
	public PartialException(Map<String, Object> responseProperties, Throwable cause) {
		super(cause);
		this.responseProperties = responseProperties;
	}

	public Map<String, Object> getResponseProperties() {
		return responseProperties;
	}
}
