package com.ynov.dap.controller.google;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.http.GenericUrl;
import com.ynov.dap.controller.BaseController;
import com.ynov.dap.service.google.GoogleAccountService;

@Controller
public class GoogleAccountController extends BaseController {

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

        final String decodedCode = extracCode(request);
        final String redirectUri = buildRedirectUri(request, config.getoAuth2CallbackUrl());
        final String userId = getUserid(session);

        return googleAccountService.oAuthCallback(code, decodedCode, redirectUri, userId);
    }

    @GetMapping("/account/add/google/{accountName}")
    public String addAccount(@PathVariable final String accountName, @RequestParam(value = "userKey", required = true) final String userKey, final HttpServletRequest request,
            final HttpSession session) {
    	final String redirectUri = buildRedirectUri(request, config.getoAuth2CallbackUrl());

        return googleAccountService.addAccount(accountName, userKey, redirectUri, session);
    }

    @Override
    public String getClassName() {
        return GoogleAccountController.class.getName();
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extracCode(final HttpServletRequest request) throws ServletException {
        final StringBuffer buf = request.getRequestURL();
        if (null != request.getQueryString()) {
            buf.append('?').append(request.getQueryString());
        }
        final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
        final String decodeCode = responseUrl.getCode();

        if (decodeCode == null) {
            throw new MissingServletRequestParameterException("code", "String");
        }

        if (null != responseUrl.getError()) {
        	getLogger().error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
        }

        return decodeCode;
    }
    
    /**
     * Build a current host (and port) absolute URL.
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    private String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }
    
    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserid(final HttpSession session) throws ServletException {
        String userId = null;
        if (null != session && null != session.getAttribute("userId")) {
            userId = (String) session.getAttribute("userId");
        }

        if (null == userId) {
        	getLogger().error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
        }
        return userId;
    }

}
