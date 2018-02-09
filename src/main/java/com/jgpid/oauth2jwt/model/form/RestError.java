package com.jgpid.oauth2jwt.model.form;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jgpid.oauth2jwt.model.serializer.RestErrorSerializer;

@JsonSerialize(using=RestErrorSerializer.class, as=String.class)
public class RestError {
	private HttpStatus status;
	private Map<String, Object> errors;
	@JsonIgnore
	private BindingResult bindingResult;

	public RestError() {
		this.errors = new HashMap<String, Object>();
	}
	public RestError(HttpStatus status, Map<String, Object> errors) {
		this.status = status;
		this.errors = errors;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public Map<String, Object> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, Object> errors) {
		this.errors = errors;
	}

	public void addError(String fieldName, Object error) {
		errors.put(fieldName, error);
	}

	public void addErrors(Map<String, Object> errors) {
		this.errors.putAll(errors);
	}

	public Object getError(String key) {
		return this.errors.get(key);
	}

	public BindingResult getBindingResult() {
		return bindingResult;
	}
	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

}
