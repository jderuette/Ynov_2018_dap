package fr.ynov.dap.microsoft;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.model.Credential;
import fr.ynov.dap.model.enumeration.CredentialEnum;
import fr.ynov.dap.repository.MicrosoftAccountRepository;

public class MicrosoftAccountService extends OutlookAPIService {

    public String getLoginUrl(final UUID state, final UUID nonce) {

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(getAuthorizeUrl());
        urlBuilder.queryParam("client_id", getConfig().getMicrosoftAppId());
        urlBuilder.queryParam("redirect_uri", getConfig().getMicrosoftRedirectUrl());
        urlBuilder.queryParam("response_type", "code id_token");
        urlBuilder.queryParam("scope", getScopes());
        urlBuilder.queryParam("state", state);
        urlBuilder.queryParam("nonce", nonce);
        urlBuilder.queryParam("response_mode", "form_post");

        return urlBuilder.toUriString();

    }
    public final ArrayList<Credential> getStoredCredentials(final MicrosoftAccountRepository microsoftAccountRepository) {

        if (microsoftAccountRepository == null) {
            return new ArrayList<>();
        }

        ArrayList<Credential> credentials = new ArrayList<>();
        microsoftAccountRepository.findAll().forEach(account -> {
            TokenResponse token = account.getToken();
            Credential newCredential = new Credential();
            newCredential.setUserId(account.getAccountName());
            newCredential.setToken(token.getAccessToken());
            newCredential.setRefreshToken(token.getRefreshToken());
            newCredential.setExpirationTime(token.getExpirationTime().getTime());
            newCredential.setTenantId(account.getTenantId());
            newCredential.setType(CredentialEnum.MICROSOFT);
            credentials.add(newCredential);

        });

        return credentials;

}
}
