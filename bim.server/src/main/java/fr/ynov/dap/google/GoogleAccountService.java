package fr.ynov.dap.google;

import java.io.IOException;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;

/**
 * Manage google accounts.
 * @author MBILLEMAZ
 *
 */
@Controller
public class GoogleAccountService extends CommonGoogleService {
    /**
    * Start of substring for token.
    */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 5;
    /**
     * End of substring for token.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 10;

    /**
     * Duration before user expiration.
     */
    private static final Long EXPIRATION_TIME = (long) (3600 * 4);
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * User repository.
     */
    @Autowired
    private AppUserRepository userRepository;

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     */
    @RequestMapping("/oAuth2Callback")
    public final String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException {
        final String decodedCode = extraCode(request);

        final String redirectUri = buildRedirectUri(request, "/oAuth2Callback");

        final String accountName = getAccountName(session);
        final String userKey = getUserKey(session);
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName)
                    .setExpiresInSeconds(EXPIRATION_TIME);
            if (null == credential || null == credential.getAccessToken()) {
                LOGGER.warn("Trying to store a NULL AccessToken for user : " + accountName);
            } else {
                AppUser user = userRepository.findByName(userKey);
                user.addGoogleAccount(new GoogleAccount(accountName));
                userRepository.save(user);
                LOGGER.debug("Utilisateur crée en base");
            }

            if (LOGGER.isDebugEnabled() && null != credential && null != credential.getAccessToken()) {

                LOGGER.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
                        + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));

            }

            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            LOGGER.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "redirect:/userCreated";
    }

    /**
     * retrieve the account name in Session.
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
            LOGGER.error("accountName in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : accountName is NULL is User Session");
        }
        return accountName;
    }

    /**
     * retrieve the userKey in Session.
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
            LOGGER.error("accountName in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userKey is NULL is User Session");
        }
        return userKey;
    }

    /**
     * Extract OAuth2 Google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extraCode(final HttpServletRequest request) throws ServletException {
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
            LOGGER.error("Error when trying to add Google acocunt : " + responseUrl.getError());
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
    protected final String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param accountName name of the account to add
     * @param userKey userKey to store
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     */
    @RequestMapping("/account/add/{accountName}")
    public final String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
            final HttpServletRequest request, final HttpSession session) {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
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
        } catch (IOException e) {
            LOGGER.error("Error while loading credential (or Google Flow)", e);
        }
        // only when error occurs, else redirected BEFORE
        return response;
    }

    /**
     * Print message for added user.
     * @return message
     */
    @RequestMapping("/userCreated")
    @ResponseBody
    public final String userCreated() {
        return "Utilisateur ajouté";
    }
}
