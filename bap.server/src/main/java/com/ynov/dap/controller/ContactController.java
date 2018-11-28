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
import com.ynov.dap.service.google.GoogleContactService;
import com.ynov.dap.service.microsoft.MicrosoftContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("contact/nb")
public class ContactController extends BaseController {

    /** The google contact service. */
    @Autowired
    private GoogleContactService googleContactService;

    /** The microsoft contact service. */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * Gets the nb contacts.
     *
     * @param appUser the app user
     * @return the nb contacts
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping(value = "/google/{appUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactModel> getGoogleNbContacts(@PathVariable final String appUser)
            throws IOException, GeneralSecurityException {
        if (appUser == null || appUser.length() <= 0) {
            return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ContactModel>(googleContactService.getNbContacts(appUser), HttpStatus.ACCEPTED);
    }

    /**
     * Gets the microsoft nb contacts.
     *
     * @param appUser the app user
     * @return the microsoft nb contacts
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @RequestMapping(value = "/microsoft/{appUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactModel> getMicrosoftNbContacts(@PathVariable final String appUser) throws IOException {

        return new ResponseEntity<ContactModel>(microsoftContactService.getNbContacts(appUser), HttpStatus.ACCEPTED);
    }

    /**
     * Gets the nb contacts.
     *
     * @param appUser the app user
     * @return the nb contacts
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @GetMapping(value = "/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactModel> getNbContacts(@PathVariable final String appUser)
            throws IOException, GeneralSecurityException {
        ContactModel googleContact = googleContactService.getNbContacts(appUser);
        ContactModel microsoftContact = microsoftContactService.getNbContacts(appUser);

        return new ResponseEntity<ContactModel>(
                new ContactModel(googleContact.getNbContacts() + microsoftContact.getNbContacts()),
                HttpStatus.ACCEPTED);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.ynov.dap.controller.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return ContactController.class.getName();
    }

}
