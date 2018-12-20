package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
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
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;

import fr.ynov.dap.Config;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;

/**
 * Controller pour la gestion du compte google.
 * @author alex
 */
@Controller
public class GoogleAccountController extends ConnexionGoogle {
    /**
     * Début chaine de carractère visible du token.
     */
    private static final Integer SENSIBLE_DATA_FIRST_CHAR = 5;
    /**
     * Fin chaine de carractère visible du token.
     */
    private static final Integer SENSIBLE_DATA_LAST_CHAR = 5;
    /**
     * repository.
     */
    @Autowired
    private AppUserRepository repository;
    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google
     * (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account
     * could not be connected to DaP.
     * @throws IOException problème lié à la sauvegarde des credentials.
     * @throws GeneralSecurityException problème de connection au compte google
     */
    @RequestMapping("/oAuth2Callback")
    public final String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException, GeneralSecurityException, IOException {
        final String decodedCode = extracCode(request);
        final String redirectUri = buildRedirectUri(request, Config.getoAuth2CallbackUrl());
        final String userKey = getSessionParam(session, "userKey");
        final String userId = getSessionParam(session, "userId");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow(httpTransport);
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
              //TODO roa by Djer |log4J| Ne récupère pas une instance de logger "à la volé", utilise une constante de classe
                LogManager.getLogger().warn("Trying to store a NULL AccessToken for user : " + userId);
            }
            if (LogManager.getLogger().isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                  //TODO roa by Djer |log4J| Ne récupère pas une instance de logger "à la volé", utilise une constante de classe
                    LogManager.getLogger().debug("New user credential stored with userId : " + userId
                            + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
                }
            }
            GoogleAccount account = new GoogleAccount();
            account.setAccountName(userId);
            repository.findByUserKey(userKey).adGoogleAccount(account);
            //TODO roa by Djer |JPA| Attention, tu save "ce qui est lut en BDD". lors du "repository.findByUserKey(userKey)" conserve la valeur de retour (dans un "currentUser") puis ajoute le compte, et enfin "save" le "currentuser" qui aura été modifié
            repository.save(repository.findByUserKey(userKey));
        } catch (IOException e) {
          //TODO roa by Djer |log4J| Ne récupère pas une instance de logger "à la volé", utilise une constante de classe
            //TODO roa by Djer |Log4J| Contextualise tes messages (" for userKey : " + userKey + " and accountName : " + userId)
            LogManager.getLogger().error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying " + "to connect Google Account");
        }
        return "redirect:/";
    }
    /**
     * Retrieve the User ID in Session.
     * @param session the HTTP Session
     * @param nomParam parammètre à récupérer
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    private String getSessionParam(final HttpSession session, final String nomParam) throws ServletException {
        String param = null;
        if (null != session && null != session.getAttribute(nomParam)) {
            param = (String) session.getAttribute(nomParam);
        }
        if (null == param) {
          //TODO roa by Djer |log4J| Ne récupère pas une instance de logger "à la volé", utilise une constante de classe
            LogManager.getLogger().error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add " + "Google account : userId is NULL is User Session");
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
          //TODO roa by Djer |log4J| Ne récupère pas une instance de logger "à la volé", utilise une constante de classe
            LogManager.getLogger().error("Error when trying" + " to add Google acocunt : " + responseUrl.getError());
            throw new ServletException("Error when trying" + " to add Google acocunt");
        }
        return decodeCode;
    }
    /**
     * Build a current host (and port) absolute URL.
     * @param req         The current HTTP reques
     * to extract schema, host, port informations
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
     * @param accountName  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @param user le propriétaire du compte
     * @return the view to Display (on Error)
     * @throws IOException problème de chargement des credentials
     * @throws GeneralSecurityException problème de connection au compte google
     */
    @RequestMapping("/add/account/{accountName}")
    public final String addAccount(@PathVariable final String accountName, final @RequestParam("userKey") String user,
            final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException, IOException {
        String response = "errorOccurs";
        LogManager.getLogger().info("fonction addAccount");
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        try {
            flow = super.getFlow(httpTransport);
            credential = flow.loadCredential(accountName);
            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, Config.getoAuth2CallbackUrl()));
                session.setAttribute("userKey", user);
                session.setAttribute("userId", accountName);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
          //TODO roa by Djer |log4J| Ne récupère pas une instance de logger "à la volé", utilise une constante de classe
            LogManager.getLogger().error("Error while loading credential (or Google Flow)", e);
        }
        return response;
    }
}
