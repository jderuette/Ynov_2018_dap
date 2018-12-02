package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import fr.ynov.dap.Constant;
import fr.ynov.dap.comparateur.Sorter;
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

    public static final String INBOX_FOLDER_NAME = "inbox";

    private Integer getNbUnreadMails(final MicrosoftAccount microsoftAccount)
            throws IOException, GeneralSecurityException {

        if (microsoftAccount == null) {
            getLogger().warn("MicrosoftAccount is null");
            return 0;
        }

        String email = microsoftAccount.getEmail();
        String tenantId = microsoftAccount.getTenantId();
        //TODO bal by Djer |POO| Ton "Token" dans la classe MicrosoftAccount est un oneToOne (ce qui est bien) donc pas de "s" à ta variable c'est ambigüe.
        TokenResponse tokens = microsoftAccount.getToken();

        if (tenantId.isEmpty() || email.isEmpty() || tokens == null) {
            //TODO bal by Djer |Log4J| Il faut contextualiser les logs (" for Microsoft accountName : " + microsoftAccount)");
            getLogger().warn("MicrosoftAccount information are empty or null");
            return 0;

        }

        //TODO bal by Djer |POO| Le nom de cette méthode laisse croire qu'un nouveau token est créé à chaque fois, ce qui n'est pas vrai (heureusement !)
        TokenResponse newTokens = getToken(microsoftAccount);
        OutlookGetService outlookService = GetOutlookService.getOutlookService(newTokens.getAccessToken(), email);
        OutlookFolder inboxFolder = outlookService.getOutlookFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {
            //TODO bal by Djer |Log4J| Il faut contextualiser les logs (" for Microsoft accountName : " + microsoftAccount + " while searching for folder named : " + INBOX_FOLDER_NAME)");
            getLogger().warn("Graph API respond with empty or malformed JSON");
            return 0;

        }

        return inboxFolder.getUnreadItemCount();

    }

    //TODO bal by Djer |JavaDoc| Javadoc ?
    public final Integer getNbUnreadMails(final AppUser user) throws IOException, GeneralSecurityException {

        if (user.getMicrosoftAccounts().size() == 0) {
            getLogger().warn("No MicrosoftAccount found for this DaP user");
            return 0;
        }
        Integer numberOfUnreadMails = 0;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

            numberOfUnreadMails += getNbUnreadMails(account);

        }

        return numberOfUnreadMails;

    }

    //TODO bal by Djer |POO| Une grosse partie de ce code est du "copier/colelr" de la méthode getNbUnreadMails. Peut être factorisé.
    private MicrosoftCalendarEvent getNextEvent(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {
            getLogger().warn("MicrosoftAccount is null");
            return null;

        }

        String email = microsoftAccount.getEmail();
        //TODO bal by Djer |IDE| Ton IDE t'indique que cette variable n'est pas utilsée. Bug ? A supprimer ? 
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
            //TODO bal by Djer |Log4J| Il faut contextualiser les logs (" for Microsoft accountName : " + microsoftAccount)");
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

    //TODO bal by Djer |JavaDoc| Javadoc ?
    public MicrosoftCalendarEvent getNextEvent(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {
            //TODO bal by Djer |Log4J| Le "id" est un identifiant "interne", ajoute en plus le "userKey" qui est un identifiant "public"
            getLogger().warn("Current user " + user.getId() + " haven't any MicrosoftAccount");
            return null;

        }

        ArrayList<MicrosoftCalendarEvent> events = new ArrayList<>();
        for (MicrosoftAccount microsoftAccount : user.getMicrosoftAccounts()) {
            MicrosoftCalendarEvent evnt = getNextEvent(microsoftAccount);

            if (evnt != null) {
                events.add(evnt);
            }
        }

        if (events.size() == 0) {
            //TODO bal by Djer |Log4J| Ce cas peut se produire de façon "normal" (si je n'ai que des comtpes Google), un level Info serait plus approprié.
            getLogger().warn("No next event found for current user : " + user.getId());
            return null;

        }

        Collections.sort(events, new Sorter());
        MicrosoftCalendarEvent evnt = events.get(0);
        return evnt;

    }

    private Integer getNumberOfPeople(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {
            getLogger().warn("MicrosoftAccount is null");
            return 0;

        }

        String email = microsoftAccount.getEmail();
      //TODO bal by Djer |IDE| Ton IDE t'indique que cette variable n'est pas utilsée. Bug ? A supprimer ?
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
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
            //TODO bal by Djer |POO| Evite les multiples return. Dans tes autres méthodes "simillaires" tu utilisais des "return au milieu" pour les cas d'erreur ce qui est pas top mais "acceptable". Là tu change de phylosophie !
            return contacts.getValue().length;
        }

        getLogger().warn("Value return by Graph API isn't valid for microsoft account : " + microsoftAccount.getId());
        return 0;

    }

    //TODO bal by Djer |JavaDoc| Javadoc ?
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

    private ArrayList<Message> getMessages(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {
            getLogger().warn("MicrosoftAccount is null");
            return new ArrayList<Message>();

        }

        String email = microsoftAccount.getEmail();
      //TODO bal by Djer |IDE| Ton IDE t'indique que cette variable n'est pas utilsée. Bug ? A supprimer ?
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
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

    //TODO bal by Djer |JavaDoc| Javadoc ?
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