package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

@Service
public class ContactService extends GoogleService {


	private final static Logger logger = LogManager.getLogger();



	/**
	 * 
	 * @param user
	 * @return people service with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	
	public PeopleService GetPeopleservice (String userKey) throws IOException, GeneralSecurityException{
		HTTP_TRANSPORT= GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userKey))
		.setApplicationName(configuration.getApplicationName())
		.build();
		return service;
	}
	
	/**
	 * 
	 * @param user
	 * @return Get contacts method with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public int GetContact(String userKey) throws IOException, GeneralSecurityException {
		int i=0;
		try{
			PeopleService service = GetPeopleservice(userKey); 
			//System.out.println(service.people().connections().list("").size());
			// Request 10 connections.
			ListConnectionsResponse response = service.people().connections()
					.list("people/me")
					.setPageSize(1000)
					.setPersonFields("names,emailAddresses")
					.execute();
			
			// Print display name of connections if available.
			List<Person> connections = response.getConnections();
			if (connections != null && connections.size() > 0) {
				for (Person person : connections) {
					List<Name> names = person.getNames();
					if (names != null && names.size() > 0) {
						
						i++;
					} else {
						logger.info("No names available for connection.");
					}
				}
			} else {
				logger.info("No connections found.");
			}
			
		} catch (IOException e) {
			logger.warn("Un problème vient d'être détecté avec comme détails : "+e.getMessage());
		}
		return i;
	}
	

}
