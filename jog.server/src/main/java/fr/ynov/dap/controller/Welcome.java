package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.GmailService;

@Controller
public class Welcome {

	@Autowired
	GmailService gmailService;

	@RequestMapping("/")
	public String Bienvenue(@RequestParam("userKey") String userKey, ModelMap mo)
			throws IOException, GeneralSecurityException {

		int nbunreadEmails = gmailService.getUnreadMessageCount(userKey, "me");
		mo.addAttribute("nbEmails", nbunreadEmails);
		return "welcome";

	}

	@RequestMapping("/admin")
	public String Admin(ModelMap po) throws IOException, GeneralSecurityException {

		DataStore<StoredCredential> credentials = gmailService.getDataStore();
		po.addAttribute("data", gmailService.getDataStore());
		Map<String, StoredCredential> laMap = new HashMap<>();
		// laMap.put("data", gmailService.getDataStore());

		for (String aaKey : credentials.keySet()) {
			laMap.put(aaKey, laMap.get(aaKey));
		}

		po.addAttribute("data", laMap);
		//TODO job by Djer |API Microsoft| Affichage des comtpes Microsoft ? 
		return "admin";

	}

}
