package fr.ynov.dap.dap.services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;

@Service
public class PeopleServices extends GoogleService {
	@Autowired
	AppUserRepository repository;
	
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
                .setApplicationName(config.getAppName())
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
		AppUser appUser = repository.findByName(user);
		List<GoogleAccount> gAccounts = appUser.getAccounts();
		Integer totalUnreadMessages = 0;
		for(int i =0; i < gAccounts.size(); i++) {
			ListConnectionsResponse contacts = this.getService(gAccounts.get(i).getName()).people().connections().list("people/me").setPersonFields("names,emailAddresses").execute();		
	        totalUnreadMessages +=  contacts.getTotalPeople();
		}
		return totalUnreadMessages;
	}
}
