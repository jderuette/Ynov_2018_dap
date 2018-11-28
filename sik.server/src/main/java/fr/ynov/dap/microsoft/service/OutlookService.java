package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import fr.ynov.dap.Constants;
import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.microsoft.builder.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.contract.OutlookApiService;
import fr.ynov.dap.microsoft.model.Message;
import fr.ynov.dap.microsoft.model.OutlookContact;
import fr.ynov.dap.microsoft.model.OutlookEvent;
import fr.ynov.dap.microsoft.model.OutlookFolder;
import fr.ynov.dap.microsoft.model.PagedResult;
import fr.ynov.dap.microsoft.model.TokenResponse;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.microsoft.Inbox;
import fr.ynov.dap.model.microsoft.MicrosoftAccount;
import fr.ynov.dap.model.microsoft.MicrosoftCalendarEvent;
import fr.ynov.dap.utils.StrUtils;
import retrofit2.Response;

/**
 * Service to manage outlook API.
 * @author Kévin Sibué
 *
 */
@Service
public class OutlookService extends OutlookAPIService {

    /**
     * Default name for Inbox folder.
     */
    public static final String INBOX_FOLDER_NAME = "inbox";

    /**
     * Default error message when a microsoft account instance is null.
     */
    private static final String ERROR_MICROSOFT_ACCOUNT_NULL = "MicrosoftAccount is null";

    /**
     * Default error message when microsoft information are empty or null.
     */
    private static final String ERROR_MICROSOFT_INFO_EMPTY = "MicrosoftAccount information are empty or null";

    /**
     * Get number of unread email for a user.
     * @param msAcc User's microsoft account
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when configuration is missing
     */
    private Integer getNbUnreadEmails(final MicrosoftAccount msAcc)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        if (msAcc == null) {

            getLogger().warn(ERROR_MICROSOFT_ACCOUNT_NULL);

            return 0;

        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {

            getLogger().warn(ERROR_MICROSOFT_INFO_EMPTY);

            return 0;

        }

        TokenResponse newTokens = getToken(msAcc);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        OutlookFolder inboxFolder = outlookService.getFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {

            getLogger().warn("Graph API respond with empty or malformed JSON");

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
    public final Integer getNbUnreadEmails(final AppUser user)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        if (user.getMicrosoftAccounts().size() == 0) {

            getLogger().warn("No MicrosoftAccount found for this DaP user");

            return 0;

        }

        Integer numberOfUnreadMails = 0;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

            numberOfUnreadMails += getNbUnreadEmails(account);

        }

        return numberOfUnreadMails;

    }

    /**
     * Get next event of a specific Microsoft account.
     * @param msAcc User's microsoft account
     * @return Next event of the current Microsoft account
     * @throws IOException Exception
     */
    private MicrosoftCalendarEvent getNextEvent(final MicrosoftAccount msAcc) throws IOException {

        if (msAcc == null) {

            getLogger().warn(ERROR_MICROSOFT_ACCOUNT_NULL);

            return null;

        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {

            getLogger().warn(ERROR_MICROSOFT_INFO_EMPTY);

            return null;

        }

        TokenResponse newTokens = getToken(msAcc);

        String filter = "start/dateTime ge '" + Instant.now().toString() + "'";
        String sort = "start/dateTime ASC";
        String properties = "Organizer,subject,start,end,Attendees";
        Integer maxResults = 1;

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

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

    /**
     * Get next event of a specific Microsoft account.
     * @param user Current Dap User to use
     * @return Next event of the current Microsoft account
     * @throws IOException Exception
     */
    public MicrosoftCalendarEvent getNextEvent(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {

            getLogger().warn("Current user " + user.getId() + " haven't any MicrosoftAccount");

            return null;

        }

        ArrayList<MicrosoftCalendarEvent> events = new ArrayList<>();

        for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

            MicrosoftCalendarEvent evnt = getNextEvent(msAcc);

            if (evnt != null) {
                events.add(evnt);
            }

        }

        if (events.size() == 0) {

            getLogger().warn("No next event found for current user : " + user.getId());

            return null;

        }

        Collections.sort(events, new SortByNearest());

        MicrosoftCalendarEvent evnt = events.get(0);

        return evnt;

    }

    /**
     * Get number of contacts for a specific user.
     * @param msAcc User's microsoft account
     * @return Number of contact for current Microsoft account
     * @throws IOException Exception
     */
    private Integer getNumberOfContacts(final MicrosoftAccount msAcc) throws IOException {

        if (msAcc == null) {

            getLogger().warn(ERROR_MICROSOFT_ACCOUNT_NULL);

            return 0;

        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {

            getLogger().warn(ERROR_MICROSOFT_INFO_EMPTY);

            return null;

        }

        String sort = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        TokenResponse newTokens = getToken(msAcc);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<OutlookContact>> response = outlookService.getContacts(sort, properties).execute();

        PagedResult<OutlookContact> contacts = response.body();

        if (contacts != null && contacts.getValue() != null) {
            return contacts.getValue().length;
        }

        getLogger().warn("Value return by Graph API isn't valid for microsoft account : " + msAcc.getId());

        return 0;

    }

    /**
     * Get number of contacts for every Microsoft account of a Dap User.
     * @param user Dap User to use
     * @return Number of contacts
     * @throws IOException Exception
     */
    public final Integer getNumberOfContacts(final AppUser user) throws IOException {

        if (user.getMicrosoftAccounts().size() == 0) {

            getLogger().warn("Current user " + user.getId() + " haven't any MicrosoftAccount");

            return 0;

        }

        Integer nbOfContacts = 0;

        for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

            Integer nb = getNumberOfContacts(msAcc);

            nbOfContacts += nb;

        }

        return nbOfContacts;

    }

    /**
     * Get every messages for a Microsoft account.
     * @param msAcc Microsoft Account
     * @return List of message
     * @throws IOException Exception
     */
    private ArrayList<Message> getMessages(final MicrosoftAccount msAcc) throws IOException {

        if (msAcc == null) {

            getLogger().warn(ERROR_MICROSOFT_ACCOUNT_NULL);

            return new ArrayList<Message>();

        }

        String email = msAcc.getEmail();
        String tenantId = msAcc.getTenantId();
        TokenResponse tokens = msAcc.getToken();

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {

            getLogger().warn(ERROR_MICROSOFT_INFO_EMPTY);

            return null;

        }

        String folder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = Constants.NUMBER_OF_MAILS;

        TokenResponse newTokens = getToken(msAcc);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<Message>> resp = outlookService.getMessages(folder, sort, properties, maxResults)
                .execute();

        PagedResult<Message> msg = resp.body();

        return msg.toArrayList();

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
