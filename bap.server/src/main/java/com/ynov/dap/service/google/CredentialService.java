package com.ynov.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

/**
 * The Class CredentialService.
 */
@Service
public class CredentialService extends GoogleService {

	/**
	 * Return credentials.
	 *
	 * @return the data store
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DataStore<StoredCredential> returnCredentials() throws GeneralSecurityException, IOException {
		DataStore<StoredCredential> credentials = null;
		credentials = getFlow().getCredentialDataStore();
		return credentials;
	}

	/* (non-Javadoc)
	 * @see com.ynov.dap.service.BaseService#getClassName()
	 */
	@Override
	protected String getClassName() {
        return GoogleContactService.class.getName();
	}

}
