package fr.ynov.dap.dap.service.microsoft;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The Class MicrosoftAccountService.
 */
@Service
public class MicrosoftAccountService extends OutlookCredentialService {

	/**
	 * Gets the login url.
	 *
	 * @param state
	 *            the state
	 * @param nonce
	 *            the nonce
	 * @return the login url
	 */
	public String getLoginUrl(final UUID state, final UUID nonce) {

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
		urlBuilder.queryParam("client_id", getAppId());
		urlBuilder.queryParam("redirect_uri", getRedirectUrl());
		urlBuilder.queryParam("response_type", "code id_token");
		urlBuilder.queryParam("scope", getScopes());
		urlBuilder.queryParam("state", state);
		urlBuilder.queryParam("nonce", nonce);
		urlBuilder.queryParam("response_mode", "form_post");

		return urlBuilder.toUriString();
	}

}
