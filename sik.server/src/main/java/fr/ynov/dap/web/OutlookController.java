package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.dto.out.NextEventOutDto;
import fr.ynov.dap.dto.out.NumberContactOutDto;
import fr.ynov.dap.dto.out.UnreadMailOutDto;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.microsoft.MicrosoftAccountService;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.model.MicrosoftCalendarEvent;

/**
 * Controller to manage every call to Microsoft Outlook API.
 * @author Kévin Sibué
 *
 */
@RestController
@RequestMapping("/microsoft/outlook")
public class OutlookController extends BaseController {

    /**
     * Instance of Microsoft Account service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private MicrosoftAccountService microsoftAccountService;

    /**
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

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
    @RequestMapping("/nbUnread/{userId}")
    public final UnreadMailOutDto getNumberOfUnreadMessage(@PathVariable("userId") final String userId,
            final HttpServletRequest request) throws UserNotFoundException, NoMicrosoftAccountException,
            NoConfigurationException, IOException, GeneralSecurityException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        Integer numberOfUnreadMails = outlookService.getNbUnreadEmails(user);

        return new UnreadMailOutDto(numberOfUnreadMails);

    }

    @RequestMapping("/nextEvent/{userId}")
    public NextEventOutDto getNextEvent(@PathVariable("userId") final String userId, final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        MicrosoftCalendarEvent evnt = outlookService.getNextEvent(user);

        return new NextEventOutDto(evnt);

    }

    @RequestMapping("/nbContacts/{userId}")
    public NumberContactOutDto getNumberOfContacts(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        Integer nbOfContacts = outlookService.getNumberOfContacts(user);

        return new NumberContactOutDto(nbOfContacts);

    }

    @Override
    protected final String getClassName() {
        return OutlookController.class.getName();
    }

}
