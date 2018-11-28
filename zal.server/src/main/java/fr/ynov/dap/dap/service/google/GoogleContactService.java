package fr.ynov.dap.dap.service.google;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;

/**
 * The Class GoogleContactService.
 */
@Service
public class GoogleContactService extends GoogleService {
	/**
	 * get people google service with credentials.
	 * 
	 * @param userId
	 *            *id of user*
	 * @return PeopleService
	 * @throws Exception
	 *             *Exception*
	 */
	public PeopleService getPeopleGoogleService(final String userId) throws Exception {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		PeopleService service = new PeopleService.Builder(httpTransport, jsonFactory,
				getCredentials(httpTransport, userId)).setApplicationName(getCfg().getApplicationName()).build();
		return service;
	}

	/**
	 * get contacts with id user.
	 *
	 * @param user
	 *            the user
	 * @return ContactModel
	 * @throws Exception
	 *             *Exception*
	 */
	public Integer getNbContactFromGoogle(AppUser user) throws Exception {
		Integer contacts = 0;
		if (user.getGoogleAccounts().size() == 0) {
			return 0;
		}
		for (GoogleAccount account : user.getGoogleAccounts()) {
			PeopleService service = getPeopleGoogleService(account.getName());
			ListConnectionsResponse response = service.people().connections().list("people/me")
					.setPersonFields("names,emailAddresses").execute();
			contacts += response.getTotalPeople();
		}

		return contacts;
	}
}
