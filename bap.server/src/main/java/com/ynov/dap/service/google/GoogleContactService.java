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

@Service
public class GoogleContactService extends GoogleService {
	@Autowired
	private AppUserRepository appUserRepository;
	
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

            return nbPeople;
        } else {
            getLogger().error("No contacts found for user : " + account.getName());
        }
        return 0;
    }
    
    public ContactModel getNbContacts(final String userKey) throws IOException, GeneralSecurityException {
    	
		AppUser appUser = appUserRepository.findByName(userKey);
		
		Integer nbContacts = 0;
		for (GoogleAccount account : appUser.getGoogleAccounts()) {
			nbContacts += getNbContacts(account);
		}

		return new ContactModel(nbContacts);
    }

    @Override
    public String getClassName() {
        return GoogleContactService.class.getName();
    }
}
