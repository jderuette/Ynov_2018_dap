package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
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
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;

/**
 * Controller pour la gestion du compte google.
 * @author alexa
 */
@Controller
//TODO roa by Djer idealement devrait être un Service, ou alors devrait être présent dans le package "web"
// (le plus simple est de le mettre dans Web)
public class GoogleAccount extends ConnexionGoogle {
    //TODO roa by Djer tu fait de l'injection pour ConnexionGoogle dans les autres Service, pourquoi pas ici ?
    /**
     * Début chaine de carractère visible du token.
     */
    private static final Integer SENSIBLE_DATA_FIRST_CHAR = 5;
    /**
     * Fin chaine de carractère visible du token.
     */
    //TODO roa by Djer il serait quand meme utile d'afficher quelques caractère (au moin 5-6, donc de 5 à 10)
    private static final Integer SENSIBLE_DATA_LAST_CHAR = 5;

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws IOException problème lié à la sauvegarde des credentials.
     * @throws GeneralSecurityException problème de connection au compte google
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException, GeneralSecurityException, IOException {
        final String decodedCode = extracCode(request);
        final String redirectUri = buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl());
        final String userId = getUserid(session);
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow(httpTransport);
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                //TODO roa by Djer "getLogger()" est une opération couteuse,
                // c'est pourquoi on crée génralement une constante "LOG" dans la classe
                LogManager.getLogger().warn("Trying to store a NULL AccessToken for user : " + userId);
            }
            if (LogManager.getLogger().isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    LogManager.getLogger().debug("New user credential stored with userId : " + userId
                            + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
                }
            }
        } catch (IOException e) {
            LogManager.getLogger().error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }
        //TODO roa by Djer Es-tu sure d'avoir un mapping sur / ??
        return "redirect:/";
    }

    /**
     * Retrieve the User ID in Session.
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
            LogManager.getLogger().error("userId in Session is NULL in Callback");
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
            LogManager.getLogger().error("Error when trying to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying to add Google acocunt");
        }
        return decodeCode;
    }

    /**
     * Build a current host (and port) absolute URL.
     * @param req         The current HTTP request to extract schema, host, port informations
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
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws IOException problème de chargement des credentials
     * @throws GeneralSecurityException problème de connection au compte google
     */
    @RequestMapping("/account/add/{userId}")
    public String addAccount(@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) throws GeneralSecurityException, IOException {
        String response = "errorOccurs";
        LogManager.getLogger().info("fonction addAccount");
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        try {
            flow = super.getFlow(httpTransport);
            credential = flow.loadCredential(userId);
            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl()));
                session.setAttribute("userId", userId);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            LogManager.getLogger().error("Error while loading credential (or Google Flow)", e);
        }
        return response;
    }
}
