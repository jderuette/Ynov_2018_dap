package fr.ynov.dap.dap;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import fr.ynov.dap.dap.repositories.GoogleAccountRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Google account.
 */
@Service
public class GoogleAccountService extends GoogleService {
    /**
     * sensible data.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 5;
    /**
     * sensible data.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 15;
    
  //TODO plp by Djer |POO| Devrait être écris en MAJUSCULE (Checkstyle/PMD te signale ce genre d'oublie)
    /**
     * instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(GoogleAccountService.class);
    
  //TODO plp by Djer |POO| Attention si tu ne précise pas, par défaut cet attribut est public (comme la classe) !
    /**
     * instantiate appUserRepository
     */
    @Autowired
    AppUserRepository userRepository;
    
  //TODO plp by Djer |POO| Attention si tu ne précise pas, par défaut cet attribut est public (comme la classe) !
    /**
     * instantiate googleAccountRepository
     */
    @Autowired
    GoogleAccountRepository googleRepository;
    /**
     * Handle the Google response.
     *
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException         When Google account could not be connected to DaP.
     * @throws GeneralSecurityException throw exception.
     */
    @RequestMapping("/oAuth2Callback")
    public final String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
                                final HttpSession session) throws ServletException, GeneralSecurityException {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, getConfig().getoAuth2CallbackUrl());

        final String userKey = getUserKey(session);
        final String accountName = getAccountName(session);

        AppUser user = userRepository.findByName(userKey);

        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);
            if (null == credential || null == credential.getAccessToken()) {
                log.warn("Trying to store a NULL AccessToken for user : " + accountName);
            }

            if (log.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    log.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                            SENSIBLE_DATA_LAST_CHAR));
                }
            }

            GoogleAccount googleAccount = new GoogleAccount();
//            googleAccount.setOwner(user);
            googleAccount.setName(accountName);
            user.addGoogleAccount(googleAccount);
            //TODO plp by Djer |JPA| Sauvegarde simple le "AppUSer", avec le Cascade.ALL dans ton entité, JPA va s'occuper de mettre à jour/créer les "Google acocunts"
            googleRepository.save(googleAccount);
            userRepository.save(user);
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            //TODO plp by Djer |Log4J| Contextualise tes messages (" for userkey : " + userKey + " and accountName : " + accountName)
            log.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to connect Google Account");
        }

        return "redirect:/";
    }

    /**
     * retrieve the User ID in Session.
     *
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
            log.error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google account : userKey is NULL is User Session");
        }
        return userKey;
    }

    /**
     * retrieve the account name in Session.
     *
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getAccountName(final HttpSession session) throws ServletException {
        String accountName = null;
        if (null != session && null != session.getAttribute("accountName")) {
            accountName = (String) session.getAttribute("accountName");
        }

        if (null == accountName) {
            log.error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : accountName is NULL is User Session");
        }
        return accountName;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     *
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
            log.error("Error when trying to add Google account : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google account");
        }

        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     *
     * @param req         The current HTTP request to extract schema, host, port
     *                    informations
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    protected final String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     *
     * @param accountName  the user to store Data
     * @param userKey  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException throw exception.
     */
    public final String addAccount(@PathVariable final String accountName, @PathVariable final String userKey,
                                   final HttpServletRequest request, final HttpSession session)
            throws GeneralSecurityException {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(accountName);

            if (credential != null && credential.getAccessToken() != null) {
//                response = "AccountAlreadyAdded";
                response = "welcome";
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getoAuth2CallbackUrl()));
                // store userId in session for CallBack Access
                session.setAttribute("userKey", userKey);
                session.setAttribute("accountName", accountName);

                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            //TODO plp by Djer |Log4J| Contextualise tes messages (" for userKey : " + userKey + " and acocuntName : " + accountName)
            log.error("Error while loading credential (or Google Flow)", e);
        }
        return response;
    }
}
