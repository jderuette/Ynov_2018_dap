package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.Config;
import fr.ynov.dap.Constants;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.microsoft.MicrosoftService;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * Controller to manage every call to Google Account API.
 * @author Robin DUDEK
 *
 */
@Controller
public class AccountController extends BaseController {

    @Override
    protected final String getClassName() {
        return AccountController.class.getName();
    }

    /**
     * Current configuration.
     */
    @Autowired
    private Config config;


    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Instance of GoogleAccount service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Instance of MicrosoftAccountService.
     * Auto resolved by Autowire.
     */
    /* @Autowired
    private MicrosoftAccountService msAccountService;*/

    /**
     * Add new user.
     * @param userKey user Key
     * @return Html page
     */
    @RequestMapping("/account/add/{userKey}")
    @ResponseBody
    public String addNewUser(@PathVariable final String userKey) {

        AppUser newUser = new AppUser();
        newUser.setUserKey(userKey);

        newUser = appUserRepository.save(newUser);

        return "User " + userKey + " added.";
    }

    /** Add the account.
    *
    * @param gAccountName the google account email
    * @param userKey the user id in the mySQL database
    * @param request the request
    * @param session the session
    * @return the string
    * @throws GeneralSecurityException the general security exception
    */
    @RequestMapping("/account/google/add/{gAccountName}")
    public String addAcount(@PathVariable final String gAccountName,
            @RequestParam(value = "userKey", required = true) final String userKey,
            final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException {

        session.setAttribute("userKey", userKey);
        session.setAttribute("accountName", gAccountName);

        final String redirectUri = buildRedirectUri(request, config.getOAuth2CallbackUrl());
        String response = googleAccountService.addAccount(gAccountName, userKey, redirectUri, session);

        return response;
    }

    /**
     * Link new Microsoft account to a user.
     * @param msAccountName Microsoft Account name
     * @param userKey the user id in the mySQL database
     * @param request Http request
     * @param session Http session
     * @param response Http response
     * @throws IOException Exception
     */
    @RequestMapping("/account/microsoft/add/{msAccountName}")
    public void addMicrosoftAccount(@PathVariable final String msAccountName,
            @RequestParam(value = "userKey", required = true) final String userKey,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws IOException {

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        session.setAttribute(Constants.SESSION_EXPECTED_STATE, state);
        session.setAttribute(Constants.SESSION_EXPECTED_NONCE, nonce);
        session.setAttribute("userKey", userKey);
        session.setAttribute("accountName", msAccountName);

        String redirectUrl = MicrosoftService.getLoginUrl(state, nonce);

        response.sendRedirect(redirectUrl);
    }

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
            final HttpSession session) throws ServletException, GeneralSecurityException {

        final String decodedCode = extractGoogleCode(request);
        final String redirectUri = buildRedirectUri(request, config.getOAuth2CallbackUrl());
        final String userId = session.getAttribute("userKey").toString();
        final String accountName = session.getAttribute("accountName").toString();

        String redirection = googleAccountService.oAuthCallback(code, userId, accountName, decodedCode, redirectUri);
        return redirection;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extractGoogleCode(final HttpServletRequest request) throws ServletException {

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

            getLogger().error("Error when trying to add Google account : " + responseUrl.getError());

            throw new ServletException("Error when trying to add Google account");

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
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }
}
