package fr.ynov.dap.dap.controller;

import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;

import fr.ynov.dap.dap.service.google.GoogleAccountService;
import fr.ynov.dap.dap.utils.BuildRedirectUri;

/**
 * The Class Oauth2Controller.
 */
@Controller
public class Oauth2Controller {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(Oauth2Controller.class);

	/** The google account. */
	@Autowired
	private GoogleAccountService googleAccount;

	/**
	 * O auth callback.
	 *
	 * @param code
	 *            the code
	 * @param request
	 *            the request
	 * @param session
	 *            the session
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @throws GeneralSecurityException
	 *             the general security exception
	 */
	@RequestMapping(value = "/oAuth2Callback")
	public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
			final HttpSession session) throws Exception, GeneralSecurityException {

		final String decodedCode = extractCode(request);
		final String redirectUri = new BuildRedirectUri().buildRedirectUri(request, "/oAuth2Callback");
		final String accountName = getAccountName(session);
		return googleAccount.oAuthCallback(code, decodedCode, redirectUri, accountName);
	}

	/**
	 * Extract code.
	 *
	 * @param request
	 *            the request
	 * @return the string
	 * @throws ServletException
	 *             the servlet exception
	 */
	public String extractCode(final HttpServletRequest request) throws ServletException {
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
			LOGGER.error("Error when trying to add Google acocunt : " + responseUrl.getError());
			throw new ServletException("Error when trying to add Google acocunt");
		}

		return decodeCode;
	}

	/**
	 * Gets the account name.
	 *
	 * @param session
	 *            the session
	 * @return the account name
	 * @throws ServletException
	 *             the servlet exception
	 */
	public String getAccountName(final HttpSession session) throws ServletException {
		String userId = null;
		if (null != session && null != session.getAttribute("accountName")) {
			userId = (String) session.getAttribute("accountName");
		}

		if (null == userId) {
			LOGGER.error("AccountName in Session is NULL in Callback");
			throw new ServletException("Error when trying to add Google acocunt :" + " userId is NULL is User Session");
		}
		return userId;
	}
}
