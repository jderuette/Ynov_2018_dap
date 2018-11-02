package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleService.People;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleService {

	/**
	 * Gets the service.
	 *
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public PeopleService getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	    PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials("user"))
	            .setApplicationName(Config.APPLICATION_NAME)
	            .build();
		return service;
	}
	
	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Integer getContacts() throws GeneralSecurityException, IOException{
    // Request 10 connections.
		PeopleService service = this.getService();
    	ListConnectionsResponse response = service.people().connections()
            .list("people/me")
            .setPageSize(10)
            .setPersonFields("names,emailAddresses")
            .execute();
    	//TODO dur by Djer Sera toujours MAX 10, comme la pagination est a 10 résultats par page, et que tu ne controles PAS les pages suivantes
    	return response.getTotalPeople();
	}
}
