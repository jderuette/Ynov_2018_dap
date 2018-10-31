package fr.ynov.dap.service;


import java.io.IOException;

import java.security.GeneralSecurityException;


import org.springframework.stereotype.Service;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.client.http.javanet.NetHttpTransport;
/**
 * This class extends GoogleService and provides people service features
 * @author Dom
 *
 */
@Service
public class PeopleGService extends GoogleService{
/**
 * Return the service of people according userId param
 * @param userId
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
	  public PeopleService getServices(String userId) throws IOException, GeneralSecurityException {
	    NetHttpTransport httpTransport = new NetHttpTransport();

	    PeopleService services = new PeopleService.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
				.setApplicationName(config.getApplicationName()).build();
	   
	    return services;
	  }
	  /**
	   * Return the number of contact according the userId param in the format Int
	   * @param userId
	   * @return
	   * @throws IOException
	   * @throws GeneralSecurityException
	   */
	  public int nbContact(String userId) throws IOException, GeneralSecurityException {
		  return this.getServices(userId).people().connections()
		          .list("people/me")
		          .setPersonFields("names,emailAddresses")
		          .execute().getTotalPeople();
		  
	  }
}
