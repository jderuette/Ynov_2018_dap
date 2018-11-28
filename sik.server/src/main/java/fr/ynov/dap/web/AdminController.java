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
     * Default page name to use.
     */
    private static final String DEFAULT_PAGE_NAME = "base";

    /**
     * User known attribute name to use on model.
     */
    private static final String USER_KNOWN_ATTRIBUTE = "userKnown";

    /**
     * Fragment attribute name to use on model.
     */
    private static final String FRAGMENT_ATTRIBUTE = "fragment";

    private static final String USER_ID_PATH_VARIABLE = "userId";

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

        model.addAttribute("credentials", credentials);

        return getPageWithFragment(model, true, "fragments/admin_datastore");

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
    public String calendar(final ModelMap model, @PathVariable(USER_ID_PATH_VARIABLE) final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException, UserNotFoundException,
            NoNextEventException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Calendar with error");

            return getPageWithFragment(model, false, "fragments/admin_calendar");

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

            model.addAttribute("event", null);

            return getPageWithFragment(model, true, "fragments/admin_calendar");

        }

        Collections.sort(events, new SortByNearest());

        model.addAttribute("event", events.get(0));

        return getPageWithFragment(model, true, "fragments/admin_calendar");

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
    public String mail(final ModelMap model, @PathVariable(USER_ID_PATH_VARIABLE) final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Mail with error");

            return getPageWithFragment(model, false, "fragments/admin_mail");

        }

        Integer nbUnreadMails = 0;

        nbUnreadMails += outlookService.getNbUnreadEmails(user);

        nbUnreadMails += gmailService.getNbUnreadEmails(user);

        model.addAttribute("count", nbUnreadMails);

        return getPageWithFragment(model, true, "fragments/admin_mail");

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
    public String contacts(final ModelMap model, @PathVariable(USER_ID_PATH_VARIABLE) final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Contact with error");

            return getPageWithFragment(model, false, "fragments/admin_contact");

        }

        Integer nbContacts = 0;

        nbContacts += outlookService.getNumberOfContacts(user);

        nbContacts += contactService.getNumberOfContacts(user);

        model.addAttribute("count", nbContacts);

        return getPageWithFragment(model, true, "fragments/admin_contact");

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
    public String mailsList(final ModelMap model, @PathVariable(USER_ID_PATH_VARIABLE) final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {

            getLogger().warn("User is undefined. Show Admin Mail List with error");

            return getPageWithFragment(model, false, "fragments/admin_mail_list");

        }

        ArrayList<Inbox> inboxs = outlookService.getMessages(user);

        model.addAttribute("inboxs", inboxs);

        return getPageWithFragment(model, true, "fragments/admin_mail_list");

    }

    /**
     * Update model to be conform with page.
     * @param model ModelMap
     * @param known True if user is knoww
     * @param fragmentName Fragment to use on page
     * @return Base page to use
     */
    private String getPageWithFragment(final ModelMap model, final Boolean known, final String fragmentName) {

        model.addAttribute(USER_KNOWN_ATTRIBUTE, known);
        model.addAttribute(FRAGMENT_ATTRIBUTE, fragmentName);

        return DEFAULT_PAGE_NAME;

    }

    @Override
    protected final String getClassName() {
        return AdminController.class.getName();
    }

}
