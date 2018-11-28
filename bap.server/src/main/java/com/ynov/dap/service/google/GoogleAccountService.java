package com.ynov.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.repository.google.GoogleAccountRepository;

/**
 * The Class GoogleAccountService.
 */
@Service
public class GoogleAccountService extends GoogleService {

	/** The Constant SENSIBLE_DATA_FIRST_CHAR. */
	private static final int SENSIBLE_DATA_FIRST_CHAR = 0;

	/** The Constant SENSIBLE_DATA_LAST_CHAR. */
	private static final int SENSIBLE_DATA_LAST_CHAR = 1;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/** The google account repository. */
	@Autowired
	private GoogleAccountRepository googleAccountRepository;

	/**
	 * O auth callback.
	 *
	 * @param code the code
	 * @param decodedCode the decoded code
	 * @param redirectUri the redirect uri
	 * @param userId the user id
	 * @return the string
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String oAuthCallback(final String code, String decodedCode, String redirectUri, String userId)
			throws GeneralSecurityException, IOException {
		GoogleAuthorizationCodeFlow flow = super.getFlow();

		final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

		final Credential credential = flow.createAndStoreCredential(response, userId);
		if (null == credential || null == credential.getAccessToken()) {
			getLogger().error("Trying to store a NULL AccessToken for user : " + userId);
		}

		if (null != credential && null != credential.getAccessToken()) {
			getLogger().error("New user credential stored with userId : " + userId + "partial AccessToken : "
					+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
		}
		return "redirect:/";
	}

	/**
	 * Adds the account.
	 *
	 * @param userId the user id
	 * @param userKey the user key
	 * @param redirectUri the redirect uri
	 * @param session the session
	 * @return the string
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String addAccount(final String userId, final String userKey, String redirectUri, HttpSession session)
			throws GeneralSecurityException, IOException {
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow = null;
		Credential credential = null;

		AppUser appUser = appUserRepository.findByName(userKey);

		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return "index";
		}

		flow = super.getFlow();
		credential = flow.loadCredential(userId);

		if (credential != null && credential.getAccessToken() != null) {
			response = "AccountAlreadyAdded";
			getLogger().info("Info AccountAlreadyAdded for userId : " + userId);
		} else {
			GoogleAccount googleAccount = new GoogleAccount();
			googleAccount.setOwner(appUser);
			googleAccount.setName(userId);
			appUser.addGoogleAccount(googleAccount);
			googleAccountRepository.save(googleAccount);

			final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
			authorizationUrl.setRedirectUri(redirectUri);
			session.setAttribute("userId", userId);
			response = "redirect:" + authorizationUrl.build();
		}
		return response;
	}

	/* (non-Javadoc)
	 * @see com.ynov.dap.service.BaseService#getClassName()
	 */
	@Override
	public String getClassName() {
		return GoogleAccountService.class.getName();
	}

}
