package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccountData;
import fr.ynov.dap.data.GoogleRepository;
import fr.ynov.dap.service.GoogleService;

/**
 *
 * @author Dom
 *
 */
@Controller
public class GoogleAccountController extends GoogleService {
    /**
     * SENSIBLE_DATA_FIRST_CHAR.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 5;
    /**.
     * SENSIBLE_DATA_LAST_CHAR
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 10;
    /**
     * .
     */
    private Logger logger = Logger.getLogger("GoogleAccount");

    /**
     * .
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * .
     */
    @Autowired
    private GoogleRepository googleRepository;

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view display
     * @throws Exception .
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws Exception {
        final String decodedCode = extracCode(request);
        final String accountName = getUserAccount(session);
        final String userKey = getUserKey(session);
        final String redirectUri = buildRedirectUri(request, getConfig().getoAuth2CallbackUrl());
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);

            if (null == credential || null == credential.getAccessToken()) {
                logger.warning("Trying to store a NULL AccessToken for user : " + userKey);
            } else {
                if (logger.isLoggable(Level.ALL)) {
                    logger.severe("New user credential stored with userId : " + accountName + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
                }

                GoogleAccountData account = new GoogleAccountData();
                account.setAccountName(accountName);

                AppUser appuser = appUserRepository.findByName(userKey);
                appuser.getGoogleAccounts().stream().anyMatch(u -> u.getAccountName() == accountName);
                appuser.adGoogleAccount(account);
                appUserRepository.save(appuser);
                //googleRepository.save(account);

            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
            throw new ServletException("Erreur lors de la connection au compte Google, le compte est déjà liée.");
        }

        return "Vous êtes bien connecté(e). Le compte " + accountName + " est bien liée avec l'utilisateur : "
                + userKey;
    }

    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */

    private String getUserKey(final HttpSession session) throws ServletException {
        String userKey = null;
        if (null != session && null != session.getAttribute("userKey")) {
            userKey = (String) session.getAttribute("userKey");
        }

        if (null == userKey) {
            logger.severe("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
        }
        return userKey;
    }

    /**
     * @param session .
     * @return .
     * @throws ServletException .
     */
    private String getUserAccount(final HttpSession session) throws ServletException {
        String accountName = null;
        if (null != session && null != session.getAttribute("accountName")) {
            accountName = (String) session.getAttribute("accountName");
        }

        if (null == accountName) {
            logger.severe("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
        }
        return accountName;
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
            logger.severe("Error when trying to add Google acocunt : " + responseUrl.getError());
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
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userKey  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @param accountName .
     * @throws GeneralSecurityException .
     */
    @RequestMapping("/account/add/{accountName}")
    public String addAccount(@PathVariable final String accountName, @RequestParam("userKey") final String userKey,
            final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {

            if (appUserRepository.findByName(userKey) == null) {
                return "The account " + userKey + " doesn't exist";
            }

            if (googleRepository.findByAccountName(accountName) != null) {
                return "The account " + accountName + " doesn't exist";
            } else {
                flow = super.getFlow();
                credential = flow.loadCredential(accountName);

                if (credential != null && credential.getAccessToken() != null) {
                    response = "AccountAlreadyAdded";

                } else {
                    // redirect to the authorization flow
                    final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                    authorizationUrl.setRedirectUri(buildRedirectUri(request, "/oAuth2Callback"));
                    // store userId in session for CallBack Access
                    session.setAttribute("accountName", accountName);
                    session.setAttribute("userKey", userKey);
                    response = "redirect:" + authorizationUrl.build();
                }
            }
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
        // only when error occurs, else redirected BEFORE
        return response;
    }
}
