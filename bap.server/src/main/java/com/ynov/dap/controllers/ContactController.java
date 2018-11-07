package com.ynov.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.google.ContactService;
import com.ynov.dap.models.ContactModel;

/**
 * The Class ContactController.
 */
@RestController
public class ContactController extends BaseController {
    /** The contact service. */
    @Autowired
    private ContactService contactService;

    /**
     * Gets the nb contacts.
     *
     * @param gUser the g user
     * @return the nb contacts
     */
    @RequestMapping(value = "/contact/{gUser}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ContactModel> getNbContacts(@PathVariable final String gUser) {
        if (gUser == null || gUser.length() <= 0) {
            return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
        }

        try {
            return new ResponseEntity<ContactModel>(contactService.getNbContacts(gUser), HttpStatus.ACCEPTED);
        } catch (IOException e) {
            getLogger().error(e.getMessage());
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
        return new ResponseEntity<ContactModel>(new ContactModel(0), HttpStatus.BAD_REQUEST);
    }

    /* (non-Javadoc)
     * @see com.ynov.dap.controllers.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return ContactController.class.getName();
    }
}
