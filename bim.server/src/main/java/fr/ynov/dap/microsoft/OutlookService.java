package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.MicrosoftEvent;
import fr.ynov.dap.data.microsoft.Contact;
import fr.ynov.dap.data.microsoft.Folder;
import fr.ynov.dap.data.microsoft.Message;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.PagedResult;
import fr.ynov.dap.data.microsoft.TokenResponse;

/**
 * Outlook service.
 * @author MBILLEMAZ
 *
 */
@Service
public class OutlookService {

    /**
     * 
     * @param user user
     * @return next message
     * @throws IOException if can't access accounts
     */
    public HashMap<String, PagedResult<Message>> getNextMessages(AppUser user) throws IOException {
        List<MicrosoftAccount> accounts = user.getMicrosoftAccounts();

        HashMap<String, PagedResult<Message>> result = new HashMap<String, PagedResult<Message>>();
        for (int i = 0; i < accounts.size(); i++) {
            MicrosoftAccount account = accounts.get(i);
            String tenantId = account.getTenantId();

            TokenResponse token = AuthHelper.ensureTokens(account.getToken(), tenantId);

            OutlookApiRequests outlookService = OutlookRequestsBuilder.getOutlookService(token.getAccessToken());

            // Retrieve messages from the inbox
            String folder = "inbox";
            // Sort by time received in descending order
            String sort = "receivedDateTime DESC";
            // Only return the properties we care about
            String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
            // Return at most 10 messages
            Integer maxResults = 10;

            result.put(account.getName(),
                    outlookService.getMessages(folder, sort, properties, maxResults).execute().body());
        }

        return result;
    }

    /**
     * return nb unread mails.
     * @param user user
     * @return nb unread mail
     * @throws IOException if cannot access account
     */
    public Integer getNbUnread(AppUser user) throws IOException {
        List<MicrosoftAccount> accounts = user.getMicrosoftAccounts();
        int nbUnread = 0;

        for (int i = 0; i < accounts.size(); i++) {
            MicrosoftAccount account = accounts.get(i);
            String tenantId = account.getTenantId();

            TokenResponse token = AuthHelper.ensureTokens(account.getToken(), tenantId);

            OutlookApiRequests outlookService = OutlookRequestsBuilder.getOutlookService(token.getAccessToken());

            // Retrieve messages from the inbox
            String folderName = "inbox";

            Folder folder = outlookService.getFolder(folderName).execute().body();

            nbUnread += folder.getUnreadItemCount();
        }

        return nbUnread;

    }

    /**
     * Return next event in all microsoft account of given user.
     * @param user user
     * @return next event
     * @throws IOException if cannot access account
     */
    public MicrosoftEvent getNextEvent(AppUser user) throws IOException {
        List<MicrosoftAccount> accounts = user.getMicrosoftAccounts();

        MicrosoftEvent nextEvent = null;

        for (int i = 0; i < accounts.size(); i++) {
            MicrosoftAccount account = accounts.get(i);
            String tenantId = account.getTenantId();

            TokenResponse token = AuthHelper.ensureTokens(account.getToken(), tenantId);

            OutlookApiRequests outlookService = OutlookRequestsBuilder.getOutlookService(token.getAccessToken());
            // Sort by start time in ascending order
            String sort = "start/dateTime ASC";
            // Only return the properties we care about
            String properties = "organizer,subject,start,end";
            // Return at most 10 events
            Integer maxResults = 10;
            // Only event after today.
            Date today = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String filter = "start/dateTime ge '" + dateFormat.format(today) + "'";
            PagedResult<MicrosoftEvent> events = outlookService.getEvents(sort, properties, maxResults, filter)
                    .execute().body();
            if (events != null && events.getValue().length > 0) {
                MicrosoftEvent event = null;
                event = events.getValue()[0];

                if (event != null && (nextEvent == null || nextEvent.getStart().compareTo(event.getStart()) >= 1)) {
                    nextEvent = event;
                }
            }

        }

        return nextEvent;
    }

    /**
     * get total of contact in all microsoft account for given user.
     * @param user app user
     * @return nb contact
     * @throws IOException if cannot access account.
     */
    public Integer getNbContact(AppUser user) throws IOException {
        List<MicrosoftAccount> accounts = user.getMicrosoftAccounts();

        Integer contacts = 0;
        for (int i = 0; i < accounts.size(); i++) {
            MicrosoftAccount account = accounts.get(i);
            String tenantId = account.getTenantId();

            TokenResponse token = AuthHelper.ensureTokens(account.getToken(), tenantId);

            OutlookApiRequests outlookService = OutlookRequestsBuilder.getOutlookService(token.getAccessToken());
            PagedResult<Contact> result = outlookService.getNbContacts().execute().body();
            contacts += result.getCount();
            LogManager.getLogger().info(account.getName(), result.getCount().toString());

        }

        return contacts;
    }
}