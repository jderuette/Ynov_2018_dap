package com.ynov.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

@Service
public class CredentialService extends GoogleService {

	public DataStore<StoredCredential> returnCredentials() {
		DataStore<StoredCredential> credentials = null;
		try {
			credentials = getFlow().getCredentialDataStore();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return credentials;
	}

	@Override
	protected String getClassName() {
        return GoogleContactService.class.getName();
	}
	
}
