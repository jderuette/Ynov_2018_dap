package fr.ynov.dap.dapM2.Services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;


/**
 * The Class ContactService.
 */
public class ContactService extends GoogleService {
	
	/**
	 * Instantiates a new contact service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ContactService() throws IOException, GeneralSecurityException {
		super();
	}
	
	
	
	/**
	 * Gets the contacts.
	 *
	 * @param user the user
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public ListConnectionsResponse getContacts(String user) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(HTTP_TRANSPORT, user))
    			.setApplicationName(cfg.getAPPLICATION_NAME())
    			.build();
        
    	return peopleService.people().connections().list("people/me").setPersonFields("names").execute();
	}
}
