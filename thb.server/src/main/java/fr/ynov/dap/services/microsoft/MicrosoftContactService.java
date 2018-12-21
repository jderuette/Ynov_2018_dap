package fr.ynov.dap.services.microsoft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.Contact;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.data.interfaces.OutlookServiceInterface;

/**
 * The Class MicrosoftContactService.
 */
@Service
public class MicrosoftContactService extends MicrosoftService {

	/** The app user repo. */
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the contacts.
	 *
	 * @param user the user
	 * @return the contacts
	 */
	public Integer getContacts(String user) {
		Integer nbUnreadEmails = 0;

		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
		  //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return nbUnreadEmails;
		}

		LOG.info("get microsoft emails for " + user);
		for (MicrosoftAccount m : appU.getMicrosoftAccounts()) {
			nbUnreadEmails += getContactByAccount(m);
		}

		return nbUnreadEmails;
	}

	/**
	 * Gets the contact by account.
	 *
	 * @param account the account
	 * @return the contact by account
	 */
	private Integer getContactByAccount(MicrosoftAccount account) {

		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());

		OutlookServiceInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		String sort = "GivenName ASC";
		String properties = "GivenName,Surname,CompanyName,EmailAddresses";
		//TODO thb by Djer |API Microsoft| Et si j'ai plsu de 1000 contacts ? Tu pourrais utiliser la pagination (avec des pages plus petites) pour être sure d'avoir tous les contacts. Ou tuiliser le parametre "?count=true" dnas l'URL du service pour avoir le total
		Integer maxResults = 1000;

		try {
			PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();

			return contacts.getValue().length;
		} catch (IOException e) {
		  //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error(e);
		}

		return 0;
	}
}
