package com.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

/**
 * TODO bap by Djer Ne crie pas : Je ne suis pas aveugle ! 
 * SERVICE FOR ACCOUNT.
 * @author POL
 */
@Service
public class GoogleAccountService extends GoogleService {

    /**
     * FIRST CHAR.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 0;

    /**
     * LAST CHAR.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 1;

    /**
     * LOGGER SLF4J.
     */
    //TODO bap by Djer static final ?
    private Logger log = LoggerFactory.getLogger(GoogleAccountService.class);

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     */
    //TODO bap by Djer bien vue de l'extraire en service/Controller. Mais un Service qui dépend de "request" ce n'estp as top.
    //Essaye de faire le travail de traitement de la requete (et session) dans le controller.
     public String oAuthCallback(final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException {
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, "/oAuth2Callback");

        final String userId = getUserid(session);
        try {
            GoogleAuthorizationCodeFlow flow = null;
            try {
                flow = super.getFlow();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                log.error("Error while loading credential (or Google Flow)", e);
            }
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                log.warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (log.isDebugEnabled() && null != credential && null != credential.getAccessToken()) {
                log.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                                    SENSIBLE_DATA_LAST_CHAR));
            }
        } catch (IOException e) {
            log.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        //TODO bap by Djer es-tu sure d'avoir un mapping sur / ?
        return "redirect:/";
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     */
     //TODO bap by Djer essaye de ne plus dépendre de Requets et Session dans ce code "metier"
     public String addAccount(@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow = null;
        Credential credential = null;
        try {
            try {
                flow = super.getFlow();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                log.error("Error while loading credential (or Google Flow)", e);
            }
            credential = flow.loadCredential(userId);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, "/oAuth2Callback"));
                session.setAttribute("userId", userId);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            log.error("Error while loading credential (or Google Flow)", e);
        }
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
            log.error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
        }
        return userId;
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
