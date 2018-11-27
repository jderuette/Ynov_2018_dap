package com.ynov.dap.service.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.ContactModel;
import com.ynov.dap.model.microsoft.Contact;
import com.ynov.dap.model.microsoft.PagedResult;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;

@Service
public class MicrosoftContactService {

	@Autowired
	private AppUserRepository appUserRepository;

	public Integer getNbContacts(final MicrosoftAccount account) {
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		tokens = AuthHelper.ensureTokens(tokens, tenantId);
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		try {
			PagedResult<Contact> contacts = outlookService.getNbContacts().execute().body();

			System.out.println("NB contacts");
			System.out.println(contacts.getCount());
			return contacts.getCount();
		} catch (IOException e) {
			e.getStackTrace();
		}

		return 0;
	}

	public ContactModel getNbContacts(String user) {
		AppUser appUser = appUserRepository.findByName(user);

		Integer nbContacts = 0;
		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

			nbContacts += getNbContacts(account);
		}

		return new ContactModel(nbContacts);
	}

	public Contact[] getContacts(final MicrosoftAccount account) {
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String sort = "GivenName ASC";
		String properties = "GivenName,Surname,CompanyName,EmailAddresses";
		Integer maxResults = 10;

		try {
			PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();

			System.out.println("contacts");
			System.out.println(contacts.getValue());

			return contacts.getValue();
		} catch (IOException e) {
			e.getStackTrace();
		}
		return null;
	}

	public List<Contact[]> getContacts(String user) {
		AppUser appUser = appUserRepository.findByName(user);

		List<Contact[]> contacts = new ArrayList<Contact[]>();
		
		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
			contacts.add(getContacts(account));
		}
		
		return contacts;
	}

}
