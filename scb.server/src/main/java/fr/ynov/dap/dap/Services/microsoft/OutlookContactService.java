package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.models.Contact;
import fr.ynov.dap.dap.models.PagedResult;

@Service
public class OutlookContactService {

	@Autowired
	AppUserRepository repository;
	
	public Integer getContactNumber(String userid) {
		
		AppUser appUser = repository.findByName(userid);
		List<OutlookAccount> otAccounts = appUser.getOutlookAccounts();
		
		Integer contactNumber = 0;
		for(int i=0; i < otAccounts.size(); i++) {
	        OutlookService outlookService = OutlookServiceFactory.getOutlookService(otAccounts.get(i).getIdToken().getAccessToken());

		    String sort = "GivenName ASC";
		    // Only return the properties we care about
		    String properties = "GivenName,Surname,CompanyName,EmailAddresses";
	        ModelAndView model = new ModelAndView("contacts");
		    try {
		      PagedResult<Contact> contacts = outlookService.getContacts(
		          sort, properties)
		          .execute().body();
		        contactNumber += contacts.getValue().length;
		    }catch(IOException e) {
				return null;
		    }
		}
		return contactNumber;

	}

}
