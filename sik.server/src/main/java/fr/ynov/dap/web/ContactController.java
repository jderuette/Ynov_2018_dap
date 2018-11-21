package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

    /**
     * Get number of contacts from every account (ms, google, ...).
     * @param userId User id
     * @return NumberContactOutDto instance
     * @throws GeneralSecurityException Security exception
     * @throws IOException Exception
     * @throws UserNotFoundException No user found for this user id
     * @throws NoGoogleAccountException No google account found for this user
     * @throws NoMicrosoftAccountException No microsoft found fot this user
     */
    @RequestMapping("/nbContacts/{userId}")
    public final NumberContactOutDto getNumberOfContacts(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserNotFoundException, NoGoogleAccountException,
            NoMicrosoftAccountException {

        AppUser user = getUserById(userId);

        Integer googleNbContacts = contactService.getNumberOfContacts(user);

        Integer microsoftNbContacts = outlookService.getNumberOfContacts(user);

        return new NumberContactOutDto(googleNbContacts + microsoftNbContacts);

    }

    /**
     * Endpoint to get the user's number of contact from every Google account.
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

        AppUser user = getUserById(userId);

        Integer nbContacts = contactService.getNumberOfContacts(user);

        return new NumberContactOutDto(nbContacts);

    }

    /**
     * Endpoint to get the user's number of contact from every Microsoft accounts.
     * @param userId User's Id
     * @return NumberContactOutDto instance
     * @throws UserNotFoundException No user found
     * @throws NoMicrosoftAccountException No microsoft account linked with this user
     * @throws NoNextEventException no next event for current user on microsoft
     * @throws IOException exception
     */
    @RequestMapping("/microsoft/nbContacts/{userId}")
    public NumberContactOutDto getMicrosoftNumberOfContacts(@PathVariable("userId") final String userId)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = getUserById(userId);

        Integer nbOfContacts = outlookService.getNumberOfContacts(user);

        return new NumberContactOutDto(nbOfContacts);

    }

    @Override
    protected final String getClassName() {
        return ContactController.class.getName();
    }

}
