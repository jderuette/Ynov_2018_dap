package fr.ynov.dap.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleService {

	@Autowired
	AppUserRepository appUserRepository;

	//TODO mot by Djer |POO| Si en MAJUSCULE devrait être static finale. Pourquoi "protected" ?)
	protected Logger LOG = LogManager.getLogger(ContactService.class);

	/**
	 * Instantiates a new contact service.
	 *
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ContactService() throws IOException, GeneralSecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the list connection.
	 *
	 * @param user the user
	 * @return the list connection
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ListConnectionsResponse getListConnection(String user) throws IOException, GeneralSecurityException {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, user)).setApplicationName(getCfg().getApplicationName()).build();

		ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(2000)
				.setPersonFields("names,emailAddresses").execute();

		return response;
	}

	/**
	 * Gets the nb contact.
	 *
	 * @param user the user
	 * @return the nb contact
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public int getNbContact(String user) throws IOException, GeneralSecurityException {
		Integer response = 0;

		AppUser appUser = appUserRepository.findByUserKey(user);

		if (appUser == null) {
			return response;
		}

		for (GoogleAccount g : appUser.getAccounts()) {
			try {
				if (getListConnection(g.getName()).getTotalItems() != null) {
					response += getListConnection(g.getName()).getTotalItems();
				}
			} catch (IOException | GeneralSecurityException e) {
			  //TODO mot by Djer |Log4J| Contextualise tes messages (" for userKey : " + user + " and accountName : " + g.getName())
				LOG.error("Error nombre de contact", e);
			}
		}

		return response;

	}
}
