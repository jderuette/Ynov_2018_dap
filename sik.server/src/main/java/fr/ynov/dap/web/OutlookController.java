package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.dto.out.NextEventOutDto;
import fr.ynov.dap.dto.out.UnreadMailOutDto;
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
     */
    @RequestMapping("/nbUnread/{userId}")
    public final UnreadMailOutDto getNumberOfUnreadMessage(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, IOException, GeneralSecurityException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getMicrosoftAccounts().size() == 0) {
            throw new NoMicrosoftAccountException();
        }

        Integer numberOfUnreadMails = 0;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

            String email = account.getEmail();
            String tenantId = account.getTenantId();
            TokenResponse tokens = account.getToken();

            numberOfUnreadMails += outlookService.getNbUnreadEmails(tenantId, email, tokens);

        }

        return new UnreadMailOutDto(numberOfUnreadMails);

    }

    @RequestMapping("/nextEvent/{userId}")
    public NextEventOutDto getNextEvent(@PathVariable("userId") final String userId, final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getMicrosoftAccounts().size() == 0) {
            throw new NoMicrosoftAccountException();
        }

        ArrayList<MicrosoftCalendarEvent> events = new ArrayList<>();

        for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

            String email = msAcc.getEmail();
            String tenantId = msAcc.getTenantId();
            TokenResponse tokens = msAcc.getToken();

            MicrosoftCalendarEvent evnt = outlookService.getNextEvent(tenantId, email, tokens);

            if (evnt != null) {
                events.add(evnt);
            }

        }

        if (events.size() == 0) {
            throw new NoNextEventException();
        }

        Collections.sort(events, new SortByNearest());

        MicrosoftCalendarEvent evnt = events.get(0);

        return new NextEventOutDto(evnt);

    }

    @Override
    protected final String getClassName() {
        return OutlookController.class.getName();
    }

}
