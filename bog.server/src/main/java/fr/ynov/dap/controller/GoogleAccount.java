package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.security.auth.callback.Callback;
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
import fr.ynov.dap.data.GoogleAccountData;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.GoogleAccountRepository;
import fr.ynov.dap.service.GoogleService;

/**
 * @author Gaël BOSSER
 * Class GoogleAccount useful to connect to our Google account
 */
@Controller
public class GoogleAccount extends GoogleService implements Callback {

    /**.
     * SENSIBLE_DATA_FIRST_CHAR constante
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 5;
    /**.
     * SENSIBLE_DATA_LAST_CHAR constante
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 10;

    /**.
     * Constructor GoogleAccount
     * @throws Exception si un problème est survenu lors de la création de l'instance GoogleAccount
     * @throws IOException si un problème est survenu lors de la création de l'instance GoogleAccount
     */
    public GoogleAccount() throws Exception, IOException {
        super();

    }

    /**.
     * repositoryUser récupère l'instance de AppUserRepository en cours
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**.
     * repositoryGoogleAccount récupére l'instance de GoogleAccountRepository en cours
     */
    @Autowired
    private GoogleAccountRepository repositoryGoogle;

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws Exception si un problème est survenu lors de l'appel à cette fonction
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws Exception {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, getConfig().getoAuth2CallbackUrl());
        final String accountName = getUserAccountName(session);
        final String userKey = getUserKey(session);
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);

            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + accountName);
            } else {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
                }
                GoogleAccountData account = new GoogleAccountData();
                account.setAccountName(accountName);
                AppUser userExisting = repositoryUser.findByName(userKey);
                userExisting.addGoogleAccount(account);
                repositoryUser.save(userExisting);
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            LOG.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "redirect:/user/success";
    }

    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User userKey in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserKey(final HttpSession session) throws ServletException {
        String userKey = null;
        if (null != session && null != session.getAttribute("userKey")) {
            userKey = (String) session.getAttribute("userKey");
        }

        if (null == userKey) {
            LOG.error("userKey in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userKey is NULL is User Session");
        }
        return userKey;
    }

    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User accountName in Session
     * @throws ServletException if no User Id in session
     */
    private String getUserAccountName(final HttpSession session) throws ServletException {
        String accountName = null;
        if (null != session && null != session.getAttribute("accountName")) {
            accountName = (String) session.getAttribute("accountName");
        }

        if (null == accountName) {
            LOG.error("accountName in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : accountName is NULL is User Session");
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
            LOG.error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
            // onError(request, resp, responseUrl);
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
     * @param accountName  the user to store Data
     * @param userKey  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException addAccount fonction
     */
    @RequestMapping("/account/add/{accountName}")
    public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
            final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException {

        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;

        try {
            if (null != repositoryGoogle.findByAccountName(accountName)) {
                response = "L'accountname : " + accountName + " existe déjà.";
                return response;
            }
            if (null == repositoryUser.findByName(userKey)) {
                response = "L'utilisateur " + userKey + " n'existe pas.";
                return response;
            }
            flow = super.getFlow();
            credential = flow.loadCredential(userKey);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getoAuth2CallbackUrl()));
                session.setAttribute("accountName", accountName);
                session.setAttribute("userKey", userKey);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            LOG.error("Error while loading credential (or Google Flow)", e);
        }
        return response;
    }
}
