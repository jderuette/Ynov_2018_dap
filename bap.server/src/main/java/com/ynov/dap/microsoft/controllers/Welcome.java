package com.ynov.dap.microsoft.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.ynov.dap.google.business.CredentialService;

@Controller
public class Welcome {

    @Autowired
    private CredentialService credentialService;

    /*
    @RequestMapping("/{gUser}")
    public String returnWelcome(@PathVariable final String gUser, final ModelMap model) {
    	try {
			model.addAttribute("nbEmails", mailService.getNbUnreadEmails(gUser).getUnRead());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "welcome";
	}
	*/

	@RequestMapping("/data")
	public String returnDataStore(final ModelMap model) {
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

}