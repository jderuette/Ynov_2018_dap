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
        TokenResponse tokens = microsoftAccount.getToken();

        if (tenantId.isEmpty() || email.isEmpty() || tokens == null) {
            getLogger().warn("MicrosoftAccount information are empty or null");
            return 0;

        }

        TokenResponse newTokens = getToken(microsoftAccount);
        OutlookGetService outlookService = GetOutlookService.getOutlookService(newTokens.getAccessToken(), email);
        OutlookFolder inboxFolder = outlookService.getOutlookFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {

            getLogger().warn("Graph API respond with empty or malformed JSON");
            return 0;

        }

        return inboxFolder.getUnreadItemCount();

    }


    public final Integer getNbUnreadMails(final AppUser user)
            throws IOException, GeneralSecurityException {

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

    private MicrosoftCalendarEvent getNextEvent(final MicrosoftAccount microsoftAccount) throws IOException {

        if (microsoftAccount == null) {
            getLogger().warn("MicrosoftAccount is null");
            return null;

        }

        String email = microsoftAccount.getEmail();
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getToken();

        if (email.isEmpty() || tokens == null) {
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

    public MicrosoftCalendarEvent getNextEvent(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {
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
            return contacts.getValue().length;
        }

        getLogger().warn("Value return by Graph API isn't valid for microsoft account : " + microsoftAccount.getId());
        return 0;

    }


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