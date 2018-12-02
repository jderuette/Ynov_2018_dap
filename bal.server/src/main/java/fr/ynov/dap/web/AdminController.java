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

import fr.ynov.dap.comparateur.Sorter;
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

/**
 * The Class AdminController.
 */
@Controller
@RequestMapping("/index")
public class AdminController extends BaseController {

    /** The google account service. */
    @Autowired
    private GoogleAccountService googleAccountService;

    /** The microsoft account service. */
    @Autowired
    private MicrosoftAccountService microsoftAccountService;

    /** The microsoft account repository. */
    @Autowired
    //TODO bal by Djer |SOA| Pas top, et pas utile pourrait être injecté dans le service
    private MicrosoftAccountRepository microsoftAccountRepository;

    /** The calendar service. */
    @Autowired
    private CalendarService calendarService;

    /** The outlook service. */
    @Autowired
    private OutlookService outlookService;

    /** The people service. */
    @Autowired
    private PeopleAPIService peopleService;

    /** The gmail service. */
    @Autowired
    private GMailService gmailService;

    /**
     * Datastores.
     *
     * @param model the model
     * @return the string
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/users")
    public String datastores(final ModelMap model) throws IOException, GeneralSecurityException {

        ArrayList<Credential> googleCredentials = googleAccountService.getStoredCredentials();
        ArrayList<Credential> microsoftCredentials = microsoftAccountService
                .getStoredCredentials(microsoftAccountRepository);
        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.addAll(googleCredentials);
        credentials.addAll(microsoftCredentials);
        model.addAttribute("userKnown", true);
        model.addAttribute("credentials", credentials);
        //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
        model.addAttribute("fragment", "fragments/datastore");

        return "base";

    }

    /**
     * Calendar.
     *
     * @param model  the model
     * @param userId the user id
     * @return the string
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     * @throws UserException            the user exception
     */
    @RequestMapping("/nextEvent/{userId}")
    public String calendar(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException, UserException {

        AppUser user = getAppUserRepository().findByUserKey(userId);
        if (user == null) {

            //TODO bal by Djer |Log4J| Contextualiser tes logs (" for userId : " + userId)
            getLogger().warn("User is undefined. Show Admin Calendar with error");
            model.addAttribute("userKnown", false);
            //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
            model.addAttribute("fragment", "fragments/calendar");
            //TODO bal by Djer |POO| Evite les multiples return dans une même méthode
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
            //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
            model.addAttribute("fragment", "fragments/calendar");
            //TODO bal by Djer |POO| Evite les multiples return dans une même méthode
            return "base";

        }

        Collections.sort(events, new Sorter());
        model.addAttribute("userKnown", true);
        model.addAttribute("event", events.get(0));
        //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
        model.addAttribute("fragment", "fragments/calendar");

        return "base";

    }

    /**
     * Mail.
     *
     * @param model  the model
     * @param userId the user id
     * @return the string
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/mails/{userId}")
    public String mail(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {
        AppUser user = getAppUserRepository().findByUserKey(userId);
        if (user == null) {
            //TODO bal by Djer |Log4J| Contextualiser tes logs (" for userId : " + userId)
            getLogger().warn("User is undefined. Show Admin Mail with error");
            model.addAttribute("userKnown", false);
            //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
            model.addAttribute("fragment", "fragments/mailNumber");
            //TODO bal by Djer |POO| Evite les multiples return dans une même méthode
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
     * Contacts.
     *
     * @param model  the model
     * @param userId the user id
     * @return the string
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/contacts/{userId}")
    public String contacts(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);

        if (user == null) {
            //TODO bal by Djer |Log4J| Contextualiser tes logs (" for userId : " + userId)
            getLogger().warn("User is undefined. Show Admin Contact with error");
            model.addAttribute("userKnown", false);
            //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
            model.addAttribute("fragment", "fragments/contact");
            //TODO bal by Djer |POO| Evite les multiples return dans une même méthode
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
     * Mail list.
     *
     * @param model  the model
     * @param userId the user id
     * @return the string
     * @throws IOException              Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/mails/list/{userId}")
    public String mailList(final ModelMap model, @PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException {

        AppUser user = getAppUserRepository().findByUserKey(userId);
        if (user == null) {
            //TODO bal by Djer |Log4J| Contextualiser tes logs (" for userId : " + userId)
            getLogger().warn("User is undefined. Show Admin Mail List with error");
            model.addAttribute("userKnown", false);
          //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
            model.addAttribute("fragment", "fragments/mail_list");
            //TODO bal by Djer |POO| Evite les multiples return dans une même méthode
            return "base";

        }

        ArrayList<Inbox> inboxs = outlookService.getMessages(user);
        model.addAttribute("userKnown", true);
        model.addAttribute("inboxs", inboxs);
        //FIXME bal by Djer |Thymleaf| Tes fragments ne sont PAS rangé dans un sous dossier "fragments"
        model.addAttribute("fragment", "fragments/mail_list");
        return "base";

    }

    /*
     * (non-Javadoc)
     * 
     * @see fr.ynov.dap.web.BaseController#getClassName()
     */
    @Override
    protected final String getClassName() {
        return AdminController.class.getName();
    }

}
