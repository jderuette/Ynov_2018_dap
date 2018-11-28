package fr.ynov.dap.controller.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.service.Google.CredentialsService;

@Controller
@RequestMapping("/google")
public class GoogleCredentialsController  {

	
	@Autowired
	private CredentialsService credentialsService;
	
	@RequestMapping("/credentials")
	public String getCredentials(ModelMap model) throws GeneralSecurityException, IOException {
		
		DataStore<StoredCredential> credentials = credentialsService.getCredentials();
		HashMap<String, String> credentialsMap = new HashMap<>();
		
		for (String k : credentials.keySet()) {
			credentialsMap.put(k, credentials.get(k).getAccessToken());
		}
		model.addAttribute("credentialsMap",credentialsMap);
		
		return "Credentials";
	}
	
}
