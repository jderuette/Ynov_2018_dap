package fr.ynov.dap.dap.microsoft;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.microsoft.OutlookAccount;
import fr.ynov.dap.dap.microsoft.models.*;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OutlookService {
    /**
     * Instantiate logger.
     */
    private static final Logger LOG = LogManager.getLogger(OutlookService.class);

    /**
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;

    /**
     *
     * @param userKey :name user
     * @param redirectAttributes : redirect
     * @return return the 10 last mail of each account of one user
     */
    public List<Map> mail(final String userKey, RedirectAttributes redirectAttributes) {

        AppUser appUser = userRepository.findByName(userKey);
        List<Map> listResponse = new ArrayList<>();

        for (OutlookAccount outlookAccount : appUser.getOutlookAccount()) {
            if (outlookAccount.getToken() == null) {
                // No tokens in session, user needs to sign in
                redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            }

            outlookAccount.setToken(AuthHelper.ensureTokens(outlookAccount.getToken(), outlookAccount.getTenantId()));

            IOutlookService ioutlookService = OutlookServiceBuilder.getOutlookService(
                    outlookAccount.getToken().getAccessToken());

            // Retrieve messages from the inbox
            String folder = "inbox";
            // Sort by time received in descending order
            String sort = "receivedDateTime DESC";
            // Only return the properties we care about
            String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
            // Return at most 10 messages
            Integer maxResults = 10;

            try {
                PagedResult<Message> messages = ioutlookService.getMessages(
                        folder, sort, properties, maxResults)
                        .execute().body();
                OutlookFolder outlookFolder = ioutlookService.getFolder("inbox").execute().body();
                Map<String, Object> account = new HashMap<>();
                account.put("name", outlookAccount.getName());
                account.put("messages", messages.getValue());
                account.put("nbEmail", outlookFolder.getTotalItemCount());
                listResponse.add(account);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
                LOG.error("Can't get messages", e);
            }
        }

        return listResponse;
    }

    /**
     *
     * @param userKey : name user
     * @return the number of unread of one user
     */
    public Integer unreadMail(final String userKey) {

        AppUser appUser = userRepository.findByName(userKey);

        Integer nbUnread = 0;
        for (OutlookAccount outlookAccount : appUser.getOutlookAccount()) {
            if (outlookAccount.getToken() == null) {
                LOG.error("No token for unread email");
            }

            outlookAccount.setToken(AuthHelper.ensureTokens(outlookAccount.getToken(), outlookAccount.getTenantId()));

            IOutlookService ioutlookService = OutlookServiceBuilder.getOutlookService(
                    outlookAccount.getToken().getAccessToken());

            try {
                OutlookFolder folder = ioutlookService.getFolder("inbox")
                        .execute().body();
                nbUnread += folder.getUnreadItemCount();
            } catch (IOException e) {
                LOG.error("Can't get messages", e);
            }
        }

        return nbUnread;
    }

    /**
     *
     * @param userKey: name user
     * @return the last event of one user
     */
    public EventMicrosoft events(final String userKey) {
        AppUser appUser = userRepository.findByName(userKey);

        List<EventMicrosoft> listLastEvents = new ArrayList<>();
        EventMicrosoft lastEvent = null;
        for (OutlookAccount outlookAccount : appUser.getOutlookAccount()) {

            if (outlookAccount.getToken() == null) {
                LOG.warn("No token for account Microsoft : " + outlookAccount.getName());
            }

            outlookAccount.setToken(AuthHelper.ensureTokens(outlookAccount.getToken(), outlookAccount.getTenantId()));

            IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(
                    outlookAccount.getToken().getAccessToken());

            // Sort by start time in descending order
            String sort = "start/dateTime ASC";
            // Only return the properties we care about
            String properties = "organizer,subject,start,end";
            // Return at most 1 events
            Integer maxResults = 1;

            try {
                EventMicrosoft event = outlookService.getEvents(
                        sort, properties, maxResults)
                        .execute().body().getValue()[0];
                listLastEvents.add(event);
                if (lastEvent == null) {
                    lastEvent = event;
                } else {
                    if (lastEvent.getStart().getDateTime().after(event.getStart().getDateTime())) {
                        lastEvent = event;
                    }
                }
            } catch (IOException e) {
                LOG.error("Can't get next event of : " + outlookAccount.getName(), e);
            }
        }

        return lastEvent;
    }

    public Integer contacts(final String userKey) {
        AppUser appUser = userRepository.findByName(userKey);

        Integer nbContacts = 0;
        for (OutlookAccount outlookAccount : appUser.getOutlookAccount()) {
            if (outlookAccount.getToken() == null) {
                LOG.warn("No token for account Microsoft : " + outlookAccount.getName());
            }

            outlookAccount.setToken(AuthHelper.ensureTokens(outlookAccount.getToken(), outlookAccount.getTenantId()));

            IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(
                    outlookAccount.getToken().getAccessToken());

            // Sort by given name in ascending order (A-Z)
            String sort = "GivenName ASC";
            // Only return the properties we care about
            String properties = "GivenName,Surname,CompanyName,EmailAddresses";

            try {
                PagedResult<Contact> contacts = outlookService.getContacts(
                        sort, properties)
                        .execute().body();

                if (contacts != null && contacts.getValue() != null) {
                    nbContacts += contacts.getValue().length;
                }
            } catch (IOException e) {
                LOG.error("Can't get contact for account Microsoft : " + outlookAccount.getName(), e);
            }
        }

        return nbContacts;
    }
}
