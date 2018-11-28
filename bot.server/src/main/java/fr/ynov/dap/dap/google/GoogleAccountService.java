package fr.ynov.dap.dap.google;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repository.AppUserRepository;
import fr.ynov.dap.dap.repository.GoogleAccountRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The Class GoogleAccount.
 */
@Service
public class GoogleAccountService extends GoogleService {

	/** The log. */
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);

	/** The sensible data first char. */
	private final int SENSIBLE_DATA_FIRST_CHAR = 0;

	/** The sensible data last char. */
	private final int SENSIBLE_DATA_LAST_CHAR = 1;

	/** The google account repo. */
	@Autowired
	private GoogleAccountRepository googleAccountRepo;

	/** The app user repo. */
	@Autowired
	private AppUserRepository appUserRepo;

	/**
	 * Instantiates a new google account.
	 */
	public GoogleAccountService() {
		super();
	}

	/**
	 * O auth callback.
	 *
	 * @param decodedCode the decoded code
	 * @param redirectUri the redirect uri
	 * @param accountName the account name
	 * @param userKey the user key
	 * @return the string
	 * @throws ServletException the servlet exception
	 */

	/*
	 * GoogleAccount googleAccount = new GoogleAccount();
	 * googleAccount.setName(accountName); googleAccountRepo.save(googleAccount);
	 */

	public String oAuthCallback(String decodedCode, String redirectUri, final String accountName, final String userKey)
			throws ServletException {
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
							+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
				}
			}

			GoogleAccount googleAccount = new GoogleAccount();
			AppUser appUser = appUserRepo.findByName(userKey);
			googleAccount.setName(accountName);
			appUser.addGoogleAccount(googleAccount);
			googleAccountRepo.save(googleAccount);

			// onSuccess(request, resp, credential);
		} catch (IOException e) {
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to connect Google Account");
		}

		return "redirect:/welcome/" + accountName;
	}

	/**
	 * Adds the account.
	 *
	 * @param accountName the account name
	 * @param userKey the user key
	 * @param redirectUri the redirect uri
	 * @param session the session
	 * @return the string
	 */
	public String addAccount(final String accountName,final String userKey, String redirectUri, final HttpSession session) {
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow;
		Credential credential = null;
		try {
			flow = super.getFlow();
			credential = flow.loadCredential(accountName);
			if (credential != null && credential.getAccessToken() != null) {
				response = "AccountAlreadyAdded";
			} else {
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(redirectUri);
				session.setAttribute("accountName", accountName);
				session.setAttribute("userKey", userKey);
				response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
			LOG.error("Error while loading credential (or Google Flow)", e);
		}

		return response;
	}
}
