package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.dap.data.interfaces.OutlookInterface;
import fr.ynov.dap.dap.data.microsoft.Contact;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;

/**
 * The Class ContactMicrosoftService.
 */
@Service
public class ContactMicrosoftService extends MicrosoftService {

	@Autowired
	AppUserRepository appUserRepository;

	//TODO mot by Djer |POO| Si en MAJUSCULE devrait être static finale. Pourquoi "protected" ?)
	protected Logger LOG = LogManager.getLogger(ContactMicrosoftService.class);

	/**
	 * Gets the contacts.
	 *
	 * @param userKey the user key
	 * @return Contacts for all Microsoft Account
	 */
	public Integer getContacts(String userKey) {
		Integer nbContacts = 0;

		AppUser appUser = appUserRepository.findByUserKey(userKey);

		if (appUser == null) {
			return nbContacts;
		}

		for (MicrosoftAccount m : appUser.getMicrosoftAccount()) {
			nbContacts += getContactByAccount(m);
		}

		return nbContacts;
	}

	/**
	 * Gets the contact by account.
	 *
	 * @param account the account
	 * @return the nb contact by account
	 */
	private Integer getContactByAccount(MicrosoftAccount account) {

		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());

		OutlookInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), null);

		String sort = "GivenName ASC";
		String properties = "GivenName,Surname,CompanyName,EmailAddresses";
		//TODO mot by Djer |API Microsoft| Utiliser la pagination serait plus "juste"
		Integer maxResults = 2000;

		try {
			PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();

			return contacts.getValue().length;
		} catch (IOException e) {
		  //TODO mot by Djer |Log4J| Contextualise tes messages (" for microsofot accountname : " + account)
		    //TODO mot by Djer |Log4J| Evite d'ajouter le message de l'excpetion dans ton propre message. Met la cause ("e") en deuxième paramètre et Log4J s'occupera d'afficher le message et la pile de la cause.
			LOG.error("Error during getContactByAccount", e.getMessage());
		}

		return 0;
	}

}
