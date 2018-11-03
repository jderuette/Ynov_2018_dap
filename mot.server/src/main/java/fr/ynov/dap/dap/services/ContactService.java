package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;


/**
 * The Class ContactService.
 */
//TODO mot by Djer Un @Service et de l'IOC serait bienvenu !
public class ContactService extends GoogleService {

	/**
	 * Instantiates a new contact service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ContactService() throws IOException, GeneralSecurityException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	/**
	 * Gets the nb contact.
	 *
	 * @param user the user
	 * @return the nb contact
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public int getNbContact(String user) throws IOException, GeneralSecurityException {
	    //TODO mot by Djer Pourquoi EMAIL en majuscule ?
		String userEMAIL = user == null ? getDefaultUser() : user;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(userEMAIL))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();

       
 
        //TODO mot by Djer ce commentaire est devenu FAUX !
        // Request 10 connections.
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                //TODO mot by Djer Pourquoi limiter à 1900 ? Pourquoi la même approximation que Sylvain ?
                .setPageSize(1900)
                .setPersonFields("names,emailAddresses")
                .execute();
 
        // Print display name of connections if available.
        //List<Person> connections = response.getConnections();
        
        return response.getTotalItems();
    }
	
	

}
