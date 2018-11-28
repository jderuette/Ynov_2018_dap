package fr.ynov.dap.microsoft.service;

import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import fr.ynov.dap.contract.MicrosoftAccountRepository;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.microsoft.model.TokenResponse;
import fr.ynov.dap.model.Credential;
import fr.ynov.dap.model.enumeration.CredentialTypeEnum;

/**
 * Base class to communicate with Microsoft API.
 * @author Kévin Sibué
 *
 */
@Service
public class MicrosoftAccountService extends OutlookAPIService {

    /**
     * Construct login url.
     * @param state Current state
     * @param nonce Current nonce
     * @return Login url
     */
    public String getLoginUrl(final UUID state, final UUID nonce) {

        if (getConfig() == null) {
            throw new NoConfigurationException();
        }

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(AUTHORIZE_URL);
        urlBuilder.queryParam("client_id", getConfig().getMicrosoftAppId());
        urlBuilder.queryParam("redirect_uri", getConfig().getMicrosoftRedirectUrl());
        urlBuilder.queryParam("response_type", "code id_token");
        urlBuilder.queryParam("scope", getScopes());
        urlBuilder.queryParam("state", state);
        urlBuilder.queryParam("nonce", nonce);
        urlBuilder.queryParam("response_mode", "form_post");

        return urlBuilder.toUriString();

    }

    /**
     * Get every credentials stored in database.
     * @param repo token repository
     * @return List of every Microsoft credential stored in database
     */
    public final ArrayList<Credential> getStoredCredentials(final MicrosoftAccountRepository repo) {

        if (repo == null) {
            return new ArrayList<>();
        }

        ArrayList<Credential> credentials = new ArrayList<>();

        repo.findAll().forEach(account -> {

            TokenResponse token = account.getToken();

            Credential newCredential = new Credential();
            newCredential.setUserId(account.getAccountName());
            newCredential.setToken(token.getAccessToken());
            newCredential.setRefreshToken(token.getRefreshToken());
            newCredential.setExpirationTime(token.getExpirationTime().getTime());
            newCredential.setTenantId(account.getTenantId());
            newCredential.setType(CredentialTypeEnum.MICROSOFT);

            credentials.add(newCredential);

        });

        return credentials;

    }

}
