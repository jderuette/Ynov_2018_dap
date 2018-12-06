package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;


/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleService {
	
	/**
	 * Instantiates a new contact service.
	 */
	public ContactService() {
		super();
	}
	
	/**
	 * Gets the service.
	 *
	 * @param userId the user id
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private PeopleService getService(String userId) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		//FIXME bot by Djer |POO| Ne compilait pas/plus, je corrige pour pouvoir vérifier l'éxécution de ton code
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JACKSON_FACTORY,
		      //FIXME bot by Djer |POO| Ne compilait pas/plus, je corrige pour pouvoir vérifier l'éxécution de ton code
				getCredentials(HTTP_TRANSPORT, userId)).setApplicationName(configuration.getApplicationName()).build();
		return service;
	}
	
	/**
	 * Gets the contacts.
	 *
	 * @param userId the user id
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//TODO bot by Djer |POO| Renvoie un nombre de contact, le nom de la méthode n'est pas très claire. Et la Javadoc n'éclaire pas !
	public int getContacts(String userId) throws GeneralSecurityException, IOException {
		PeopleService service = getService(userId);     
		ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names,emailAddresses")
                .execute();
		//TODO bot by Djer |API Google| (Ancien TO-DO non corrigé !) Toujour max 10 contacts, car paginé, et 10 résultats par page et que tu ne parcour pas les pages
		int countContact = response.getTotalPeople();
		return countContact;
	}
}
