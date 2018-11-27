package com.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ynov.dap.model.ContactModel;
import com.ynov.dap.model.MailModel;
import com.ynov.dap.service.google.GoogleContactService;
import com.ynov.dap.service.microsoft.MicrosoftContactService;

@RestController
@RequestMapping("contact/nb")
public class ContactController extends BaseController {

	@Autowired
	private GoogleContactService googleContactService;

	@Autowired
	private MicrosoftContactService microsoftContactService;

	/**
	 * Gets the nb contacts.
	 *
	 * @param gUser the g user
	 * @return the nb contacts
	 */
	@RequestMapping(value = "/google/{appUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactModel> getGoogleNbContacts(@PathVariable final String appUser) {
		if (appUser == null || appUser.length() <= 0) {
			return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
		}

		try {
			return new ResponseEntity<ContactModel>(googleContactService.getNbContacts(appUser), HttpStatus.ACCEPTED);
		} catch (IOException e) {
			getLogger().error(e.getMessage());
		} catch (Exception e) {
			getLogger().error(e.getMessage());
		}
		return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/microsoft/{appUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactModel> getMicrosoftNbContacts(@PathVariable final String appUser) {

		return new ResponseEntity<ContactModel>(microsoftContactService.getNbContacts(appUser), HttpStatus.ACCEPTED);
	}

	@GetMapping(value = "/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactModel> getNbContacts(@PathVariable final String appUser) throws IOException, GeneralSecurityException {
		ContactModel googleContact = googleContactService.getNbContacts(appUser);
		ContactModel microsoftContact = microsoftContactService.getNbContacts(appUser);
		
		return new ResponseEntity<ContactModel>(new ContactModel(googleContact.getNbContacts() + microsoftContact.getNbContacts()),
				HttpStatus.ACCEPTED);
	}

	@Override
	public String getClassName() {
		return ContactController.class.getName();
	}

}
