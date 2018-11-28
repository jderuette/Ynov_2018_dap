package fr.ynov.dap.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.google.GoogleAccountRepository;

/**
 * Service for account google.
 * 
 * @author loic
 *
 */
@Service
public class GoogleAccountService extends GoogleService {

	/** The Constant SENSIBLE_DATA_FIRST_CHAR. */
	private static final int SENSIBLE_DATA_FIRST_CHAR = 2;

	/** The Constant SENSIBLE_DATA_LAST_CHAR. */
	private static final int SENSIBLE_DATA_LAST_CHAR = 9;
	/**
	 * logger for log.
	 */
	private static final Logger LOGGER = LogManager.getLogger(GoogleAccountService.class);

	/** The google account repository. */
	@Autowired
	private GoogleAccountRepository googleAccountRepository;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Handle the Google response.
	 *
	 * @param code
	 *            The (encoded) code use by Google (token, expirationDate,...)
	 * @param decodedCode
	 *            the decoded code
	 * @param redirectUri
	 *            the redirect uri
	 * @param accountName
	 *            the account name
	 * @return the view to display
	 * @throws ServletException
	 *             When Google account could not be connected to DaP.
	 * @throws GeneralSecurityException
	 *             the general security exception
	 */
	public String oAuthCallback(final String code, final String decodedCode, final String redirectUri,
			final String accountName) throws ServletException, GeneralSecurityException {

		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

			final Credential credential = flow.createAndStoreCredential(response, accountName);
			if (null == credential || null == credential.getAccessToken()) {
				LOGGER.warn("Trying to store a NULL AccessToken for user : " + accountName);
			}

			if (LOGGER.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
					LOGGER.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
							+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
				}
			}
		} catch (IOException e) {
			LOGGER.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to conenct Google Account");
		}

		return "redirect:/";
	}

	/**
	 * Add a Google account (user will be prompt to connect and accept required
	 * access).
	 *
	 * @param accountName
	 *            the account name
	 * @param userKey
	 *            the user key
	 * @param redirectUri
	 *            the redirect uri
	 * @param session
	 *            the HTTP session
	 * @return the view to Display (on Error)
	 * @throws GeneralSecurityException
	 *             *GeneralSecurityException*
	 */
	public String addAccount(final String accountName, final String userKey, String redirectUri, HttpSession session)
			throws GeneralSecurityException {
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow = null;
		Credential credential = null;

		AppUser user = appUserRepository.findByUserkey(userKey);

		if (user == null) {
			LOGGER.error("user not found");
			return "index";
		}
		GoogleAccount account = new GoogleAccount();
		account.setName(accountName);
		account.setOwner(user);
		try {
			try {
				flow = super.getFlow();
			} catch (GeneralSecurityException e) {
				LOGGER.error("Error loading credential or Google Flow", e);
			}
			credential = flow.loadCredential(accountName);

			if (credential != null && credential.getAccessToken() != null) {
				response = "AccountAlreadyAdded";
				LOGGER.error("AccountAlreadyAdded");
			} else {
				googleAccountRepository.save(account);
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(redirectUri);
				session.setAttribute("accountName", accountName);
				response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
			LOGGER.error("Error loading credential or Google Flow", e);
		}
		return response;
	}
}
