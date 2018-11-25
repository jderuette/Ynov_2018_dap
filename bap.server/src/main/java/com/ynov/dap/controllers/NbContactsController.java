package com.ynov.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.data.AppUser;
import com.ynov.dap.google.business.ContactService;
import com.ynov.dap.google.models.ContactModel;
import com.ynov.dap.microsoft.business.AuthHelper;
import com.ynov.dap.microsoft.business.OutlookService;
import com.ynov.dap.microsoft.business.OutlookServiceBuilder;
import com.ynov.dap.microsoft.data.MicrosoftAccount;
import com.ynov.dap.microsoft.models.Contact;
import com.ynov.dap.microsoft.models.PagedResult;
import com.ynov.dap.microsoft.models.TokenResponse;
import com.ynov.dap.repositories.AppUserRepository;

@RestController
@RequestMapping("contact/nb")
public class NbContactsController {

	@Autowired
    private ContactService contactService;
	
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Gets the nb contacts.
     *
     * @param gUser the g user
     * @return the nb contacts
     */
    @RequestMapping(value = "/google/{appUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactModel> getNbContacts(@PathVariable final String appUser) {
        if (appUser == null || appUser.length() <= 0) {
            return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<ContactModel>(contactService.getNbContacts(appUser), HttpStatus.ACCEPTED);
        } catch (IOException e) {
            //getLogger().error(e.getMessage());
        } catch (Exception e) {
            //getLogger().error(e.getMessage());
        }
        return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
    }
    
    @RequestMapping(value = "/microsoft/{appUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String contacts(@PathVariable final String appUser, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
       
    	/*
    	HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
        if (tokens == null) {
          // No tokens in session, user needs to sign in
          redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
          return "redirect:/index";
        }

        String tenantId = (String)session.getAttribute("userTenantId");

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        String email = (String)session.getAttribute("userEmail");

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        // Return at most 10 contacts
        Integer maxResults = 10;

        try {
          PagedResult<Contact> contacts = outlookService.getContacts(
              sort, properties, maxResults)
              .execute().body();
          
          System.out.println("contacts");
          System.out.println(contacts.getValue());
          
          model.addAttribute("contacts", contacts.getValue());
        } catch (IOException e) {
          redirectAttributes.addFlashAttribute("error", e.getMessage());
          return "redirect:/index";
        }
        */
    	
	    AppUser user = appUserRepository.findByName(appUser);

	    for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

	        String email = account.getEmail();
	        String tenantId = account.getTenantId();
	        TokenResponse tokens = account.getTokenResponse();

	        tokens = AuthHelper.ensureTokens(tokens, tenantId);
	        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

	        // Sort by given name in ascending order (A-Z)
	        String sort = "GivenName ASC";
	        // Only return the properties we care about
	        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
	        // Return at most 10 contacts
	        Integer maxResults = 1000;

	        try {
	          PagedResult<Contact> contacts = outlookService.getContacts(
	              sort, properties, maxResults)
	              .execute().body();
	          
	          System.out.println("NB contacts");
	          System.out.println(contacts.getValue().length);
	          
	          model.addAttribute("contacts", contacts.getValue());
	        } catch (IOException e) {
	          redirectAttributes.addFlashAttribute("error", e.getMessage());
	          return "redirect:/index";
	        }
	    }
	    

        return "contacts";
      }

    /* (non-Javadoc)
     * @see com.ynov.dap.controllers.BaseController#getClassName()
     */
    /*
    @Override
    public String getClassName() {
        return ContactController.class.getName();
    }
    */
	
}
