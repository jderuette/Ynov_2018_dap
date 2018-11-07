package fr.ynov.dap.controllers;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.services.GoogleAccountService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * GoogleAccountController
 */
@Controller
public class GoogleAccountController extends GoogleHelper {

    @Autowired
    private GoogleAccountService googleAccountService;

    private static final Logger  LOG                      = LogManager.getLogger(GoogleAccountController.class);
    private static final Integer SENSIBLE_DATA_FIRST_CHAR = 3;
    private static final Integer SENSIBLE_DATA_LAST_CHAR  = 8;
    private static final String  OAUTH_CALLBACK_URL       = "/oAuth2Callback";

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     *
     * @param userKey the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     */
    @RequestMapping("/user/add/{userKey}")
    public final String addAccount(@PathVariable final String userKey, final HttpServletRequest request,
                                   final HttpSession session) {
        String                      response   = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential                  credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(userKey);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(googleAccountService.buildRedirectUri(request, OAUTH_CALLBACK_URL));
                // store userKey in session for CallBack Access
                session.setAttribute("userKey", userKey);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            LOG.error("Error while loading credential (or Google Flow)", e);
        }
        // only when error occurs, else redirected BEFORE
        return response;
    }

    /**
     * Handle the Google response.
     *
     * @param request The HTTP Request
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     */
    @RequestMapping(OAUTH_CALLBACK_URL)
    public final String oAuthCallback(final HttpServletRequest request,
                                      final HttpSession session) throws ServletException {
        final String decodedCode = googleAccountService.extractCode(request);

        final String redirectUri = googleAccountService.buildRedirectUri(request, OAUTH_CALLBACK_URL);

        final String userId = googleAccountService.getUserKey(session);
        try {
            final GoogleAuthorizationCodeFlow flow     = super.getFlow();
            final TokenResponse               response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (LOG.isDebugEnabled() && (null != credential && null != credential.getAccessToken())) {
                LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                        + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                        SENSIBLE_DATA_LAST_CHAR));
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            LOG.error("Exception while trying to store user Credential", e);
        }

        return "redirect:/user-success";
    }
}
