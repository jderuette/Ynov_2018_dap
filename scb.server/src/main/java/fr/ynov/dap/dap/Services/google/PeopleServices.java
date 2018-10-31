package fr.ynov.dap.dap.Services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.Config;

@Service
public class PeopleServices extends GoogleService {
	
	/**
	 * 
	 * @param user
	 * @return the people services that provides info for contacts..etc
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	PeopleService getService(String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
        return service;
	}
	
	/**
	 * 
	 * @param user
	 * @return an int that represent the number of contact
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Integer contactNumber(String user) throws IOException, GeneralSecurityException {
		ListConnectionsResponse contacts = this.getService(user).people().connections().list("people/me").setPersonFields("names,emailAddresses").execute();		
		return contacts.getTotalPeople();
	}
}
