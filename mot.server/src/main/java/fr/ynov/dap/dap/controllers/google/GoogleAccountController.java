package fr.ynov.dap.dap.controllers.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.dap.services.google.GoogleService;

/**
 * The Class GoogleAccountController.
 */
@Controller
public class GoogleAccountController extends GoogleService {

	public GoogleAccountController() throws IOException, GeneralSecurityException {
		super();
	}

	private static final Integer SENSIBLE_DATA_FIRST_CHAR = 0;
	private static final Integer SENSIBLE_DATA_LAST_CHAR = 0;

	@Autowired
	public AppUserRepository appUserRepo;

	protected Logger LOG = LogManager.getLogger(GoogleAccountController.class);

	/**
	 * Adds the account.
	 *
	 * @param accountName the account name
	 * @param userKey     the user key
	 * @param request     the request
	 * @param session     the session
	 * @return the string
	 */
	@RequestMapping("/add/googleAccount/{accountName}")
	public @ResponseBody String addAccount(@PathVariable(value = "accountName") final String accountName,
			@RequestParam(value = "userKey", required = true) String userKey, final HttpServletRequest request,
			final HttpSession session, final HttpServletResponse responseServ) {

		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow = null;
		Credential credential = null;

		try {
			try {
				flow = super.getFlow();
			} catch (GeneralSecurityException e) {
				LOG.error(e.getLocalizedMessage());
			}

			credential = flow.loadCredential(accountName);

			if (credential != null && credential.getAccessToken() != null) {
				LOG.warn("User already added and saved in store");
			} else {
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(buildRedirectUri(request, getCfg().getAuth2CallbackUrl()));
				
				session.setAttribute("userId", userKey);
				session.setAttribute("accountName", accountName);
				
				responseServ.sendRedirect(authorizationUrl.build());
				return  "";
			}
		} catch (IOException e) {
			LOG.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		return response;
	}

	/**
	 * O auth callback.
	 *
	 * @param code    the code
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws ServletException         the servlet exception
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/oAuth2Callback")
	public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException, GeneralSecurityException {

		final String decodedCode = extracCode(request);
		final String redirectUri = buildRedirectUri(request, getCfg().getAuth2CallbackUrl());
		final String userKey = getUserKey(session);
		final String accountName = getAccountName(session);

		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

			final Credential credential = flow.createAndStoreCredential(response, accountName);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + accountName);
			} else {
				AppUser user = appUserRepo.findByUserKey(userKey);
				if (user == null) {

					user = new AppUser();
					user.setUserKey(userKey);
					
					GoogleAccount googleAccount = new GoogleAccount();
					googleAccount.setName(accountName);

					user.addGoogleAccount(googleAccount);
					appUserRepo.save(user);

				} else {
					GoogleAccount googleAccount = new GoogleAccount();
					googleAccount.setName(accountName);

					user.addGoogleAccount(googleAccount);
					appUserRepo.save(user);
				}
			}

			if (LOG.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
					LOG.debug("New user credential stored with userId : " + userKey + "partial AccessToken : "
							+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
				}
			}
			// onSuccess(request, resp, credential);
		} catch (IOException e) {
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}

		return "redirect:/welcome?userKey=" + userKey;
	}

	/**
	 * retrieve the User ID in Session.
	 * 
	 * @param session the HTTP Session
	 * @return the current User Key in Session
	 * @throws ServletException if no User Id in session
	 */
	private String getUserKey(final HttpSession session) throws ServletException {
		String userKey = null;
		if (null != session && null != session.getAttribute("userId")) {
			userKey = (String) session.getAttribute("userId");
		}

		if (null == userKey) {
			LOG.error("userKey in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google account : userKey is NULL is User Session");
		}
		return userKey;
	}

	/**
	 * retrieve the AccountName in Session.
	 * 
	 * @param session the HTTP Session
	 * @return The AccountName
	 * @throws ServletException if no AccountName in session
	 */
	private String getAccountName(final HttpSession session) throws ServletException {
		String accountName = null;
		if (null != session && null != session.getAttribute("accountName")) {
			accountName = (String) session.getAttribute("accountName");
		}

		if (null == accountName) {
			LOG.error("userKey in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google account : AccountName is NULL is User Session");
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
	public static String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}
}
