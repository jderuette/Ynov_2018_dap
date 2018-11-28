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

import fr.ynov.dap.comparator.Sorter;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.Inbox;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.google.GMailService;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.google.PeopleAPIService;
import fr.ynov.dap.microsoft.MicrosoftAccountService;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.model.Credential;
import fr.ynov.dap.model.EventAllApi;
import fr.ynov.dap.model.GoogleCalendarEvent;
import fr.ynov.dap.model.MicrosoftCalendarEvent;
import fr.ynov.dap.repository.MicrosoftAccountRepository;

@Controller
@RequestMapping("/index")
public class AdminController extends BaseController {
	 /**
     * Auto inject on GoogleAccountService.
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Auto inject on MicrosoftAccountService.
     */
    @Autowired
    private MicrosoftAccountService microsoftAccountService;

    /**
     * Instance of MicrosoftAccountRepository service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

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
     * Instance of Calendar service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private PeopleAPIService peopleService;

    /**
     * Instance of GMailService.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GMailService gmailService;

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
            throws IOException, GeneralSecurityException {

        ArrayList<Credential> googleCredentials = googleAccountService.getStoredCredentials();

        ArrayList<Credential> microsoftCredentials = microsoftAccountService.getStoredCredentials(microsoftAccountRepository);

        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.addAll(googleCredentials);
        credentials.addAll(microsoftCredentials);

        model.addAttribute("userKnown", true);
        model.addAttribute("credentials", credentials);
        model.addAttribute("fragment", "fragments/datastore");

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
     */
    @RequestMapping("/nextEvent/{userId}")
    public String calendar(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException, UserException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Calendar with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/calendar");

            return "base";

        }

        List<EventAllApi> events = new ArrayList<>();

        GoogleCalendarEvent googleEvent = calendarService.getNextEvent(user);
        if (googleEvent != null) {
            events.add(googleEvent);
        }

        MicrosoftCalendarEvent microsoftEvent = outlookService.getNextEvent(user);
        if (microsoftEvent != null) {
            events.add(microsoftEvent);
        }

        if (events.size() == 0) {

            model.addAttribute("userKnown", true);
            model.addAttribute("event", null);
            model.addAttribute("fragment", "fragments/calendar");

            return "base";

        }

        Collections.sort(events, new Sorter());

        model.addAttribute("userKnown", true);
        model.addAttribute("event", events.get(0));
        model.addAttribute("fragment", "fragments/calendar");

        return "base";

    }

    /**
     * Show number of unread mail for current user.
     * @param model Model for page.
     * @param userId Id of current user.
     * @return Html page
     * @throws NoConfigurationException No configuration available
     * @throws IOException Exception
     * @throws GeneralSecurityException Security Exception
     */
    @RequestMapping("/mails/{userId}")
    public String mail(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Mail with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/mailNumber");

            return "base";

        }

        Integer nbUnreadMails = 0;

        nbUnreadMails += outlookService.getNbUnreadMails(user);

        nbUnreadMails += gmailService.getNbUnreadMailAllAccount(user);

        model.addAttribute("userKnown", true);
        model.addAttribute("count", nbUnreadMails);
        model.addAttribute("fragment", "fragments/mailNumber");

        return "base";

    }

    /**
     * Show number of contacts.
     * @param model Mode for page
     * @param userId Id of current user
     * @return Html Page
     * @throws IOException Exception
     * @throws GeneralSecurityException Security exception
     */
    @RequestMapping("/contacts/{userId}")
    public String contacts(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Contact with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/contact");

            return "base";

        }

        Integer nbContacts = 0;

        nbContacts += outlookService.getNumberOfPeoples(user);

        nbContacts += peopleService.getTotalPeopleAllAccount(user);

        model.addAttribute("userKnown", true);
        model.addAttribute("count", nbContacts);
        model.addAttribute("fragment", "fragments/contact");

        return "base";

    }

    /**
     * Get every mail from every microsoft account.
     * @param model Model for page
     * @param userId Id of current user
     * @return Html page
     * @throws IOException Exception
     * @throws GeneralSecurityException Security Exception
     */
    @RequestMapping("/mails/list/{userId}")
    public String mailList(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Mail List with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/mail_list");

            return "base";

        }

        ArrayList<Inbox> inboxs = outlookService.getMessages(user);

        model.addAttribute("userKnown", true);
        model.addAttribute("inboxs", inboxs);
        model.addAttribute("fragment", "fragments/mail_list");

        return "base";

    }

    @Override
    protected final String getClassName() {
        return AdminController.class.getName();
    }

}
