package fr.ynov.dap.outlookService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javassist.NotFoundException;

@Service
public class ContactService {
	@Autowired
	private MailService outmailService;

	public Contact[] listContact(String accountName, Model model, final HttpServletRequest request)
			throws NotFoundException {
		OutlookService outlookService = outmailService.ConnexionOutlook(model, request, accountName);
		// Sort by given name in ascending order (A-Z)
		String sort = "givenName ASC";
		// Only return the properties we care about
		String properties = "givenName,surname,companyName,emailAddresses";
		// Return at most 10 contacts
		Integer maxResults = 1000;
		Contact[] listContact = null;
		try {
			PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();
			listContact = contacts.getValue();
		} catch (IOException e) {

		}

		return listContact;
	}

	public int nbContact(String accountName, Model model, final HttpServletRequest request) throws NotFoundException {
		return listContact(accountName, model, request).length;
	}

}
