package com.jgpid.oauth2jwt.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jgpid.oauth2jwt.model.form.RestError;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import com.jgpid.oauth2jwt.util.JacksonObjectMapper;

public class RequestAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		logger.error("RequestAuthenticationFailureHandler.onAuthenticationFailure", exception);

		Map<String, Object> errors = new HashMap<String, Object>();
		RestError err = new RestError();

		logger.debug(exception.getClass().getName());

		if (exception.getClass().getName().equals(DisabledException.class.getName())) {
			errors.put("message", exception.getMessage());

			err.setStatus(HttpStatus.FORBIDDEN);
			err.setErrors(errors);

			sendResponse(response, HttpServletResponse.SC_FORBIDDEN, err);
		}
		else {
			errors.put("message", "Requested User not found");

			err.setStatus(HttpStatus.NOT_FOUND);
			err.setErrors(errors);

			sendResponse(response, HttpServletResponse.SC_NOT_FOUND, err);
		}

	}

	public void sendResponse(HttpServletResponse response, int httpStatusCode, RestError err) throws IOException {
		Jackson2JsonObjectMapper jsonMapper = JacksonObjectMapper.getJacksonJsonObjectMapper();
		PrintWriter printer = response.getWriter();

		try {
			String errorJson = jsonMapper.toJson(err);
			response.setStatus(httpStatusCode);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			printer.write(errorJson);
			printer.flush();
		} catch (Exception ex) {
			logger.error("Error when sending recordNotFound response at GlobalException.recordNotFound", ex);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setContentLength(0);
		} finally {
			if (printer != null) printer.close();
		}
	}
}
