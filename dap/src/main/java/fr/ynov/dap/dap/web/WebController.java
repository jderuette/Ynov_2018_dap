package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//TODO duv by Djer |IDE| Configure tes "save Action" dans eclipse, Et les plugin de qualité de code au passage !!!! Ca t'évitera de laisser des Imports inutilisés (et pleisn d'autre "petites" erreurs)
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.google.service.UserInfoService;
import fr.ynov.dap.dap.microsoft.services.MicrosoftAccountService;
import fr.ynov.dap.dap.microsoft.services.MicrosoftMailService;
import fr.ynov.dap.dap.model.MicrosoftMail;
import fr.ynov.dap.dap.model.StoredCredentialsResponse;

/**
 *
 * @author David_tepoche
 *
 */
@Controller
public class WebController {

	/**
	 * link userInfoService.
	 */
	@Autowired
	private UserInfoService userInfoService;

	/**
	 * link the msMailService.
	 */
	@Autowired
	private MicrosoftMailService microsoftMailService;

	/**
	 * link the appUserRepository.
	 */
	//TODO duv by Djer |SOA| Pas top d'acceder directement au repository depuis un Controller. un DapUserService aurait été bienvenu.
	@Autowired
	private AppUserRepostory appUSerRepository;

	/**
	 * launch the admin page with correct data
	 * 
	 * @param userKey the user in database
	 * @param map     needed to pass param in the view for Thymeleaf
	 * @return the name of the page called
	 * @throws IOException              throw when fail to get the credential
	 * @throws GeneralSecurityException throw when fail to get the credential
	 */
	//TODO duv by Djer |Spring| Le modelMap est en général nomée "model", "map" est beaucoup trop générique...
	@GetMapping("/admin/{userKey}")
	public String getAdminPage(@PathVariable(value = "userKey") final String userKey, final ModelMap map)
			throws IOException, GeneralSecurityException {

		AppUser appUser = appUSerRepository.findByUserKey(userKey);
		if (appUser == null) {
			return "Error";
		}
		List<StoredCredentialsResponse> storedCredentialsResponses = new ArrayList<>();
		List<MicrosoftAccount> mAccounts = new ArrayList<>();
		//TODO duv by Djer |POO| A gagner des caractères dans les noms d'attributs, on se retrouve à ne plus respecter le camelCase ...
		mAccounts = appUser.getmAccounts();
		List<GoogleAccount> gAccounts = new ArrayList<>();
		gAccounts = appUser.getgAccounts();

		for (MicrosoftAccount microsoftAccount : mAccounts) {
			storedCredentialsResponses.add(new StoredCredentialsResponse(microsoftAccount));

		}
		//TODO duv by Djer |POO| Pourquoi la classe pleinenment qualifiée et pas un import ? 
		com.google.api.client.util.store.DataStore<StoredCredential> s = userInfoService.getCredentialDataStore();

		for (GoogleAccount googleAccount : gAccounts) {
			storedCredentialsResponses.add(new StoredCredentialsResponse(googleAccount,
					s.get(googleAccount.getAccountName()).getAccessToken()));
		}

		map.addAttribute("comptes", storedCredentialsResponses);
		map.addAttribute("userKey", userKey);
		map.addAttribute("nbAccount", storedCredentialsResponses.size());
		map.addAttribute("GLAccount", gAccounts.size());
		map.addAttribute("MSAccount", mAccounts.size());
		return "mainPage";
	}

	/**
	 * launch the mail's page from the user in bdd pass through the url
	 * @param userKey the user in bdd get from the url param
	 * @param map needed to pass param in the view for Thymeleaf
	 * @return the name of the page called
	 * @throws IOException throw when fail to get the credential
	 * @throws SecretFileAccesException throw if you can't get the info from the
	 *                                  properties
	 */
	@GetMapping("/admin/{userKey}/mails")
	public String getMailsPage(@PathVariable(value = "userKey") final String userKey, final ModelMap map)
			throws IOException, SecretFileAccesException {
		AppUser appUser = appUSerRepository.findByUserKey(userKey);
		if (appUser == null) {
			return "Error";
		}
		List<MicrosoftAccount> mAccounts = new ArrayList<>();
		mAccounts = appUser.getmAccounts();

		HashMap<String, MicrosoftMail[]> emails = new HashMap<>();
		for (MicrosoftAccount microsoftAccount : mAccounts) {

		    //TODO duv by Djer |Audit Code| Ho ?! Un Magic Number ! Mais que font PMD et CheckStyle ? 
			MicrosoftMail[] mails = microsoftMailService.getMail(microsoftAccount, 5);
			emails.put(microsoftAccount.getAccountName(), mails);

		}

		map.addAttribute("emails", emails);

		return "mailsPage";
	}
}
