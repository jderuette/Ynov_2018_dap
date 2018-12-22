package fr.ynov.dap.google.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

/**
 * The Class GoogleAccountService.
 */
@Service
public class GoogleAccountService extends GoogleService{
	
    //TODO brc by Djer |POO| Pas top de les positionner à 0. Permet d'afficher une partie d'une information senssible pour identifier d'éventuel problèmes (ici first=3 et last=9 serait pas mal)
	/** The Constant SENSIBLE_DATA_LAST_CHAR. */
	private static final int SENSIBLE_DATA_LAST_CHAR = 0;
	
	/** The Constant SENSIBLE_DATA_FIRST_CHAR. */
	private static final int SENSIBLE_DATA_FIRST_CHAR = 0;
	
	/** The logger. */
	 //TODO brc by Djer |POO| Devrait être écris en Majuscule (car static et final)
    //TODO brc by Djer |Audit Code| static devrait être avant final (PMD/Checkstyle te préviennent de cette inversion)
	private final static Logger logger = LogManager.getLogger(GoogleAccountService.class);

	/**
	 * Handle the Google response.
	 *
	 * @param decodedCode the decoded code
	 * @param redirectUri the redirect uri
	 * @param userId the user id
	 * @return the view to display
	 * @throws ServletException When Google account could not be connected to DaP.
	 */
    public String oAuthCallback(final String decodedCode, final String redirectUri,
    		final String userId) throws ServletException {
    	
        try {
            GoogleAuthorizationCodeFlow flow = null;
			try {
				flow = getFlow();
			} catch (GeneralSecurityException e) {
			    //TODO brc by Djer |Log4J| Ne log pas juste l'exception. Ajoute ton message (contextualisé) et ajoute la "cause" en deuxième paramètre.
				logger.error(e);
			}
			//TODO brc by Djer |API Google| "decodedCode" est une information  (un peu) senssible évite de l'afficher (en entier) dasn les logs
			logger.info("decodedCode : " + decodedCode + "for user : " + userId);
			logger.info("redirectUri : " + redirectUri + "for user : " + userId);

            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
            
            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
                logger.warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (logger.isDebugEnabled() && null != credential && null != credential.getAccessToken()) {
                    logger.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                                    SENSIBLE_DATA_LAST_CHAR));
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            //TODO brc by Djer |Log4J| Contextualise tes messages
            logger.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }
        logger.info("oauthCallback OK");
        
        return "redirect:/index";
    }

    /**
     * retrieve the User ID in Session.
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no User Id in session
     */
  //TODO brc by Djer |MVC| C'est bien d'avoir "mutulaiser" ce code, mais comme il travail avec des objets "Web" (Request) il serait mieux dans les controller (éventuellement dans un "helper")
    public String getUserid(final HttpSession session) throws ServletException {
        String userId = null;
        if (null != session && null != session.getAttribute("userId")) {
            userId = (String) session.getAttribute("userId");
        }

        if (null == userId) {
            logger.error("userId in Session is NULL in Callback");
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
    //TODO brc by Djer |MVC| C'est bien d'avoir "mutulaiser" ce code, mais comme il travail avec des objets "Web" (Request) il serait mieux dans les controller (éventuellement dans un "helper")
    public String extracCode(final HttpServletRequest request) throws ServletException {
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
  //TODO brc by Djer |MVC| C'est bien d'avoir "mutulaiser" ce code, mais comme il travail avec des objets "Web" (Request) il serait mieux dans les controller (éventuellement dans un "helper")
    public String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }
    
    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     *
     * @param accountName the account name
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException the general security exception
     */
  //TODO brc by Djer |MVC| Il faudrait que le controller extrait les informations de la requete et de la session pour éviter qu'un service "metier" dépendande de ces objets
    public String addAccount(final String accountName, final HttpServletRequest request,
            final HttpSession session) throws GeneralSecurityException {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = getFlow();  
            credential = flow.loadCredential(accountName);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(buildRedirectUri(request, cfg.getoAuth2CallbackUrl()));
                // store userId in session for CallBack Access
                session.setAttribute("userId", accountName);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
            //TODO brc by Djer |Log4J| Contextualise tes messages
            logger.error("Error while loading credential (or Google Flow)", e);
        }
        // only when error occurs, else redirected BEFORE
        return response;
    }
}
