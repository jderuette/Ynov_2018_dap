package fr.ynov.dap.dap.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The Class ContactService.
 */
public class ContactService extends GoogleServices {

	/**
	 * Instantiates a new contact service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ContactService() throws IOException, GeneralSecurityException {
		super();
	}

	/** The instance. */
	private static ContactService INSTANCE = null;

	/**
	 * Gets the single instance of ContactService.
	 *
	 * @return single instance of ContactService
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO brs by Djer Inutile, Spring le fait pour toi sur un @RestController (ou un @Service).
    // Deplus ton constructeur est public !
    // Deplus deplus ton unique méthode est static !!!
	public static ContactService getInstance() throws IOException, GeneralSecurityException {
		if (INSTANCE == null) {
			INSTANCE = new ContactService();

		}
		return INSTANCE;
	}
	
	/**
	 * getNbContact.
	 *
	 * @param user  the user to store Data
	 * @return int the number of contact
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static int getNbContact(String user) throws IOException, GeneralSecurityException {
		if (user == null) {
			user = "sylvain69740@gmail.com";
		}
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, configuration.getJSON_FACTORY(),
				getCredentials(HTTP_TRANSPORT, configuration.getCREDENTIALS_FILE_PATH(), user))
						.setApplicationName(configuration.getAPPLICATION_NAME()).build();

		

		//TODO brs by Djer commentaire de venu faux ! 
		// Request 10 connections.
		ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(1900)
				.setPersonFields("names,emailAddresses").execute();

		//TODO brs by Djer Poruquoi limiter à 1900 contacts max ?
		return response.getTotalItems();
	}
}