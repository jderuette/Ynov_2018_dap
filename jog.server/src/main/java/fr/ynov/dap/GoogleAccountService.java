package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;

@Controller
//TODO jog by Djer |MVC| Il faudrait séparer Service/controller (les "@RequestMapping" doivent être dnas le controller et idéalement els paramtre "Requets" et "session" doivent être géré dans le controller)
public class GoogleAccountService extends GoogleService {
	public GoogleAccountService() throws GeneralSecurityException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	private static Logger LOG = LogManager.getLogger();

	/**
	 * Handle the Google response.
	 * 
	 * @param request
	 *            The HTTP Request
	 * @param code
	 *            The (encoded) code use by Google (token, expirationDate,...)
	 * @param session
	 *            the HTTP Session
	 * @return the view to display
	 * @throws ServletException
	 *             When Google account could not be connected to DaP.
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/oAuth2Callback")
	public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException, GeneralSecurityException {
		final String decodedCode = extracCode(request);

		final String redirectUri = buildRedirectUri(request, configuration.getoAuth2CallbackUrl());

		final String userId = getUserid(session);
		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

			final com.google.api.client.auth.oauth2.Credential credential = flow.createAndStoreCredential(response,
					userId);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
			}

		} catch (IOException e) {
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}

		return "redirect:/";
	}

	/**
	 * retrieve the User ID in Session.
	 * 
	 * @param session
	 *            the HTTP Session
	 * @return the current User Id in Session
	 * @throws ServletException
	 *             if no User Id in session
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
	 * @param request
	 *            the HTTP request to extract OAuth2 code
	 * @return the decoded code
	 * @throws ServletException
	 *             if the code cannot be decoded
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
	 * @param req
	 *            The current HTTP request to extract schema, host, port
	 *            informations
	 * @param destination
	 *            the "path" to the resource
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
	 * 
	 * @param userId
	 *            the user to store Data
	 * @param request
	 *            the HTTP request
	 * @param session
	 *            the HTTP session
	 * @return the view to Display (on Error)
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/add/account/{accountName}")
	public String addAccount(@PathVariable final String accountName, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
	    //TODO jog by Djer |Rest API| pas de SysOut sur un serveur
		System.out.println(accountName);
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
				authorizationUrl.setRedirectUri(buildRedirectUri(request, configuration.getoAuth2CallbackUrl()));
				// store userId in session for CallBack Access
				session.setAttribute("userId", accountName);
				response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
		    //TODO jog by Djer |log4J| Contextualise tes messages (" for accountName : " + accountName)
			LOG.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		return response;
	}

}
