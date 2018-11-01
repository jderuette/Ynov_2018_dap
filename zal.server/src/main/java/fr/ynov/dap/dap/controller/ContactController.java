package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.service.ContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("/contacts")
public class ContactController {
    
    /** The contact service. */
    @Autowired
    private ContactService contactService;

  
    /**
     * Gets the number of contacts.
     *
     * @param userId the user id
     * @return the number of contacts
     * @throws Exception the exception
     */
    @RequestMapping(value = "/{userId}")
    public ContactModel getNumberOfContacts(@PathVariable final String userId) throws Exception {
        return contactService.getPeople(userId);
    }
}
