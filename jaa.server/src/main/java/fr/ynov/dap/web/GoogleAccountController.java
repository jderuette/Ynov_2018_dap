package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.services.google.GoogleService;

/**
 * Google Account controller used to create a new Google Account.
 *
 */
@Controller
public class GoogleAccountController extends GoogleService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();
    /**
     * Index of first char for sensible data.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 1;
    /**
     * Index of last char for sensible data.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 10;
    /**
     * UserKey parameter constant.
     */
    private static final String USER_KEY_PARAM_NAME = "userKey";
    /**
     * Account name parameter constant.
     */
    private static final String ACCOUNT_NAME_PARAM_NAME = "accountName";

    /**
     * AppUser repository instantiate thanks to the injection of dependency.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * Handle the user Google Account response for authentication.
     * @param request The HTTP Request
     * @param code The (encoded) code used by Google (token, expirationDate)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException, GeneralSecurityException {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, getConfig().getoAuth2CallbackUrl());

        final String accountName = getSessionParam(session, ACCOUNT_NAME_PARAM_NAME);
        final String userKey = getSessionParam(session, USER_KEY_PARAM_NAME);
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);
            if (null == credential || null == credential.getAccessToken()) {
                log.warn("Trying to store a NULL AccessToken for user : " + accountName);
            }

            if (log.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    log.debug("New user credential stored with accountName : " + accountName + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                                    SENSIBLE_DATA_LAST_CHAR));
                }
            }
            // onSuccess(request, resp, credential);
            GoogleAccount googleAccount = new GoogleAccount();
            googleAccount.setAccountName(accountName);

            AppUser appUser = repository.findByUserKey(userKey);
            appUser.addGoogleAccount(googleAccount);
            repository.save(appUser);
        } catch (IOException e) {
            log.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "redirect:/";
    }

    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @param paramName name of the parameter to get
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getSessionParam(final HttpSession session, final String paramName) throws ServletException {
        String param = null;
        if (null != session && null != session.getAttribute(paramName)) {
            param = (String) session.getAttribute(paramName);
        }

        if (null == param) {
            log.error(paramName + " in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google account : " + paramName
                    + " is NULL is User Session");
        }
        return param;
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
            log.error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
            // onError(request, resp, responseUrl);
        }

        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     * @param req The current HTTP request to extract schema, host, port informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

    /**
     * Add a Google account (user will be prompt to connect in the Web Browser and accept required
     * access).
     * @param accountName  the user to store Data
     * @param userKey the userKey of the user
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/add/account/{accountName}")
    public String addAccount(@PathVariable final String accountName,
            @RequestParam(USER_KEY_PARAM_NAME) final String userKey,
            final HttpServletRequest request,
            final HttpSession session) throws GeneralSecurityException {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(accountName);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded"; //TODO throw exception
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getoAuth2CallbackUrl()));
                // store accountName in session for CallBack Access
                session.setAttribute(ACCOUNT_NAME_PARAM_NAME, accountName);
                session.setAttribute(USER_KEY_PARAM_NAME, userKey);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            log.error("Error while loading credential (or Google Flow)", e);
        }
        // only when error occurs, else redirected BEFORE
        return response;
    }

}
