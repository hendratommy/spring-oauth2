package com.jgpid.oauth2jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class CustomTokenServices extends DefaultTokenServices {
    private static final Logger logger = LoggerFactory.getLogger(CustomTokenServices.class);

    public OAuth2Authentication loadAuthentication(String accessTokenValue) throws AuthenticationException,
            InvalidTokenException {
        logger.debug("Authenticating accessToken: " + accessTokenValue);
        try {
            return super.loadAuthentication(accessTokenValue);
        } catch (AuthenticationException e) {
            logger.warn("Authentication failed at loadAuthentication for accessToken: " + accessTokenValue, e);
            throw e;
        } catch (InvalidTokenException e) {
            logger.warn("Invalid token at loadAuthentication for accessToken: " + accessTokenValue, e);
            throw e;
        }
    }

    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        logger.debug("Create accessToken for authentication: " + (authentication.getPrincipal() instanceof User
                ? ((User) authentication.getPrincipal()).getUsername() : authentication.getPrincipal()));
        try {
            OAuth2AccessToken token = super.createAccessToken(authentication);
            logger.debug("Token: " + token.getValue());
            return token;
        } catch (AuthenticationException e) {
            logger.warn("Authentication failed at createAccessToken for authentication: " + ((User) authentication.getPrincipal()).getUsername());
            throw e;
        }
    }

    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
        logger.debug("Get access token for " + ((User) authentication.getPrincipal()).getUsername());
        OAuth2AccessToken token = super.getAccessToken(authentication);
        logger.debug("Token: " + token.getTokenType() + " : " + token.getValue());
        return token;
    }

    /*
    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        logger.debug("Creating accessToken for authentication: " + ((User)authentication.getPrincipal()).getUsername()
                + ", refreshToken: " + refreshToken.getValue());
        try {
            return super.createAccessToken(authentication, refreshToken);
        } catch (AuthenticationException e) {
            logger.warn("Authentication failed at createAccessToken for authentication: " + ((User)authentication.getPrincipal()).getUsername()
                    + ", refreshToken: " + refreshToken.getValue());
            throw e;
        }
    }
    */
}
