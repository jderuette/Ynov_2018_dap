package com.ynov.dap.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ynov.dap.google.GoogleAccountService;

/**
 * The Class AccountController.
 */
@Controller
public class AccountController {

    /** The google account service. */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Oauth callback.
     *
     * @param code the code
     * @param request the request
     * @param session the session
     * @return the string
     * @throws ServletException the servlet exception
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException {

        return googleAccountService.oAuthCallback(code, request, session);
    }

    /**
     * Adds the account.
     *
     * @param userId the user id
     * @param request the request
     * @param session the session
     * @return the string
     */
    @RequestMapping("/account/add/{userId}")
    public String addAccount(@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) {

        return googleAccountService.addAccount(userId, request, session);
    }

}
