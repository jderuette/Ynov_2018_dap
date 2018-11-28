package com.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.repository.microsoft.MicrosoftAccountRepository;
import com.ynov.dap.service.google.CredentialService;

/**
 * The Class AdminService.
 */
@Service
public class AdminService extends BaseService {

	/** The credential service. */
	@Autowired
	private CredentialService credentialService;

	/** The microsoft account repository. */
	@Autowired
	private MicrosoftAccountRepository microsoftAccountRepository;

	/**
	 * Gets the google data store.
	 *
	 * @return the google data store
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Map<String, Object> getGoogleDataStore() throws GeneralSecurityException, IOException {

		Map<String, Object> dataStore = new HashMap<String, Object>();
		DataStore<StoredCredential> credentials = credentialService.getFlow().getCredentialDataStore();

		for (String key : credentials.keySet()) {
			Map<String, Object> userData = new HashMap<String, Object>();
			StoredCredential values = credentials.get(key);
			userData.put("accessToken", values.getAccessToken());
			userData.put("refreshToken", values.getRefreshToken());
			userData.put("expirationTimeMilliseconds", values.getExpirationTimeMilliseconds());

			dataStore.put(key, userData);
		}

		return dataStore;
	}

	/**
	 * Gets the microsoft data store.
	 *
	 * @return the microsoft data store
	 */
	public Map<Integer, Object> getMicrosoftDataStore() {
		Map<Integer, Object> dataStore = new HashMap<Integer, Object>();

		Integer index = 0;
		for (MicrosoftAccount account : microsoftAccountRepository.findAll()) {
			Map<String, Object> userData = new HashMap<String, Object>();

			userData.put("tenantId", account.getTenantId());
			userData.put("tokenResponse", account.getTokenResponse().getAccessToken());
			userData.put("name", account.getName());

			dataStore.put(index, userData);
			index++;
		}

		return dataStore;
	}

	/* (non-Javadoc)
	 * @see com.ynov.dap.service.BaseService#getClassName()
	 */
	@Override
	protected String getClassName() {
		return AdminService.class.getName();
	}

}
