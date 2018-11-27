package fr.ynov.dap.services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.interfaces.AppUserRepository;

@Service
public class ContactService extends GoogleService {

	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the contacts.
	 *
	 * @param user the user
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	private ListConnectionsResponse getContactByAccount(GoogleAccount account)
			throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, super.getJSON_FACTORY(),
				super.getCredentials(HTTP_TRANSPORT, account.getName()))
						.setApplicationName(getConfig().getApplicationName()).build();

		try {
			return peopleService.people().connections().list("people/me").setPersonFields("names").execute();
		} catch (IOException e) {
			LOG.error("error for getting google contacts", e);
		}

		return null;
	}

	public Integer getContacts(String user) {
		Integer nbUnreadEmails = 0;

		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
			LOG.error("unknow user");
			return nbUnreadEmails;
		}

		LOG.info("get microsoft emails for " + user);
		for (GoogleAccount g : appU.getGoogleAccounts()) {
			try {
				if (getContactByAccount(g).getTotalItems() != null) {
					nbUnreadEmails += getContactByAccount(g).getTotalItems();
				}
			} catch (GeneralSecurityException | IOException e) {
				LOG.error("error for getting contacts : "+ g.getName(), e);
			}
		}

		return nbUnreadEmails;
	}
}
