package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.dto.out.NumberContactOutDto;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.UserNotFoundException;
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
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Endpoint to get the user's number of contact.
     * @param userId User's Id
     * @return Number of contact for user linked by userId. JSON Formatted.
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws UserNotFoundException Thrown when the user is not found.
     * @throws NoGoogleAccountException Thrown when the user haven't any google account.
     */
    @RequestMapping("/nbContacts/{userId}")
    public final NumberContactOutDto getNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserNotFoundException, NoGoogleAccountException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getGoogleAccounts().size() == 0) {
            throw new NoGoogleAccountException();
        }

        Integer nbContacts = 0;

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {
            String accountName = gAcc.getAccountName();
            nbContacts += contactService.getNumberOfContacts(accountName);
        }

        return new NumberContactOutDto(nbContacts);

    }

    @Override
    protected final String getClassName() {
        return ContactController.class.getName();
    }

}
