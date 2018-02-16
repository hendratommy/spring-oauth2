package com.jgpid.oauth2jwt.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jgpid.oauth2jwt.model.domain.User;
import com.jgpid.oauth2jwt.service.UserService;

/**
 * Created by hendr on 02/07/2017.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private static int sleepTimeout = 1000;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('READ_USERS') and #oauth2.hasScope('MANAGE_USERS')")
    public Page<User> getUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                               @RequestParam(name = "size", defaultValue = "10") int size,
                               @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                               @RequestParam(name = "direction", defaultValue = "asc") String direction, HttpServletRequest request) {
        logger.info(request.getMethod() + " " + request.getRequestURI()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));
        // mock busy server
        try {
            Thread.sleep(sleepTimeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return userService.getUsers(page, size, sortBy != null ? sortBy.trim() : sortBy,
                direction != null ? direction.trim() : direction);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public User getUser(@PathVariable Long id, HttpServletRequest request) {
        logger.info(request.getMethod() + " " + request.getRequestURI()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        return userService.getUser(id);
    }

    @RequestMapping(path = "/search", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('READ_USERS')")
    public Page<User> searchUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
                                  @RequestParam(name = "direction", defaultValue = "asc") String direction,
                                  @RequestParam(name = "keyword", required = true) String keyword, HttpServletRequest request) {
        logger.info(request.getMethod() + " " + request.getRequestURI()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        if (keyword != null && !keyword.isEmpty())
            return userService.searchUsers(page, size, sortBy != null ? sortBy.trim() : sortBy,
                    direction != null ? direction.trim() : direction, keyword.trim());
        return userService.getUsers(page, size, sortBy != null ? sortBy.trim() : sortBy,
                direction != null ? direction.trim() : direction);
    }
}
