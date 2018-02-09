package com.jgpid.oauth2jwt.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;

public class CustomClientDetailsUserDetailsService extends ClientDetailsUserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomClientDetailsUserDetailsService.class);

    public CustomClientDetailsUserDetailsService(ClientDetailsService clientDetailsService) {
        super(clientDetailsService);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("CustomClientDetailsUserDetailsService.loadUserByUsername: " + username);
        return super.loadUserByUsername(username);
    }
}
