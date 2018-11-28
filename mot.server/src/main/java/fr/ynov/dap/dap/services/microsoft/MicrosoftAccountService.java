package fr.ynov.dap.dap.services.microsoft;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.dap.Config;

/**
 * The Class MicrosoftAccountService.
 */
@Service
public class MicrosoftAccountService extends MicrosoftService {

	@Autowired
	private Config cfg;
	
	/**
	 * Gets the login url.
	 *
	 * @param state the state
	 * @param nonce the nonce
	 * @return the login url
	 */
	public String getLoginUrl(UUID state, UUID nonce) {

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(cfg.getAuthorizeUrl());
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
