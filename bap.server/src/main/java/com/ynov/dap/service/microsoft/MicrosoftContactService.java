package com.ynov.dap.service.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.ContactModel;
import com.ynov.dap.model.MailModel;
import com.ynov.dap.model.microsoft.Contact;
import com.ynov.dap.model.microsoft.PagedResult;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.service.BaseService;

@Service
public class MicrosoftContactService extends BaseService {

	@Autowired
	private AppUserRepository appUserRepository;

	public Integer getNbContacts(final MicrosoftAccount account) throws IOException {
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		tokens = AuthHelper.ensureTokens(tokens, tenantId);
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		PagedResult<Contact> contacts = outlookService.getNbContacts().execute().body();

		return contacts.getCount();

	}

	public ContactModel getNbContacts(String userKey) throws IOException {
		Integer nbContacts = 0;

		AppUser appUser = appUserRepository.findByName(userKey);
		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return new ContactModel(nbContacts);
		}
		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
			nbContacts += getNbContacts(account);
		}

		return new ContactModel(nbContacts);
	}

	public Contact[] getContacts(final MicrosoftAccount account) throws IOException {
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String sort = "GivenName ASC";
		String properties = "GivenName,Surname,CompanyName,EmailAddresses";
		Integer maxResults = 10;

		PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();

		return contacts.getValue();

	}

	public List<Contact[]> getContacts(String user) throws IOException {
		AppUser appUser = appUserRepository.findByName(user);

		List<Contact[]> contacts = new ArrayList<Contact[]>();

		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
			contacts.add(getContacts(account));
		}

		return contacts;
	}

	@Override
	protected String getClassName() {
		return MicrosoftContactService.class.getName();
	}

}
