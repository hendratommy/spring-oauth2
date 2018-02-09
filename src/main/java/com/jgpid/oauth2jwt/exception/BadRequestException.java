package com.jgpid.oauth2jwt.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private BindingResult bindingResult;
	private Map<String, Object> fieldErrors = new HashMap<>();

	public BadRequestException() {
		super();
	}
	public BadRequestException(String message) {
		super(message);
	}
	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}
	public BadRequestException(Throwable cause) {
		super(cause);
	}

	public BadRequestException(BindingResult bindingResult) {
		super();
		this.bindingResult = bindingResult;
	}
	public BadRequestException(String message, BindingResult bindingResult) {
		super(message);
		this.bindingResult = bindingResult;
	}
	public BadRequestException(String message, Throwable cause, BindingResult bindingResult) {
		super(message, cause);
		this.bindingResult = bindingResult;
	}
	public BadRequestException(Throwable cause, BindingResult bindingResult) {
		super(cause);
		this.bindingResult = bindingResult;
	}
	public BindingResult getBindingResult() {
		return bindingResult;
	}
	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}
	public Map<String, Object> getFieldErrors() {
		return fieldErrors;
	}
	public void setFieldErrors(Map<String, Object> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}

}
