package com.ynov.dap.service.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.model.ContactModel;
import com.ynov.dap.repository.AppUserRepository;

/**
 * The Class GoogleContactService.
 */
@Service
public class GoogleContactService extends GoogleService {

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

    /**
     * Gets the nb contacts.
     *
     * @param account the account
     * @return the nb contacts
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public Integer getNbContacts(final GoogleAccount account) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(httpTransport, JSON_FACTORY, getCredentials(account.getName()))
                .setApplicationName(getConfig().getApplicationName())
                .build();

        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(100)
                .setPersonFields("names")
                .execute();

        Integer nbPeople = response.getTotalPeople();
        if (nbPeople != null) {
            getLogger().info("Contacts found for user : " + account.getName());
            return nbPeople;
        } else {
            getLogger().error("No contacts found for user : " + account.getName());
        }
        return 0;
    }

    /**
     * Gets the nb contacts.
     *
     * @param userKey the user key
     * @return the nb contacts
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public ContactModel getNbContacts(final String userKey) throws IOException, GeneralSecurityException {
		Integer nbContacts = 0;

		AppUser appUser = appUserRepository.findByName(userKey);
		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return new ContactModel(nbContacts);
		}

		for (GoogleAccount account : appUser.getGoogleAccounts()) {
			nbContacts += getNbContacts(account);
		}

        getLogger().info("Total Contacts for userKey : " + userKey);

		return new ContactModel(nbContacts);
    }

    /* (non-Javadoc)
     * @see com.ynov.dap.service.BaseService#getClassName()
     */
    @Override
    public String getClassName() {
        return GoogleContactService.class.getName();
    }
}
