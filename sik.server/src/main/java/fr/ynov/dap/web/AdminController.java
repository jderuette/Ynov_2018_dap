package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.contract.ApiEvent;
import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.contract.TokenRepository;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.microsoft.service.MicrosoftAccountService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.Credential;
import fr.ynov.dap.model.google.GoogleCalendarEvent;
import fr.ynov.dap.model.microsoft.MicrosoftCalendarEvent;

/**
 * Controller to manage via interface users.
 * @author Kévin Sibué
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Auto inject on GoogleAccountService.
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Auto inject on MicrosoftAccountService.
     */
    @Autowired
    private MicrosoftAccountService msAccountService;

    /**
     * Instance of TokenRepository service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private TokenRepository tokenRepository;

    /**
     * Instance of Calendar service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private CalendarService calendarService;

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
     * Method return a view to show every user stored on datastore.
     * @param model Model
     * @return Html page
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Exception
     */
    @RequestMapping("/users")
    public String datastores(final ModelMap model)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        ArrayList<Credential> googleCredentials = googleAccountService.getStoredCredentials();

        ArrayList<Credential> msCredentials = msAccountService.getStoredCredentials(tokenRepository);

        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.addAll(googleCredentials);
        credentials.addAll(msCredentials);

        model.addAttribute("credentials", credentials);
        model.addAttribute("fragment", "fragments/admin_datastore");

        return "base";

    }

    /**
     * Get UI form next event of a specific user.
     * @param model Model used
     * @param userId User id
     * @return Html page
     * @throws NoConfigurationException No configuration found
     * @throws IOException Exception
     * @throws GeneralSecurityException Security exception
     * @throws UserNotFoundException User not found exception
     * @throws NoNextEventException No next event found for current user
     * @throws NoGoogleAccountException No google account found for current user
     * @throws NoMicrosoftAccountException No microsoft account found for current user
     */
    @RequestMapping("/nextEvent/{userId}")
    public String showMail(final ModelMap model, @PathVariable("userId") final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException, UserNotFoundException,
            NoNextEventException, NoGoogleAccountException, NoMicrosoftAccountException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/admin_calendar");

            return "base";

        }

        List<ApiEvent> events = new ArrayList<>();

        try {
            GoogleCalendarEvent gEvnt = calendarService.getNextEvent(user);
            if (gEvnt != null) {
                events.add(gEvnt);
            }
        } catch (Exception ex) {

        }

        try {
            MicrosoftCalendarEvent msEvnt = outlookService.getNextEvent(user);
            if (msEvnt != null) {
                events.add(msEvnt);
            }
        } catch (Exception ex) {

        }

        if (events.size() == 0) {
            throw new NoNextEventException();
        }

        Collections.sort(events, new SortByNearest());

        model.addAttribute("userKnown", true);
        model.addAttribute("event", events.get(0));
        model.addAttribute("fragment", "fragments/admin_calendar");

        return "base";

    }

}
