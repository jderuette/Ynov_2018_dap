package fr.ynov.dap.dap.Services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dap.Config;

@Controller
public class GoogleAccount extends GoogleService {
	
	private GoogleAccount(){
		super();
	}
	
	public Logger LOG = LogManager.getLogger(GoogleAccount.class);

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

		final String userId = getUserid(session);
		try {
			
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
			final Credential credential = flow.createAndStoreCredential(response, userId);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
			}

			if (LOG.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
					LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
							+ credential.getAccessToken().substring(Config.SENSIBLE_DATA_FIRST_CHAR,
									Config.SENSIBLE_DATA_LAST_CHAR));
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
	private String getUserid(final HttpSession session) throws ServletException {
		String userId = null;
		if (null != session && null != session.getAttribute("userId")) {
			userId = (String) session.getAttribute("userId");
		}

		if (null == userId) {
			LOG.error("userId in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
		}
		return userId;
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


	public String addAccount(final String userId, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		
	    //TODO scb by Djer veuillez agréer Monsieur mes sincères saltations.
		LOG.debug("Salut");

		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow;
		Credential credential = null;
		try {
			flow = super.getFlow();
			credential = flow.loadCredential(userId);

			if (credential != null && credential.getAccessToken() != null) {
				return "Account Already Added";
			} else {
				// redirect to the authorization flow
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(buildRedirectUri(request, config.getoAuth2CallbackUrl()));
				// store userId in session for CallBack Access
				session.setAttribute("userId", userId);
				System.out.println(authorizationUrl.build());
				response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
			LOG.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		return response;
	}
}
