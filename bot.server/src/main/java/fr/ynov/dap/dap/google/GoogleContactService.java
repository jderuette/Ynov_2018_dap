package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repository.AppUserRepository;

/**
 * The Class ContactService.
 */
@Service
public class GoogleContactService extends GoogleService {
	
	/** The app user repo. */
	@Autowired
    private AppUserRepository appUserRepo;
	
	/** The log. */
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	/**
	 * Instantiates a new contact service.
	 */
	public GoogleContactService() {
		super();
	}
	
	/**
	 * Gets the service.
	 *
	 * @param accountName the user id
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private PeopleService getService(String accountName) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JACKSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, accountName)).setApplicationName(configuration.getApplicationName()).build();
		return service;
	}
	
	/**
	 * Gets the contacts.
	 *
	 * @param accountName the user id
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private int getNbContacts(String accountName) throws GeneralSecurityException, IOException {
		PeopleService service = getService(accountName);     
		ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses")
                .execute();

		return response.getTotalPeople();
	}
	
	/**
	 * Gets the nb contacts for all accounts.
	 *
	 * @param userKey the user key
	 * @return the nb contacts for all accounts
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public int getNbContactsForAllAccounts(final String userKey) throws IOException, GeneralSecurityException {
		AppUser user = appUserRepo.findByName(userKey);
		int nbContacts = 0;
		
		if(user != null) {
			for (GoogleAccount currentData : user.getGoogleAccounts()) {
				nbContacts += getNbContacts(currentData.getName());
	        }
			return nbContacts;
		}
		
		LOG.warn("No user found !");
		
		return 0;
	}
}
