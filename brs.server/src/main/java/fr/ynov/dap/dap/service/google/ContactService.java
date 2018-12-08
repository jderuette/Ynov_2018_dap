package fr.ynov.dap.dap.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class ContactService.
 */
@Service
public class ContactService extends GoogleServices {

	@Autowired 
	public AppUserRepository repo;
	
	/**
	 * Instantiates a new contact service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public ContactService() throws IOException, GeneralSecurityException {
		super();
	}

	
	/**
	 * getNbContact.
	 *
	 * @param user  the user to store Data
	 * @return int the number of contact
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public int getNbContactByAccount(String user) throws IOException, GeneralSecurityException {
		
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, getJSON_FACTORY(),
				getCredentials( user))
						.setApplicationName(getConfiguration().getApplicationName()).build();

		ListConnectionsResponse response = service.people().connections().list("people/me")
				.setPersonFields("names,emailAddresses").execute();

		return response.getTotalItems();
	}
	
public Integer getNbTotalContact(String userKey) {
		
		Integer nbTotalContact = 0;
		AppUser user = repo.findByUserkey(userKey);
		if(user == null) {
		  //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.info("no user");
			return 0;
		}
		for (GoogleAccount googleAccount : user.getGoogleAccounts()) {
			try {
				nbTotalContact += getNbContactByAccount(googleAccount.getAccountName());
				//TODO brs by Djer |Log4J| Contextualise tes messages
				LOG.info("Google account : ",googleAccount);
			} catch (IOException e) {
				LOG.error("Google account : ",googleAccount);
				LOG.error(e);
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				LOG.error("Google account : ",googleAccount);
				LOG.error(e);
				e.printStackTrace();
			}
        }
		if(nbTotalContact == 0) {
		  //TODO brs by Djer |Log4J| Contextualise tes messages (au lieu de "this userKey" utilise " for userkey : " + userKey)
			LOG.info("No contact for this userKey");
		}
		return nbTotalContact;
	}
	
	
}