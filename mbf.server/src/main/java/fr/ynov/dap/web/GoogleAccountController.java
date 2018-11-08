package fr.ynov.dap.web;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import fr.ynov.dap.services.google.GoogleService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The GoogleAccount class handles the users' authentication inside the app.
 */
@Controller
public class GoogleAccountController extends GoogleService {

    /**
     * The first five characters of the user data.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 5;
    /**
     * The last ten characters of the user data.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 10;
    /**
     * The endpoint to hit in order to redirect the user on the authentication page.
     */
    private static final String OAUTH_2_CALLBACK_URL = "/oAuth2Callback";

    /**
     * The logger of the GoogleAccount class.
     */
    private Logger logger = Logger.getLogger(GoogleAccountController.class.getName());

    /**
     * Handle the google response.
     * @param request The HTTP Request
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When google account could not be connected to DaP.
     */
    @RequestMapping("/oAuth2Callback")
    public final String oAuthCallback(final HttpServletRequest request,
                                final HttpSession session) throws ServletException {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, OAUTH_2_CALLBACK_URL);

        final String userId = getUserid(session);
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                logger.warning("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (logger.isLoggable(Level.ALL) && (null != credential && null != credential.getAccessToken())) {
                logger.fine("New user credential stored with userId : " + userId + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                            SENSIBLE_DATA_LAST_CHAR));
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            logger.severe("Failed to run the user account authentication with the following message: " + e.getMessage());
        }

        return "redirect:/";
    }

    /**
     * Add a google account (user will be prompt to connect and accept required
     * access).
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     */
    @RequestMapping("/account/add/{userId}")
    public final String addAccount(@PathVariable final String userId, final HttpServletRequest request,
                             final HttpSession session) {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(userId);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, OAUTH_2_CALLBACK_URL));
                // store userId in session for CallBack Access
                session.setAttribute("userId", userId);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            logger.severe("Error while loading credential (or google Flow) " + e.getMessage());
        }
        // only when error occurs, else redirected BEFORE
        return response;
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
            logger.severe("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add google acocunt : userId is NULL is User Session");
        }
        return userId;
    }

    /**
     * Extract OAuth2 google code (from URL) and decode it.
     * @param request the HTTP request to extract OAuth2 code
     * @return the decoded code
     * @throws ServletException if the code cannot be decoded
     */
    private String extracCode(final  HttpServletRequest request) throws MissingServletRequestParameterException {
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
            logger.severe("Error when trying to add google account : " + responseUrl.getError());
        }
        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     * @param req The current HTTP request to extract schema, host, port informations.
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    protected final String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

}
