package com.jgpid.oauth2jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgpid.oauth2jwt.model.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);

    private UserDetailsService userDetailsService;

    public CustomUsernamePasswordAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        logger.info("Attempt Authentication");
        try {
            LoginForm loginForm = new ObjectMapper().readValue(request.getInputStream(), LoginForm.class);
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginForm.getUsername());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword(), userDetails.getAuthorities());
            setDetails(request, token);

            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            logger.error("Error while parsing content to LoginForm.\nAuthentication Content-Type: "
                    + request.getHeader("Content-Type"), e);
            throw new InternalAuthenticationServiceException("Failed to parse authentication request body");
        }
    }

    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public AuthenticationManager getAuthenticationManager() {
        return super.getAuthenticationManager();
    }

    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
