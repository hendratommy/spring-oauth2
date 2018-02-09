package com.jgpid.oauth2jwt.security;

import com.jgpid.oauth2jwt.model.domain.Privilege;
import com.jgpid.oauth2jwt.model.domain.Role;
import com.jgpid.oauth2jwt.model.domain.User;
import com.jgpid.oauth2jwt.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service("customUserDetailService")
@Transactional(readOnly = true)
public class CustomUserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("User " + username + " logged in");
        logger.info("Loading user info for " + username);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            // logger.warn("Username " + username + " not found, assigning Role as Guest");
            // return new org.springframework.security.core.userdetails.User("", "", true, true, true, true, getAuthorities(Arrays.asList(roleRepository.findByName("Guest"))));
            throw new BadCredentialsException("Bad credentials");
        }
        logger.info("Found username: " + username + " with roles " + Arrays.toString(user.getRolesValue().toArray(new String[0])));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(),
                true, true, !user.isLocked(), getAuthorities(user.getRoles()));
    }

    public List<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        if (roles != null && !roles.isEmpty()) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (Role role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
                if (role.getPrivileges() != null && !role.getPrivileges().isEmpty()) {
                    for (Privilege privilege : role.getPrivileges()) {
                        authorities.add(new SimpleGrantedAuthority(privilege.getName()));
                    }
                }
            }
            return authorities;
        }
        logger.warn("Role is empty!");
        return null;
    }
}
