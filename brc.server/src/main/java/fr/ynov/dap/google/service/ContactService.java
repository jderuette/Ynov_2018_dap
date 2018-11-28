package fr.ynov.dap.google.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.GoogleAccount;
import fr.ynov.dap.microsoft.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.models.Message;
import fr.ynov.dap.models.NbContactResponse;

/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleService{
	
	/** The logger. */
	final static Logger logger = LogManager.getLogger(ContactService.class);
	
	/**
	 * Result contact.
	 *
	 * @param userId the user id
	 * @return the contact response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Integer getNbContact(String userId) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, jsonFactory, getCredentials(HTTP_TRANSPORT, cfg.getCredentialsFilePath(), userId))
                .setApplicationName(cfg.getApplicationName())
                .build();

        // Request 10 connections.
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names")
                .execute();
        int nbContact = response.getTotalPeople();
        
        return nbContact;

    }
	
	/**
	 * Gets the nb contact from account.
	 *
	 * @param user the user
	 * @return the nb contact from account
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public NbContactResponse getNbContactFromAccount(final AppUser user) throws IOException, GeneralSecurityException {
		
		if (user.getGoogleAccounts().size() == 0) {
        	logger.info("no google account found for userkey : " + user.getUserKey());
            return null;
        }
		
		Integer totalContact = 0;
		
		for (GoogleAccount account : user.getGoogleAccounts()) {
            totalContact += this.getNbContact(account.getName());
        }
		
		return new NbContactResponse(totalContact);
	}
}
