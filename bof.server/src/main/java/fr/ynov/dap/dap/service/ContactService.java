package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.model.MasterModel;

/**
 * 
 * @author Florent
 * Service class for all the contact request
 */
@Service
public class ContactService extends GoogleService{

	/**
	 * 
	 * @param userID id of user who try to access to the service
	 * @return PeopleService Instance of PeopleService
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * 
	 */
	private PeopleService getService(String userID) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(userID))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();

	     return service;
	}
	
	
	/**
	 * 
	 * @param userID id of user who try to access to the service
	 * @return ContactModel The request response formated in JSON
	 * @throws Exception
	 */
	public MasterModel getNbContacts(String userID) throws GeneralSecurityException, IOException {
		PeopleService service = getService(userID);
		ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses")
                .execute();
		List<Person> connections = response.getConnections();
		int nbOfContacts = 0;
		if(connections != null) {
			 nbOfContacts = connections.size();
		}
		return new ContactModel(nbOfContacts);
	}
	
}
