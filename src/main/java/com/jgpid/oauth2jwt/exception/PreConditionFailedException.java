package com.jgpid.oauth2jwt.exception;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class PreConditionFailedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> errors = new HashMap<>();

	public PreConditionFailedException() {
		super();
	}
	public PreConditionFailedException(String message) {
		super(message);
	}
	public PreConditionFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	public PreConditionFailedException(Throwable cause) {
		super(cause);
	}
	public Map<String, Object> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, Object> errors) {
		this.errors = errors;
	}
	public void addError(String errorName, Object errorValue) {
		errors.put(errorName, errorValue);
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.errors.put("lastModifiedBy", lastModifiedBy);
	}
	public void setUpdatedDate(Calendar updatedDate) {
		this.errors.put("updatedDate", updatedDate);
	}
}
