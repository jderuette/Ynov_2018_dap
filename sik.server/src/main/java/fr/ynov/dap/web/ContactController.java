package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dto.out.NumberContactOutDto;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.ContactService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.model.AppUser;

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
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;

    @RequestMapping("/nbContacts/{userId}")
    public final NumberContactOutDto getNumberOfContacts(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserNotFoundException, NoGoogleAccountException,
            NoMicrosoftAccountException {

        AppUser user = GetUserById(userId);

        Integer googleNbContacts = contactService.getNumberOfContacts(user);

        Integer microsoftNbContacts = outlookService.getNumberOfContacts(user);

        return new NumberContactOutDto(googleNbContacts + microsoftNbContacts);

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
    public final NumberContactOutDto getGoogleNumberOfContacts(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserNotFoundException, NoGoogleAccountException {

        AppUser user = GetUserById(userId);

        Integer nbContacts = contactService.getNumberOfContacts(user);

        return new NumberContactOutDto(nbContacts);

    }

    @RequestMapping("/microsoft/nbContacts/{userId}")
    public NumberContactOutDto getMicrosoftNumberOfContacts(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = GetUserById(userId);

        Integer nbOfContacts = outlookService.getNumberOfContacts(user);

        return new NumberContactOutDto(nbOfContacts);

    }

    @Override
    protected final String getClassName() {
        return ContactController.class.getName();
    }

}
