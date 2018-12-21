package fr.ynov.dap.services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.interfaces.AppUserRepository;

/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleService {

	/** The app user repo. */
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the contacts.
	 *
	 * @param account the account
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	//TODO thb by Djer |Gestion Exception| Attention, tu "étouffe" la IOException, tu peux l'enlevée des "thorws" (ou ne plus l'étouffer et laisser l'appelant la gérer)
	private ListConnectionsResponse getContactByAccount(GoogleAccount account)
			throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, super.getJSON_FACTORY(),
				super.getCredentials(HTTP_TRANSPORT, account.getName()))
						.setApplicationName(getConfig().getApplicationName()).build();

		try {
		    //TODO thb by Djer |POO| Evite les multiples return dans une même méthode
			return peopleService.people().connections().list("people/me").setPersonFields("names").execute();
		} catch (IOException e) {
		    //TODO thb by Djer |Log4J| Contextualise tes messages (" for accountName : " + account.getName())
			LOG.error("error for getting google contacts", e);
		}

		return null;
	}

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
			//TODO thb by Djer |POO| Evite les multiples return dans une même méthode
			return nbUnreadEmails;
		}

		LOG.info("get microsoft emails for " + user);
		for (GoogleAccount g : appU.getGoogleAccounts()) {
			try {
				if (getContactByAccount(g).getTotalItems() != null) {
					nbUnreadEmails += getContactByAccount(g).getTotalItems();
				}
			} catch (GeneralSecurityException | IOException e) {
				LOG.error("error for getting contacts : " + g.getName(), e);
			}
		}

		return nbUnreadEmails;
	}
}
