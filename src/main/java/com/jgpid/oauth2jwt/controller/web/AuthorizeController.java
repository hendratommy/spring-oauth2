package com.jgpid.oauth2jwt.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgpid.oauth2jwt.model.form.LoginForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class AuthorizeController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeController.class);

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage(HttpServletRequest request, Model uiModel) {
        logger.debug(request.getMethod() + " " + request.getRequestURI()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        uiModel.addAttribute("loginForm", new LoginForm());
        return "/login";
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public String getPrincipal(HttpServletRequest request, Model uiModel, Principal principal) {
        logger.debug(request.getMethod() + " " + request.getRequestURI()
                + (request.getQueryString() != null ? "?" + request.getQueryString() : ""));

        uiModel.addAttribute("principal", principal);
        return "/user";
    }
}
