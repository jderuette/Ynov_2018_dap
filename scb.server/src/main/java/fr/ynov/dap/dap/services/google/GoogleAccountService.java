package fr.ynov.dap.dap.services.google;

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
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dap.data.Account.AccountType;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;

@Controller
public class GoogleAccountService extends GoogleService {
	@Autowired
	AppUserRepository repository;
	
	private GoogleAccountService(){
		super();
	}
	
	public Logger LOG = LogManager.getLogger(GoogleAccountService.class);

	/**
	 * Callback that ask user to connect with a google account
	 * @param code
	 * @param request
	 * @param session
	 * @throws ServletException
	 * @throws GeneralSecurityException
	 */
	public void oAuthCallback(final String code, final HttpServletRequest request, final HttpSession session) throws ServletException, GeneralSecurityException {
		
		final String decodedCode = extracCode(request);

		final String redirectUri = buildRedirectUri(request, config.getoAuth2CallbackUrl());

		final String accountName = getAccountName(session);
		try {
			
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
			final Credential credential = flow.createAndStoreCredential(response, accountName);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + accountName);
			}

			if (LOG.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
					LOG.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
							+ credential.getAccessToken().substring(config.getSensibleDataFirstChar(),
									config.getSensibleDataLastChar()));
				}
			}
			// onSuccess(request, resp, credential);
		} catch (IOException e) {
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}
	}

	/**
	 * retrieve the User ID in Session.
	 * 
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
			LOG.error("userId in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google acocunt : accountName is NULL is User Session");
		}
		return accountName;
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
	protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}


	public String addAccount(final String userKey, 
			final String accountName,
			final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		AppUser currentUser = repository.findByName(userKey);
		if(currentUser == null) {
			return "This user doesn't exist";
		}
		else {
			String response = "errorOccurs";
			GoogleAuthorizationCodeFlow flow;
			Credential credential = null;
			try {
				flow = super.getFlow();
				credential = flow.loadCredential(accountName);

				if (credential != null && credential.getAccessToken() != null) {
					return "Account Already Added";
				} else {
					
					// Add this google account to relation in database
					GoogleAccount gAccount = new GoogleAccount();
					gAccount.setOwner(currentUser);
					gAccount.setName(accountName);
					gAccount.setAccountType(AccountType.Google);
					
					currentUser.addAccount(gAccount);
					
					repository.save(currentUser);
					
					// redirect to the authorization flow
					final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
					authorizationUrl.setRedirectUri(buildRedirectUri(request, config.getoAuth2CallbackUrl()));
					// store userId in session for CallBack Access
					session.setAttribute("accountName", accountName);

					response = "redirect:" + authorizationUrl.build();
				}
			} catch (IOException e) {
				LOG.error("Error while loading credential (or Google Flow)", e);
			}
			// only when error occurs, else redirected BEFORE
			return response;
		}
		

	}
	
	/**
	 * Create a Application user with no google account
	 * @param userKey Application user name
	 * @return
	 */
	public String AddUser(String userKey) {
		AppUser appUser = repository.findByName(userKey);
		
		if(appUser == null) {
			appUser = new AppUser();
			appUser.setName(userKey);
			repository.save(appUser);
			return "appUser "+userKey+" successfully created";
		}else {
			return "appUser "+userKey+" already exist";

		}
	}
}
