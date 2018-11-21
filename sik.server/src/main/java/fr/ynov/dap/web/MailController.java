package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.dto.out.UnreadMailOutDto;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.GMailService;
import fr.ynov.dap.microsoft.OutlookService;

/**
 * Controller to manage every call to Google Gmail API.
 * @author Kévin Sibué
 *
 */
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

    @RequestMapping("/nbUnread/{userId}")
    public final UnreadMailOutDto getNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws UserNotFoundException, NoConfigurationException, NoGoogleAccountException, IOException,
            GeneralSecurityException, NoMicrosoftAccountException {

        AppUser user = GetUserById(userId);

        Integer googleNbUnreadMail = gmailService.getNbUnreadEmails(user);

        Integer microsoftNbUnreadMail = outlookService.getNbUnreadEmails(user);

        return new UnreadMailOutDto(googleNbUnreadMail + microsoftNbUnreadMail);

    }

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
    @RequestMapping("/google/nbUnread/{userId}")
    public final UnreadMailOutDto getGoogleNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException, UserNotFoundException,
            NoGoogleAccountException {

        AppUser user = GetUserById(userId);

        Integer nbUnreadMail = gmailService.getNbUnreadEmails(user);

        return new UnreadMailOutDto(nbUnreadMail);

    }

    /**
     * Endpoint to get this number of unread message.
     * @param userId User's Id
     * @param  request Http request
     * @return Number of unread mail for user linked by userId. JSON Formatted.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws UserNotFoundException Thrown when user is not found.
     * @throws NoMicrosoftAccountException Thrown when user haven't any microsoft account.
     * @throws NoConfigurationException Exception
     */
    @RequestMapping("/microsoft/nbUnread/{userId}")
    public final UnreadMailOutDto getMicrosoftNumberOfUnreadMessage(@PathVariable("userId") final String userId,
            final HttpServletRequest request) throws UserNotFoundException, NoMicrosoftAccountException,
            NoConfigurationException, IOException, GeneralSecurityException {

        AppUser user = GetUserById(userId);

        Integer numberOfUnreadMails = outlookService.getNbUnreadEmails(user);

        return new UnreadMailOutDto(numberOfUnreadMails);

    }

    @Override
    protected final String getClassName() {
        return MailController.class.getName();
    }

}
