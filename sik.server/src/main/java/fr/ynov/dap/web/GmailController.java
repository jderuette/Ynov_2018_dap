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
import fr.ynov.dap.dto.out.UnreadMailOutDto;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.GMailService;

/**
 * Controller to manage every call to Google Gmail API.
 * @author Kévin Sibué
 *
 */
@RestController
@RequestMapping("/gmail")
public class GmailController extends BaseController {

    /**
     * Instance of Gmail service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GMailService gmailService;

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Endpoint to get this number of unread message.
     * @param userId User's Id
     * @return Number of unread mail for user linked by userId. JSON Formatted.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration provided.
     * @throws UserNotFoundException Thrown when user is not found.
     * @throws NoGoogleAccountException Thrown when user haven't any google account.
     */
    @RequestMapping("/nbUnread/{userId}")
    public final UnreadMailOutDto getNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException, UserNotFoundException,
            NoGoogleAccountException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getGoogleAccounts().size() == 0) {
            throw new NoGoogleAccountException();
        }

        Integer nbUnreadMail = 0;

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {
            nbUnreadMail += gmailService.getNbUnreadEmails(gAcc.getAccountName());
        }

        return new UnreadMailOutDto(nbUnreadMail);

    }

    @Override
    protected final String getClassName() {
        return GmailController.class.getName();
    }

}
