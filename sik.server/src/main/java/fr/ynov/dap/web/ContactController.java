package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dto.out.NumberContactOutDto;
import fr.ynov.dap.google.ContactService;

/**
 * Controller to manage every call to Google Contact API.
 * @author Kévin Sibué
 *
 */
@RestController
@RequestMapping("/contact")
public class ContactController extends BaseController {

    /**
     * Instance of ContactService service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private ContactService contactService;

    /**
     * Return calendar service instance.
     * @return CalendarService instance
     */
    private ContactService getContactService() {
        return contactService;
    }

    /**
     * Endpoint to get the user's number of contact.
     * @param userId User's Id
     * @return Number of contact for user linked by userId. JSON Formatted.
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    @RequestMapping("/nbContacts/{userId}")
    public final NumberContactOutDto getNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException {

        Integer nbContacts = getContactService().getNumberOfContacts(userId);

        return new NumberContactOutDto(nbContacts);

    }

    @Override
    protected final String getClassName() {
        return ContactController.class.getName();
    }

}
