package fr.ynov.dap.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.DataStore;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.microsoft.DateTimeTimeZone;
import fr.ynov.dap.data.microsoft.EmailAddress;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.data.microsoft.MicrosoftEvent;
import fr.ynov.dap.data.microsoft.Recipient;
import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.data.microsoft.TokenResponseRepository;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.google.GmailService;
import fr.ynov.dap.google.PeopleService;
import fr.ynov.dap.microsoft.OutlookService;

/**
 * root controller.
 * @author MBILLEMAZ
 *
 */
@Controller
public class IndexController {

    /**
     * own gmail service.
     */
    @Autowired
    private GmailService gmailService;

    /**
     * google people service.
     */
    @Autowired
    private PeopleService peopleService;

    /**
     * outlook service.
     */
    @Autowired
    private OutlookService outlookService;

    /**
     * calendar service.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * google repo.
     */
    @Autowired
    private AppUserRepository googleRepository;

    /**
     * microsoft repo.
     */
    @Autowired
    private MicrosoftAccountRepository microsoftRepository;

    /**
     * token repo.
     */
    @Autowired
    private TokenResponseRepository tokenRepository;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * get users.
     * @param model model
     * @return users
     * @throws IOException exception
     */
    @RequestMapping("/admin")
    public String getDataStore(final ModelMap model) throws IOException {
        GoogleAuthorizationCodeFlow flow = gmailService.getFlow();
        DataStore<StoredCredential> cred = flow.getCredentialDataStore();

        Map<String, StoredCredential> mapGoogle = new HashMap<String, StoredCredential>();
        Map<String, TokenResponse> mapMicrosoft = new HashMap<String, TokenResponse>();

        List<String> keys = new ArrayList<String>(cred.keySet());
        List<StoredCredential> values = new ArrayList<StoredCredential>(cred.values());

        List<String> tenantIdList = new ArrayList<String>();
        for (int i = 0; i < keys.size(); i++) {
            mapGoogle.put(keys.get(i), values.get(i));
        }

        List<MicrosoftAccount> mAccounts = (List<MicrosoftAccount>) microsoftRepository.findAll();
        for (MicrosoftAccount microsoftAccount : mAccounts) {
            mapMicrosoft.put(microsoftAccount.getName(),
                    tokenRepository.findOneByMicrosoftAccount(microsoftAccount.getId()));
            tenantIdList.add(microsoftAccount.getTenantId());
        }

        model.addAttribute("googleList", mapGoogle);
        model.addAttribute("microsoftList", mapMicrosoft);
        model.addAttribute("tenantIdList", tenantIdList);
        model.addAttribute("current", "admin");
        model.addAttribute("fragFile", "admin");
        model.addAttribute("frag", "adminBody");
        return "base";
    }

    /**
     * Get nb unread mail in all microsoft and google account.
     * @param userKey app User
     * @return nb unread mail
     */
    @RequestMapping("/email/nbUnread")
    @ResponseBody
    public Integer getNbUnreadmails(@RequestParam("userKey") final String userKey) {
        AppUser user = googleRepository.findByName(userKey);

        Integer nbUnread = -1;
        try {
            nbUnread = outlookService.getNbUnread(user);
            nbUnread += gmailService.getNbUnreadMailForUser(user, "me");
        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les mails outlook de l'utilisateur " + userKey, e);
        } catch (Exception e) {
            LOGGER.error("Impossible de récupérer les mails gmail de l'utilisateur " + userKey, e);
        }

        return nbUnread;
    }

    /**
     * Get nb of contacts in all microsoft and google account.
     * @param userKey app User
     * @return nb contact
     */
    @RequestMapping("/contact/number")
    @ResponseBody
    public Integer getNbContact(@RequestParam("userKey") final String userKey) {
        AppUser user = googleRepository.findByName(userKey);

        Integer nbContact = -1;
        try {
            Integer nbOutlookContact = outlookService.getNbContact(user);
            Integer nbGoogleContact = peopleService.getNbContact(user);
            if (nbOutlookContact > 0 || nbGoogleContact > 0) {
                nbContact = 0;
                if (nbOutlookContact > 0) {
                    nbContact += nbOutlookContact;
                }
                if (nbGoogleContact > 0) {
                    nbContact += nbGoogleContact;
                }
            }

        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les contects outlook de l'utilisateur " + userKey, e);
        } catch (Exception e) {
            LOGGER.error("Impossible de récupérer les contacts gmail de l'utilisateur " + userKey, e);
        }

        return nbContact;
    }

    /**
     * Get the next event in all microsoft and google account.
     * @param userKey app User
     * @return next event
     */
    @RequestMapping("/event/next")
    @ResponseBody
    public MicrosoftEvent getNextEvent(@RequestParam("userKey") final String userKey) {
        AppUser user = googleRepository.findByName(userKey);

        MicrosoftEvent nextEvent = null;
        try {
            nextEvent = outlookService.getNextEvent(user);
            Event nextGoogleEvent = calendarService.getNextEvent(user, "me");
            if (nextGoogleEvent != null) {
                /*
                JsonElement startDate = json.getAsJsonObject("start").get("date");
                JsonElement endDate = json.getAsJsonObject("end").get("date");
                if (startDate == null) {
                    startDate = json.getAsJsonObject("start").get("dateTime");
                }
                if (endDate == null) {
                    endDate = json.getAsJsonObject("end").get("dateTime");
                }
                DateFormat format = new SimpleDateFormat();
                Date startDateValue = format.parse(startDate.getAsJsonPrimitive().getAsString());
                Date endDateValue = new Date(endDate.toString());
                DateTimeTimeZone startDateTimeZone = new DateTimeTimeZone(startDateValue);
                DateTimeTimeZone endDateTimeZone = new DateTimeTimeZone(endDateValue);*/
                DateTime startDate = nextGoogleEvent.getStart().getDateTime();
                if (startDate == null) {
                    startDate = nextGoogleEvent.getStart().getDate();
                }
                DateTime endDate = nextGoogleEvent.getEnd().getDateTime();
                if (endDate == null) {
                    endDate = nextGoogleEvent.getEnd().getDate();
                }
                LOGGER.info(startDate);
                DateTimeTimeZone startDateTimeZone = new DateTimeTimeZone(
                        new Date(startDate.getValue()));
                DateTimeTimeZone endDateTimeZone = new DateTimeTimeZone(new Date(endDate.getValue()));
                if (startDateTimeZone.compareTo(nextEvent.getStart()) <= 1) {
                    Recipient recipient = new Recipient(
                            new EmailAddress(nextGoogleEvent.getOrganizer().getDisplayName(),
                                    nextGoogleEvent.getOrganizer().getEmail()));
                    nextEvent = new MicrosoftEvent(nextGoogleEvent.getId(), nextGoogleEvent.getSummary(), recipient,
                            startDateTimeZone, endDateTimeZone);
                }
            }

        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les evenements de l'utilisateur " + userKey, e);
        } catch (Exception e) {
            LOGGER.error("Impossible de récupérer les evenements de l'utilisateur " + userKey, e);
        }

        return nextEvent;
    }
}
