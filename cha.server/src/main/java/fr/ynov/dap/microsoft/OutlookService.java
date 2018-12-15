package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.stereotype.Service;

import fr.ynov.dap.Constant;
import fr.ynov.dap.comparator.Sorter;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.Inbox;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.model.MicrosoftCalendarEvent;
import fr.ynov.dap.model.outlook.Message;
import fr.ynov.dap.model.outlook.OutlookContact;
import fr.ynov.dap.model.outlook.OutlookEvent;
import fr.ynov.dap.model.outlook.OutlookFolder;
import fr.ynov.dap.model.outlook.PagedResult;
import retrofit2.Response;

@Service
public class OutlookService extends OutlookAPIService {

    /**
     * Default name for Inbox folder.
     */
    //TODO cha by Djer |POO| Pourquoi public ? Privé serait suffisant
    public static final String INBOX_FOLDER_NAME = "inbox";

    /**
     * Get number of unread email for a user.
     * @param microsoftAccount User's microsoft account
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when configuration is missing
     */
    private Integer getNbUnreadMails(final MicrosoftAccount microsoftAccount)
            throws IOException, GeneralSecurityException {

        if (microsoftAccount == null) {

            getLogger().warn("MicrosoftAccount is null");

            return 0;

        }

        String email = microsoftAccount.getEmail();
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (tenantId.isEmpty() || email.isEmpty() || tokens == null) {
            //TODO cha by Djer |Log4J| Contextualise tes messages (" for accountName : " + microsoftAccount.getAccountName() + " and userKey : " + microsoftAccount.getOwner().getUserKey()) 
            getLogger().warn("MicrosoftAccount information are empty or null");
            //TODO cha by Djer |POO| Evite les multiples return dans une même méthode
            return 0;

        }

        TokenResponse newTokens = getToken(microsoftAccount);

        OutlookGetService outlookService = GetOutlookService.getOutlookService(newTokens.getAccessToken(), email);

        OutlookFolder inboxFolder = outlookService.getOutlookFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {

          //TODO cha by Djer |Log4J| Contextualise tes messages (" for accountName : " + microsoftAccount.getAccountName() + " and userKey : " + microsoftAccount.getOwner().getUserKey())
            getLogger().warn("Graph API respond with empty or malformed JSON");
          //TODO cha by Djer |POO| Evite les multiples return dans une même méthode
            return 0;

        }

        return inboxFolder.getUnreadItemCount();

    }

    /**
     * Get number of email for every Microsoft account of a user.
     * @param user Dap User to use
     * @throws NoConfigurationException Thrown when configuration is missing
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @return Number of unread email
     */
    public final Integer getNbUnreadMails(final AppUser user)
            throws IOException, GeneralSecurityException {

        if (user.getMicrosoftAccounts().size() == 0) {
          //TODO cha by Djer |Log4J| Contextualise tes messages (" for userKey : " + user.getUserKey())
            getLogger().warn("No MicrosoftAccount found for this DaP user");

            return 0;

        }

        Integer numberOfUnreadMails = 0;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

            numberOfUnreadMails += getNbUnreadMails(account);

        }

