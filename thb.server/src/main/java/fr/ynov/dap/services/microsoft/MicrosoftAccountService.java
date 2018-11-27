package fr.ynov.dap.services.microsoft;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MicrosoftAccountService extends MicrosoftService {
	public String getLoginUrl(UUID state, UUID nonce) {

		UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(super.getConf().getAuthorizeUrl());
		urlBuilder.queryParam("client_id", super.getAppId());
		urlBuilder.queryParam("redirect_uri", super.getRedirectUrl());
		urlBuilder.queryParam("response_type", "code id_token");
		urlBuilder.queryParam("scope", super.getScopes());
		urlBuilder.queryParam("state", state);
		urlBuilder.queryParam("nonce", nonce);
		urlBuilder.queryParam("response_mode", "form_post");

		return urlBuilder.toUriString();
	}
}