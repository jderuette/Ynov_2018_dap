package fr.ynov.dap.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;

/**
 * The Class GoogleAccount.
 */
@Controller
public class GoogleAccountService extends GoogleServices {

	public GoogleAccountService() throws IOException, GeneralSecurityException {
		super();
		
	}


	@Autowired 
	public AppUserRepository repo;
	
	//TODO brs by Djer |JavaDoc| Supprime cette JavaDoc si elle n'est plus utile
	/**
	 * Instantiates a new google account.
	 * @param accountName 
	 *
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	

	/**
	 * Handle the Google response.
	 *
	 * @param code    The (encoded) code use by Google (token, expirationDate,...)
	 * @param request The HTTP Request
	 * @param session the HTTP Session
	 * @return the view to display
	 * @throws ServletException         When Google account could not be connected
	 *                                  to DaP.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/oAuth2Callback")
	public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException, GeneralSecurityException {
		final String decodedCode = extracCode(request);

		final String redirectUri = buildRedirectUri(request, super.getConfiguration().getoAuth2CallbackUrl());

		final String userKey = getUserKey(session);
		final String accountName = getAccountName(session);
		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

			final Credential credential = flow.createAndStoreCredential(response, accountName);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + userKey);
			}else {
					
				AppUser user0 = repo.findByUserkey(userKey);
				 	if(user0 == null) {
				 		AppUser user = new AppUser();
				 		user.setUserkey(userKey);
				 		//repo.save(user);
				 		GoogleAccount gAccount = new GoogleAccount();
		                gAccount.setAccountName(accountName);

		                user.addGoogleAccount(gAccount);

		                repo.save(user);
				 		}
				 	else {
				 		AppUser user = repo.findByUserkey(userKey);
				 		GoogleAccount gAccount = new GoogleAccount();
		                gAccount.setAccountName(accountName);

		                user.addGoogleAccount(gAccount);

		                repo.save(user);
				 	}
	                
				
			}
			
			//TODO brs by Djer : |Log4J| Contextualise entièrement le message, en lisant la log on aura l'impression que 'accountName" est toujours vide !
			LOG.info("New user credential stored with userKey : " + userKey+" AccountName : " + ", partial AccessToken : "
					+ credential.getAccessToken().substring('.'));
			if (LOG.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
				  //TODO brs by Djer |Log4J| Contextualise entièrement le message, en lisant la log on aura l'impression que 'accountName" est toujours vide !
					LOG.debug("New user credential stored with userId : " + userKey +"AccountName"+ "partial AccessToken : "
							+ credential.getAccessToken().substring('.'));
				}
			}
			// onSuccess(request, resp, credential);
		} catch (IOException e) {
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}

		return  "redirect:/?userKey="+userKey;
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
			LOG.error("userId in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
		}
		return userKey;
	}
	private String getAccountName(final HttpSession session) throws ServletException {
		
		String accountName = null;
		if (null != session  && null != session.getAttribute("accountName")) {
			
			accountName = (String) session.getAttribute("accountName");
			
		}

		if (null == accountName) {
			accountName = null;
			//TODO brs by Djer |Log4J| Ce message est faux ! C'est le "accountName" qui est null en session, pas le "userId"
			LOG.error("userId in Session is NULL in Callback");
			//TODObrs by Djer |POO| Pourquoi changer le comportement alors qu'il est très simillaire à la méthode juste au dessus ? 
			//throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
		}
		return accountName;
	}

	/**
	 * Add a Google account (user will be prompt to connect and accept required
	 * access).
	 *
	 * @param userId  the user to store Data
	 * @param request the HTTP request
	 * @param session the HTTP session
	 * @return the view to Display (on Error)
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/account/add")
	public String addAccount(@RequestParam("userKey") final String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow;
		Credential credential = null;
		try {
			flow = super.getFlow();
			credential = flow.loadCredential(userKey);

			if (credential != null && credential.getAccessToken() != null) {
				response = "AccountAlreadyAdded";
			} else {
				// redirect to the authorization flow
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl()));
				// store userId in session for CallBack Access
				session.setAttribute("userKey", userKey);
				response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
		    //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		return response;
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
			LOG.error("Error when trying to add Google acocunt : " + responseUrl.getError());
			throw new ServletException("Error when trying to add Google acocunt");
			// onError(request, resp, responseUrl);
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
	public String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}

	

	

}
