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
import fr.ynov.dap.contract.MicrosoftAccountRepository;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.google.ContactService;
import fr.ynov.dap.google.GMailService;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.microsoft.service.MicrosoftAccountService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.Credential;
import fr.ynov.dap.model.google.GoogleCalendarEvent;
import fr.ynov.dap.model.microsoft.Inbox;
import fr.ynov.dap.model.microsoft.MicrosoftCalendarEvent;

/**
 * Controller to manage via interface users.
 * @author Kévin Sibué
 *
 */
@Controller
@RequestMapping("/admin")
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
    private MicrosoftAccountService msAccountService;

    /**
     * Instance of MicrosoftAccountRepository service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private MicrosoftAccountRepository msAccountRepository;

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
    private ContactService contactService;

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
            throws NoConfigurationException, IOException, GeneralSecurityException {

        ArrayList<Credential> googleCredentials = googleAccountService.getStoredCredentials();

        ArrayList<Credential> msCredentials = msAccountService.getStoredCredentials(msAccountRepository);

        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.addAll(googleCredentials);
        credentials.addAll(msCredentials);

        model.addAttribute("userKnown", true);
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
     */
    @RequestMapping("/nextEvent/{userId}")
    public String calendar(final ModelMap model, @PathVariable("userId") final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException, UserNotFoundException,
            NoNextEventException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Calendar with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/admin_calendar");

            return "base";

        }

        List<ApiEvent> events = new ArrayList<>();

        GoogleCalendarEvent gEvnt = calendarService.getNextEvent(user);
        if (gEvnt != null) {
            events.add(gEvnt);
        }

        MicrosoftCalendarEvent msEvnt = outlookService.getNextEvent(user);
        if (msEvnt != null) {
            events.add(msEvnt);
        }

        if (events.size() == 0) {

            model.addAttribute("userKnown", true);
            model.addAttribute("event", null);
            model.addAttribute("fragment", "fragments/admin_calendar");

            return "base";

        }

        Collections.sort(events, new SortByNearest());

        model.addAttribute("userKnown", true);
        model.addAttribute("event", events.get(0));
        model.addAttribute("fragment", "fragments/admin_calendar");

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
            throws NoConfigurationException, IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Mail with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/admin_mail");

            return "base";

        }

        Integer nbUnreadMails = 0;

        nbUnreadMails += outlookService.getNbUnreadEmails(user);

        nbUnreadMails += gmailService.getNbUnreadEmails(user);

        model.addAttribute("userKnown", true);
        model.addAttribute("count", nbUnreadMails);
        model.addAttribute("fragment", "fragments/admin_mail");

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
            model.addAttribute("fragment", "fragments/admin_contact");

            return "base";

        }

        Integer nbContacts = 0;

        nbContacts += outlookService.getNumberOfContacts(user);

        nbContacts += contactService.getNumberOfContacts(user);

        model.addAttribute("userKnown", true);
        model.addAttribute("count", nbContacts);
        model.addAttribute("fragment", "fragments/admin_contact");

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
    public String mailsList(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Mail List with error");

            model.addAttribute("userKnown", false);
            model.addAttribute("fragment", "fragments/admin_mail_list");

            return "base";

        }

        ArrayList<Inbox> inboxs = outlookService.getMessages(user);

        model.addAttribute("userKnown", true);
        model.addAttribute("inboxs", inboxs);
        model.addAttribute("fragment", "fragments/admin_mail_list");

        return "base";

    }

    @Override
    protected final String getClassName() {
        return AdminController.class.getName();
    }

}
