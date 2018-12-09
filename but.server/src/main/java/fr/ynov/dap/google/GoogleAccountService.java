package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

/**
 * @author thibault
 *
 */
@Service
public class GoogleAccountService extends GoogleService {

    /**
     * Account name key.
     */
    public static final String ACCOUNT_NAME_KEY = "accountName";

    /**
     * User key.
     */
    public static final String USER_KEY = "userKey";

    /**
     * Logger for the class.
     */
    //TODO but by Djer |Log4J| Devrait être final
    private static Logger logger = LogManager.getLogger();

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    
    //TODO but by Djer |POO| Met les constante avant les attiruts (ordre : constantes, attributs, initialisateur static, constructeur, methodes métiers, méthodes "génériques" (toString, HasCode,...) getter/setter)
    /**
     * Index of first char for sensible data.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 0;
    /**
     * Index of last char for sensible data.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 5;

    /**
     * retrieve the key in Session.
     * @param key key of parameter
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
    //TODO but by Djer |POO| Pourquoi public ? Expose le moins possible ton code, créé en privé, passe en protected/public lorsque c'est NECESSAIRE. Cette méthode devrait être du coté des "controller" (dans le GoogleAccountController qui est le seul à l'utiliser pour le moment)
    public String getInSession(final String key, final HttpSession session) throws ServletException {
        String param = null;
        if (null != session && null != session.getAttribute(key)) {
            param = (String) session.getAttribute(key);
        }
        //TODO but by Djer |POO| Tu pourrais en profiter pour vérifier qu'il n'est pas "vide" car ce ne serait pas normal non plus
        if (null == param) {
            logger.error(key + " in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google account : " + key + " is NULL in User Session");
        }
        return param;
    }

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param accountName the google account to add.
     * @param owner Owner of credential (account name)
     * @throws ServletException When Google account could not be connected to DaP.
     * @return true if success add account.
     */
    public Boolean oAuthCallback(final String code, final HttpServletRequest request,
            final String accountName, final AppUser owner)
            throws ServletException {
        Boolean success = false;
        final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, getConfig().getOAuth2CallbackUrl());

        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow(owner);
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);
            success = true;
            if (null == credential || null == credential.getAccessToken()) {
                logger.warn("Trying to store a NULL AccessToken for user : " + accountName);
            }

            if (credential.getRefreshToken() != null) {
                //TODO but by Djer |Log4J| Devrait être un warning ? Voir une "error" ? (si le refreshToken est "null" c'est que "Google" à déconné !)
                logger.info("Refresh token is not null");
            } else {
                logger.info("Refresh token is null!");
            }

            if (logger.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    //TODO but by Djer |POO| Essaye d'utiliser un StringBuilder, les "+" sur des String en Java est "dangereux" pour les perfs (utilisation mémoire)
                    logger.debug("New user credential stored with accountName : " + accountName
                            + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
                }
            }
        } catch (IOException | GeneralSecurityException e) {
            logger.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to connect Google Account");
        }
        return success;
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
            logger.error("Error when trying to add Google acocunt : " + responseUrl.getError());
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
     * Request URI to add google account.
     * @param accountName the user to store Data
     * @param userKey the user key logged.
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the redirect URI.
     * @throws IOException If the credentials.json file cannot be found or bad request.
     * @throws GeneralSecurityException Security on Google API
     */
    public String getAddAccountGoogleRedirectURI(final String accountName, final String userKey,
            final HttpServletRequest request, final HttpSession session) throws IOException, GeneralSecurityException {
        String response = null;
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;

        AppUser owner = repositoryUser.findByUserKey(userKey);

        flow = super.getFlow(owner);
        credential = flow.loadCredential(accountName);

        if (credential != null && credential.getAccessToken() != null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "AccountAlreadyAdded");
        } else {
            final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
            authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getOAuth2CallbackUrl()));

            // store userId in session for CallBack Access
            session.setAttribute(ACCOUNT_NAME_KEY, accountName);
            session.setAttribute(USER_KEY, userKey);

            response = authorizationUrl.build();
        }
        return response;
    }
}
