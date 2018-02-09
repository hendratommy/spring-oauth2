package com.jgpid.oauth2jwt.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
	@Override
	public void onLogoutSuccess(
		      HttpServletRequest request,
		      HttpServletResponse response,
		      Authentication authentication)
		      throws IOException, ServletException {
		PrintWriter printer = response.getWriter();

		try {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
			printer.write("{ \"status\": \"OK\" }");
			printer.flush();
		} finally {
			if (printer != null) printer.close();
		}
	}
}
