package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.GoogleAccountException;
import fr.ynov.dap.exception.MicrosoftAccountException;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.GMailService;
import fr.ynov.dap.microsoft.OutlookService;

@RestController
@RequestMapping("/mail")
public class MailController extends BaseController {

    /**
     * Instance of Gmail service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GMailService gmailService;

    /**
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;
    

    @Override
    protected final String getClassName() {
        return MailController.class.getName();
    }

    /**
     * Get number of unread email from every account (ms, google, ...).
     * @param userId Current user id
     * @return UnreadMailOutDto instance
     * @throws UserException No user found for this user id
     * @throws NoConfigurationException No configuration available
     * @throws GoogleAccountException No google account found for this user
     * @throws IOException Exception
     * @throws GeneralSecurityException Security exception
     * @throws MicrosoftAccountException No microsoft account found for this user
     */
    @RequestMapping("/nbUnread/{userId}")
    public final int getNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws UserException, GoogleAccountException, IOException,
            GeneralSecurityException, MicrosoftAccountException {

        AppUser user = getUserById(userId);

        int googleNbUnreadMail = gmailService.getNbUnreadMailAllAccount(user);

        int microsoftNbUnreadMail = outlookService.getNbUnreadMails(user);

        return googleNbUnreadMail + microsoftNbUnreadMail;

    }

    /**
     * Endpoint to get this number of unread message.
     * @param userId User's Id
     * @return Number of unread mail for user linked by userId. JSON Formatted.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration provided.
     * @throws UserException Thrown when user is not found.
     * @throws GoogleAccountException Thrown when user haven't any google account.
     */
    @RequestMapping("/google/nbUnread/{userId}")
    public final int getGoogleNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException, UserException,
            GoogleAccountException {

        AppUser user = getUserById(userId);

        int nbUnreadMail = gmailService.getNbUnreadMailAllAccount(user);

        return nbUnreadMail;

    }

    /**
     * Endpoint to get this number of unread message.
     * @param userId User's Id
     * @param  request Http request
     * @return Number of unread mail for user linked by userId. JSON Formatted.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws UserException Thrown when user is not found.
     * @throws MicrosoftAccountException Thrown when user haven't any microsoft account.
     * @throws NoConfigurationException Exception
     */
    @RequestMapping("/microsoft/nbUnread/{userId}")
    public final int getMicrosoftNumberOfUnreadMessage(@PathVariable("userId") final String userId,
            final HttpServletRequest request) throws UserException, MicrosoftAccountException, IOException, GeneralSecurityException {

        AppUser user = getUserById(userId);

        int numberOfUnreadMails = outlookService.getNbUnreadMails(user);

        return numberOfUnreadMails;

    }

}
