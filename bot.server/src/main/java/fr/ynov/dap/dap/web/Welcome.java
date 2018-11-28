package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.google.GoogleMailService;
import fr.ynov.dap.dap.repository.AppUserRepository;


/**
 * The Class Welcome.
 */
@Controller
public class Welcome {

	/** The gmail service. */
	@Autowired
	private GoogleMailService gmailService;
	
	/** The app user repo. */
	@Autowired
	private AppUserRepository appUserRepo;

	/**
	 * Welcome.
	 *
	 * @param model the model
	 * @param userId the user id
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/welcome/{userId}")
	public String welcome(ModelMap model, @PathVariable final String userId)
			throws IOException, GeneralSecurityException {
		model.addAttribute("nbEmails", gmailService.getNbMailInbox(userId));
		return "welcome";
	}

	/**
	 * Gets the data store.
	 *
	 * @param model the model
	 * @return the data store
	 */
	@RequestMapping("/admin")
	public String getDataStore(final ModelMap model) {
		try {
			DataStore<StoredCredential> credentials = gmailService.getFlow().getCredentialDataStore();
			List<Object> usersCrendentials = new ArrayList<>();
			List<AppUser> appUsers = (List<AppUser>) appUserRepo.findAll();
			for (String key : credentials.keySet()) {
				Map<String, Object> userData = new HashMap<String, Object>();
				StoredCredential values = credentials.get(key);
				userData.put("accessToken", values.getAccessToken());
				userData.put("refreshToken", values.getRefreshToken());
				userData.put("expirationTimeMilliseconds", values.getExpirationTimeMilliseconds());
				userData.put("key", key);
				usersCrendentials.add(userData);
			}
			for (AppUser appUser : appUsers) {
				for(OutlookAccount account : appUser.getOutlookAccounts()) {
					Map<String, Object> userData = new HashMap<String, Object>();
					userData.put("accessToken", account.getToken()
							.getAccessToken());
					userData.put("refreshToken", account.getToken()
							.getRefreshToken());
					userData.put("expirationTimeMilliseconds", account.getToken()
							.getExpiresIn());
					userData.put("key", account.getName());
					usersCrendentials.add(userData);
				}
			}
			model.addAttribute("dataStore", usersCrendentials);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "data";
	}

}
