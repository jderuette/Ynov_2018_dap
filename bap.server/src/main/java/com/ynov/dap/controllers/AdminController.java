package com.ynov.dap.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.ynov.dap.google.business.CredentialService;
import com.ynov.dap.microsoft.data.MicrosoftAccount;
import com.ynov.dap.microsoft.repositories.MicrosoftAccountRepository;

@RequestMapping("admin")
@Controller
public class AdminController {
	
    @Autowired
    private CredentialService credentialService;
    
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;
	
	@GetMapping("/google")
	public String returnGoogleDataStore(final ModelMap model) {
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

			model.addAttribute("dataStore", dataStore);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "data";
	}
	
	@GetMapping("/microsoft")
	public String returnMicrosoftToken(final ModelMap model) {
		Map<Integer, Object> dataStore = new HashMap<Integer, Object>();
		
		Integer index = 0;
		for (MicrosoftAccount account : microsoftAccountRepository.findAll()) {
			Map<String, Object> userData = new HashMap<String, Object>();

			userData.put("tenantId", account.getTenantId());
			userData.put("tokenResponse", account.getTokenResponse());
			userData.put("name", account.getName());

			dataStore.put(index, userData);
			index++;
		}
		
		model.addAttribute("dataStore", dataStore);

		return "data_microsoft";
	}
}
