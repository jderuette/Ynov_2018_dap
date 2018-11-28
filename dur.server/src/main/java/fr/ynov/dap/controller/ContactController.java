package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.google.ContactService;
import fr.ynov.dap.microsoft.OutlookService;

/**
 * Controller to manage every call to Google Contact API.
 * @author Robin DUDEK
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
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;

    @RequestMapping("/nbContacts/{userId}")
    public final String getNumberOfContacts(@PathVariable("userId") final String userId, final ModelMap model)
            throws GeneralSecurityException, IOException {

        AppUser user = GetUserById(userId);

        Integer googleNbContacts = contactService.getUserContacts(user);
        Integer microsoftNbContacts = outlookService.getNumberOfContacts(user);
        model.addAttribute("totalcontacts", googleNbContacts + microsoftNbContacts);
        return "contacts";
    }

    /**
     * Endpoint to get the user's number of contact.
     * @param userId User's Id
     * @return Number of contact for user linked by userId. JSON Formatted.
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws UserNotFoundException Thrown when the user is not found.
     * @throws NoGoogleAccountException Thrown when the user haven't any google account.
     */
    @RequestMapping("/google/nbContacts/{userId}")
    public final String getGoogleNumberOfContacts(@PathVariable("userId") final String userId, final ModelMap model)
            throws GeneralSecurityException, IOException {

        AppUser user = GetUserById(userId);

        Integer googleNbContacts = contactService.getUserContacts(user);

        model.addAttribute("totalcontacts", googleNbContacts);
        return "contacts";
    }

    @RequestMapping("/microsoft/nbContacts/{userId}")
    public String getMicrosoftNumberOfContacts(@PathVariable("userId") final String userId, final ModelMap model,
            final HttpServletRequest request)
            throws IOException {

        AppUser user = GetUserById(userId);

        Integer microsoftNbContacts = outlookService.getNumberOfContacts(user);

        model.addAttribute("totalcontacts", microsoftNbContacts);
        return "contacts";

    }

    @Override
    protected final String getClassName() {
        return ContactController.class.getName();
    }

}
