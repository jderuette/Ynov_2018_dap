package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.ContactService;

/**
 *
 * @author David_tepoche
 *
 */
@RestController
@RequestMapping("/contact")
public class ContactController extends BaseController {

    /**
     * link with contactService.
     */
    @Autowired
    private ContactService contactService;

    /**
     * get the number of contact from userId.
     *
     * @param userId user token
     * @return number of contact
     * @throws GeneralSecurityException throw if the contactService fail
     * @throws IOException              throw if the contactService fail
     */
    @GetMapping("/nbrContact/{userId}")
    public @ResponseBody Integer nbrOfContact(@PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {
        return contactService.getNbrContact(userId);
    }

    /**
     * return the name of the current class.
     */
    @Override
    public String getClassName() {
        return ContactController.class.getName();
    }

}
