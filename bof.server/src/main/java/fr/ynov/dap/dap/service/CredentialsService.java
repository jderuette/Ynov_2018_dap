package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

@Service
public class CredentialsService extends GoogleService {
	
	public DataStore<StoredCredential> getCredentials() throws GeneralSecurityException, IOException{
		return getFlow().getCredentialDataStore();
	}

}
