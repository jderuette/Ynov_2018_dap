package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.services.google.GmailService;

/**
 * The Class Welcome.
 */
@Controller
public class Welcome {
	@Autowired
	private GmailService gmailService;

	protected Logger LOG = LogManager.getLogger(Welcome.class);

	/**
	 * Welcome.
	 *
	 * @param model   the model
	 * @param userKey the user key
	 * @return template ThymeLeaf welcome Or Error
	 */
	//TODO mot by Djer |Spring| Le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	@RequestMapping("/welcome")
	public String welcome(ModelMap model, @RequestParam(value = "userKey", required = true) String userKey) {
		try {
			Integer nbunreadEmails = gmailService.nbrEmailUnread(userKey);
			model.addAttribute("nbEmails", nbunreadEmails);

			//TODO mot by Djer |POO| "i" ?? "googleAccounts" serait mieux ?
			DataStore<StoredCredential> i;
			i = gmailService.getFlow().getCredentialDataStore();
			HashMap<String, String> hashMap = new HashMap<>();

			for (String userEmail : i.keySet()) {
				hashMap.put(userEmail, i.get(userEmail).getAccessToken());
			}
			model.addAttribute("credentials", hashMap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
		    //TODO mot by Djer |Log4| Contextualise tes messages (" for userkey : " + userKey)
			LOG.error("Error on Welcome", e);
			//TODO mot by Djer |POO| Attention, "e" n'est pas "errorMessage", cela peut être gènant dans ta Vue
			model.addAttribute("errorMessage", e);
			return "error";

		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
		  //TODO mot by Djer |Log4| Contextualise tes messages (" for userkey : " + userKey)
			LOG.error("Error on Welcome", e);
			//TODO mot by Djer |POO| Attention, "e" n'est pas "errorMessage", cela peut être gènant dans ta Vue
			model.addAttribute("errorMessage", e);
			return "error";
		}
		return "welcome";
	}
}
