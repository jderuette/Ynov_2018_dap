package fr.ynov.dap.dap.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.model.ContactModel;

@Service
public class ContactService extends GoogleService {
	/**
	 * get people google service with credentials.
	 * @param userId *id of user*
	 * @return PeopleService
	 * @throws Exception *Exception*
	 */
	public PeopleService getPeopleGoogleService(final String userId) throws Exception{
		 final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	     PeopleService service = new PeopleService.Builder(httpTransport, getCfg().getJsonFactory(),
	    		 getCredentials(httpTransport, getCfg().getCredentialsFilePath(), userId))
	             .setApplicationName(getCfg().getApplicationName())
	             .build();
	             return service;
	}
	/**
	 * get contacts with id user.
	 * @param userId *id of user*
	 * @return ContactModel
	 * @throws Exception *Exception*
	 */
	//TODO zal by Djer Nom de méthode pas très claire.
	public ContactModel getPeople(final String userId) throws Exception {
		PeopleService service = getPeopleGoogleService(userId);
		ListConnectionsResponse response = service.people().connections()
	             .list("people/me")
	             .setPersonFields("names,emailAddresses")
	             .execute();
		return new ContactModel(response.getTotalPeople());
	}
}
