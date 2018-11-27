package com.ynov.dap.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.repository.microsoft.MicrosoftAccountRepository;
import com.ynov.dap.service.google.CredentialService;

@Service
public class AdminService {
	
    @Autowired
    private CredentialService credentialService;
    
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;
    
	public Map<String, Object> getGoogleDataStore() {
		try {
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
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
	
}