        return numberOfUnreadMails;

    }

    /**
     * Get next event of a specific Microsoft account.
     * @param microsoftAccount User's microsoft account
     * @return Next event of the current Microsoft account
     * @throws IOException Exception
     */
    private MicrosoftCalendarEvent getNextEvent(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {

            getLogger().warn("MicrosoftAccount is null");

            return null;

        }

        String email = microsoftAccount.getEmail();
        //TODO cha by Djer |IDE| Ton IDE te dit que ca n'est pas utiliser? Bug ? A supprimer ? 
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
          //TODO cha by Djer |Log4J| Contextualise tes messages (" for accountName : " + microsoftAccount.getAccountName() + " and userKey : " + microsoftAccount.getOwner().getUserKey())
            getLogger().warn("MicrosoftAccount information are empty or null");

            return null;

        }

        TokenResponse newTokens = getToken(microsoftAccount);

        String filter = "start/dateTime ge '" + Instant.now().toString() + "'";
        String sort = "start/dateTime ASC";
        String properties = "Organizer,subject,start,end,Attendees";
        Integer maxResults = 1;

        OutlookGetService outlookService = GetOutlookService.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<OutlookEvent>> response = outlookService.getEvents(sort, filter, properties, maxResults)
                .execute();

        PagedResult<OutlookEvent> events = response.body();

        //TODO cha by Djer |POO| Evite les mutliples return. Toutes tes conditions peuvent être "groupées" avec des || (en méttant en premier celles qui peuvent empecher les suivantes de fonctionner). Java évalue les conditions dans l'ordre, et comme il s'agit d'un "ou" dès que 1 n'est pas satisfait, il arete l'évaluation des suivantes (et entre dans la branche du if)
        if (events == null) {
            return null;
        }

        if (events.getValue() == null) {
            return null;
        }

        if (events.getValue().length == 0) {
            return null;
        }

        OutlookEvent nextEvent = events.getValue()[0];

        return new MicrosoftCalendarEvent(nextEvent, email);

    }

    /**
     * Get next event of a specific Microsoft account.
     * @param user Current Dap User to use
     * @return Next event of the current Microsoft account
     * @throws IOException Exception
     */
    public MicrosoftCalendarEvent getNextEvent(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {
            //TODO cha by Djer |Log4J| Evite d'utiliser uniquement l'ID, un identifiant plus "lisible", comme le "userKey" en plus sera utile
            getLogger().warn("Current user " + user.getId() + " haven't any MicrosoftAccount");

            return null;

        }

        //TODO cha by Djer |POO| Utilise List (interface) plutot que ArrayList (implementation)
        ArrayList<MicrosoftCalendarEvent> events = new ArrayList<>();

        for (MicrosoftAccount microsoftAccount : user.getMicrosoftAccounts()) {

            MicrosoftCalendarEvent evnt = getNextEvent(microsoftAccount);

            if (evnt != null) {
                events.add(evnt);
            }

        }

        if (events.size() == 0) {
            //TODO cha by Djer |Log4J| Est-ce vraiment un warning de ne pas avoir d'évènnements à venir ? Le level Info serait suffisant
            getLogger().warn("No next event found for current user : " + user.getId());

            return null;

        }

        Collections.sort(events, new Sorter());

        MicrosoftCalendarEvent evnt = events.get(0);

        return evnt;

    }

    /**
     * Get number of contacts for a specific user.
     * @param microsoftAccount User's microsoft account
     * @return Number of contact for current Microsoft account
     * @throws IOException Exception
     */
    private Integer getNumberOfPeople(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {

            getLogger().warn("MicrosoftAccount is null");

            return 0;

        }

        String email = microsoftAccount.getEmail();
      //TODO cha by Djer |IDE| Ton IDE te dit que ca n'est pas utiliser. Bug ? A supprimer ?
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
          //TODO cha by Djer |Log4J| Contextualise tes messages (" for accountName : " + microsoftAccount.getAccountName() + " and userKey : " + microsoftAccount.getOwner().getUserKey())
            getLogger().warn("MicrosoftAccount information are empty or null");

            return null;

        }

        String sort = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        TokenResponse newTokens = getToken(microsoftAccount);

        OutlookGetService outlookService = GetOutlookService.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<OutlookContact>> response = outlookService.getContacts(sort, properties).execute();

        PagedResult<OutlookContact> contacts = response.body();

        if (contacts != null && contacts.getValue() != null) {
            //TODO cha by Djer |POO| Evite les multiples return dans un même méthode
            return contacts.getValue().length;
        }

        getLogger().warn("Value return by Graph API isn't valid for microsoft account : " + microsoftAccount.getId());

        return 0;

    }

    /**
     * Get number of contacts for every Microsoft account of a Dap User.
     * @param user Dap User to use
     * @return Number of contacts
     * @throws IOException Exception
     */
    public final Integer getNumberOfPeoples(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {

            getLogger().warn("Current user " + user.getId() + " haven't any MicrosoftAccount");

            return 0;

        }

        Integer nbOfContacts = 0;

        for (MicrosoftAccount microsoftAccount : user.getMicrosoftAccounts()) {

            Integer nb = getNumberOfPeople(microsoftAccount);

            nbOfContacts += nb;

        }

        return nbOfContacts;

    }

    /**
     * Get every messages for a Microsoft account.
     * @param microsoftAccount Microsoft Account
     * @return List of message
     * @throws IOException Exception
     */
    //TODO cha by Djer |POO| Utilise List plutot que ArrayList
    private ArrayList<Message> getMessages(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {

            getLogger().warn("MicrosoftAccount is null");

            return new ArrayList<Message>();

        }

        String email = microsoftAccount.getEmail();
        //TODO cha by Djer |IDE| Ton IDE te dit que ca n'est pas utiliser. Bug ? A supprimer ?
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
          //TODO cha by Djer |Log4J| Contextualise tes messages (" for accountName : " + microsoftAccount.getAccountName() + " and userKey : " + microsoftAccount.getOwner().getUserKey())
            getLogger().warn("MicrosoftAccount information are empty or null");

            return null;

        }

        String folder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = Constant.NUMBER_OF_MAILS;

        TokenResponse newTokens = getToken(microsoftAccount);

        OutlookGetService outlookService = GetOutlookService.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<Message>> resp = outlookService.getMessages(folder, sort, properties, maxResults)
                .execute();

        PagedResult<Message> microsoftg = resp.body();

        return microsoftg.toArrayList();

    }

    /**
     * Get every messages for a DaP User.
     * @param user DaP User.
     * @return List of message
     * @throws IOException Exception
     */
    public final ArrayList<Inbox> getMessages(final AppUser user) throws IOException {

        if (user == null) {
            return new ArrayList<Inbox>();
        }

        if (user.getMicrosoftAccounts().size() == 0) {

            getLogger().warn("Current user " + user.getId() + " haven't any MicrosoftAccount");

            return new ArrayList<Inbox>();

        }

        ArrayList<Inbox> inboxs = new ArrayList<Inbox>();

        for (MicrosoftAccount acc : user.getMicrosoftAccounts()) {

            ArrayList<Message> messages = getMessages(acc);

            Inbox inb = new Inbox();
            inb.setAccount(acc);
            inb.setMessages(messages);

            inboxs.add(inb);

        }

        return inboxs;

    }
}