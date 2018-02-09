package com.jgpid.oauth2jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Map;

/* TODO: Encode and Decode token with additional "user changes" key, so when the changes occurs the token will be invalid (force user logout)
 */
public class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {
    private static final Logger logger = LoggerFactory.getLogger(CustomJwtAccessTokenConverter.class);

    /*
    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        logger.debug("Extract Authentication");
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }
    */

    @Override
    public Map<String, ?> convertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        logger.debug("Convert access token: " + token.getValue());
        return super.convertAccessToken(token, authentication);
    }

}
