package fr.ynov.dap.dap.web;

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

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.dap.google.GoogleAccountService;

/**
 * The Class GoogleAccountController.
 */
@Controller
public class GoogleAccountController {

	/** The google account. */
	@Autowired
	private GoogleAccountService googleAccountService;
	
	/** The log. */
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	/**
	 * O auth callback.
	 *
	 * @param code the code
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws ServletException the servlet exception
	 */
	@RequestMapping("/oAuth2Callback")
	public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException {
		final String decodedCode = extracCode(request);
		final String redirectUri = buildRedirectUri(request, googleAccountService.getConfiguration().getRedirectUrl());
		final String accountName = getUserAccount(session);
		final String userKey = getUserKey(session);
		
		return googleAccountService.oAuthCallback(decodedCode, redirectUri, accountName, userKey);
	}
	
	/**
	 * Adds the account.
	 *
	 * @param accountName the account name
	 * @param userKey the user key
	 * @param request the request
	 * @param session the session
	 * @return the string
	 */
	@RequestMapping("/account/add/google/{accountName}")
	public String addAccount(@PathVariable("accountName") final String accountName,
			@RequestParam("userKey") final String userKey,
			final HttpServletRequest request,
            final HttpSession session) {
		
		final String redirectUri = buildRedirectUri(request, googleAccountService.getConfiguration().getRedirectUrl());
		return googleAccountService.addAccount(accountName, userKey, redirectUri, session);
	}
	
	/**
	 * Gets the userid.
	 *
	 * @param session the session
	 * @return the userid
	 * @throws ServletException the servlet exception
	 */
	private String getUserAccount(final HttpSession session) throws ServletException {
		String accountName = null;
		if (null != session && null != session.getAttribute("accountName")) {
			accountName = (String) session.getAttribute("accountName");
		}

		if (null == accountName) {
			LOG.error("accountName in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
		}
		return accountName;
	}
	
	/**
	 * Gets the user key.
	 *
	 * @param session the session
	 * @return the user key
	 * @throws ServletException the servlet exception
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
	
	/**
	 * Extrac code.
	 *
	 * @param request the request
	 * @return the string
	 * @throws ServletException the servlet exception
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
	 * Builds the redirect uri.
	 *
	 * @param req the req
	 * @param destination the destination
	 * @return the string
	 */
	protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}
}
