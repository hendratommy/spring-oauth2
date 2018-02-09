package com.jgpid.oauth2jwt.security;

import com.jgpid.oauth2jwt.model.form.RestError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jgpid.oauth2jwt.util.JacksonObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Component("authenticationEntryPoint")
public class RequestAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(RequestAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
			throws IOException, ServletException {
		Jackson2JsonObjectMapper jsonMapper = JacksonObjectMapper.getJacksonJsonObjectMapper();
		Map<String, Object> errors = new HashMap<>();
		errors.put("message", authenticationException.getMessage());
		RestError restError = new RestError(HttpStatus.UNAUTHORIZED, errors);
		try {
			String errorJson = jsonMapper.toJson(restError);

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			response.getWriter().write(errorJson);
		} catch (Exception e) {
			logger.error("Cannot convert to JSON String", e);
			throw new IOException(e);
		}
	}

}
