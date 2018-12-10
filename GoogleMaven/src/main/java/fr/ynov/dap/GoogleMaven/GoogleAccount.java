package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;
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
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import fr.ynov.dap.GoogleMaven.data.AppUser;
import fr.ynov.dap.GoogleMaven.repository.AppUserRepostory;
import fr.ynov.dap.GoogleMaven.repository.GoogleAccountRepository;

@Controller
public class GoogleAccount extends GoogleService {
    //TODO elj by Djer |Log4J| Attention ici tu crée un Logger qui a comme catégorie "vide" (root). Il vaut mieu ne pas mettre de paramtre, dans cas c'est Log4J qui va déterminer la catégorie (il utilisera le nom qualifié de la classe ce qui est une "bonne" catégorie)
	private static final Logger logger = LogManager.getLogger("");
	
	@Autowired
	AppUserRepostory appUserRepostory;
	
	@Autowired
	GoogleAccountRepository googleAccountRepository;

	/**
	 * Handle the Google response.
	 * @param request The HTTP Request
	 * @param code    The (encoded) code use by Google (token, expirationDate,...)
	 * @param session the HTTP Session
	 * @return the view to display
	 * @throws ServletException When Google account could not be connected to DaP.
	 * @throws GeneralSecurityException 
	 */
	@RequestMapping("/oAuth2Callback")
	public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
		final HttpSession session) throws ServletException, GeneralSecurityException {
		final String decodedCode = extracCode(request);
		final String redirectUri = buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl());

		final String userId = getUserid(session);
		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

			final com.google.api.client.auth.oauth2.Credential credential = flow.createAndStoreCredential(response, userId);
			if (null == credential || null == credential.getAccessToken()) {
				logger.warn("Trying to store a NULL AccessToken for user : " + userId);
			}

			if (logger.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
				    //TODO elj by Djer |Log4J| Pourquoi enlever une Log utile (lorsque "Google déconne") ?
					//LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
					//		+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
						//			SENSIBLE_DATA_LAST_CHAR));
				}
			}
			// onSuccess(request, resp, credential);
		} catch (IOException e) {
		    //TODO elj by Djer |Log4J| Contextualise tes messages (" for userKey : " + userId)
			logger.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}

		return "redirect:/";
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
			logger.error("Error when trying to add Google account : " + responseUrl.getError());
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
	 * @param userId  the user to store Data
	 * @param request the HTTP request
	 * @param session the HTTP session
	 * @return 
	 * @return the view to Display (on Error)
	 * @throws GeneralSecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */

	@RequestMapping("/account/add/{accountName}/{userKey}")
	public String addAccount(@PathVariable final String accountName, @PathVariable final String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException, InstantiationException, IllegalAccessException {
		//System.out.println("YOYO");
		
	    AppUser myuser = appUserRepostory.findByUserKey(userKey);
		
	    if (myuser == null){
			myuser = new AppUser(userKey);
			appUserRepostory.save(myuser);
		}
			
		
		//Launcher.SaveUser(AppUserRepostory, userId);
		
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow;
		com.google.api.client.auth.oauth2.Credential credential = null;
		try {
			flow = super.getFlow();
			credential = flow.loadCredential(accountName);

			if (credential != null && credential.getAccessToken() != null) {
				response = "AccountAlreadyAdded";
				
			} else {
				// redirect to the authorization flow
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl()));
				// store userId in session for CallBack Access
				session.setAttribute("userId", accountName);
				fr.ynov.dap.GoogleMaven.data.GoogleAccount	mygoogleaccount = new fr.ynov.dap.GoogleMaven.data.GoogleAccount(myuser,accountName,"","");
				mygoogleaccount.setOwner(myuser);
				googleAccountRepository.save(mygoogleaccount);
				System.out.println(mygoogleaccount.getAccountName());
				response = "redirect:" + authorizationUrl.build();
				
			}
		} catch (IOException e) {
			logger.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		//TODO elj by Djer |Rest API| Pas de SysOut sur un serveur
		System.out.println(response);
		
		return response;
	}



}