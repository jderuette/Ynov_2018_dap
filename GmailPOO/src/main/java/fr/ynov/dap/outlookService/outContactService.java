/**
 * 
 */
package fr.ynov.dap.outlookService;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.outlookService.*;


/**
 * @author acer
 *
 */
@Service
public class outContactService {

	public int nbContact(OutlookService outlookService) {

		// Sort by given name in ascending order (A-Z)
				String sort = "givenName ASC";
				// Only return the properties we care about
				String properties = "givenName,surname,companyName,emailAddresses";
				// Return at most 10 contacts
				Integer maxResults = 1000000;
				PagedResult<Contact> contacts = null ;
				try {
					 contacts = outlookService.getContacts(
							sort, properties, maxResults)
							.execute().body();
				
				} catch (IOException e) {
					
					
				}
	return contacts.getValue().length;
	}
}
