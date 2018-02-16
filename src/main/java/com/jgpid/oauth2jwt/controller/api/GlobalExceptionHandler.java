package com.jgpid.oauth2jwt.controller.api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletResponse;

import com.jgpid.oauth2jwt.exception.ItemNotFoundException;
import com.jgpid.oauth2jwt.exception.PartialException;
import com.jgpid.oauth2jwt.model.form.RestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jgpid.oauth2jwt.exception.BadRequestException;
import com.jgpid.oauth2jwt.exception.PreConditionFailedException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestError> handleError(Exception e) {
		logger.error("GlobalExceptionHandler.handleError", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.INTERNAL_SERVER_ERROR, errors);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<RestError> databaseConflict(Exception e) {
		logger.error("GlobalExceptionHandler.databaseConflict", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", HttpStatus.CONFLICT.name());
		RestError err = new RestError(HttpStatus.CONFLICT, errors);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}

	@ExceptionHandler({ OptimisticLockException.class, PreConditionFailedException.class })
	public ResponseEntity<RestError> updateConflict(Exception e) {
		logger.error("GlobalExceptionHandler.updateConflict", e.getMessage(), e);
		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.PRECONDITION_FAILED, errors);
		if (e instanceof PreConditionFailedException)
			err.addErrors(((PreConditionFailedException) e).getErrors());
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(err);
	}

	// @ResponseStatus(code=HttpStatus.NOT_FOUND, reason="Data not found")
	@ExceptionHandler({ EmptyResultDataAccessException.class, ItemNotFoundException.class })
	public ResponseEntity<RestError> recordNotFound(Exception e, HttpServletResponse response) {
		logger.error("GlobalExceptionHandler.recordNotFound", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.NOT_FOUND, errors);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler({ SQLException.class, DataAccessException.class })
	public ResponseEntity<RestError> databaseErrors(Exception e) {
		logger.error("GlobalExceptionHandler.databaseErrors", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.INTERNAL_SERVER_ERROR, errors);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

	@ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class, AuthenticationException.class })
	public ResponseEntity<RestError> handleBadCredentialError(Exception e) {
		logger.error("GlobalExceptionHandler.handleBadCredentialError", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.NOT_FOUND, errors);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<RestError> handleAccessDeniedError(Exception e) {
		logger.error("GlobalExceptionHandler.handleAccessDeniedError", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.FORBIDDEN, errors);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(DisabledException.class)
	public ResponseEntity<RestError> handleDisabledError(Exception e) {
		logger.error("GlobalExceptionHandler.handleDisabledError", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.FORBIDDEN, errors);
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<RestError> handleBadRequestError(BadRequestException e) {
		logger.error("GlobalExceptionHandler.handleBadRequestError", e.getMessage(), e);

		Map<String, Object> errors = new HashMap<String, Object>();
		if (e.getMessage() != null && !e.getMessage().isEmpty())
			errors.put("message", e.getMessage());
		RestError err = new RestError(HttpStatus.BAD_REQUEST, errors);
		err.setBindingResult(e.getBindingResult());
		err.addErrors(e.getFieldErrors());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	// Return status OK with exceptions, meaning the request was partial success
	@ExceptionHandler(PartialException.class)
	public ResponseEntity<Object> handlePartialException(PartialException e) {
		logger.error("GlobalExceptionHandler.handlePartialException", e.getMessage(), e);

		return ResponseEntity.status(HttpStatus.OK).body(e.getResponseProperties());
	}
}
