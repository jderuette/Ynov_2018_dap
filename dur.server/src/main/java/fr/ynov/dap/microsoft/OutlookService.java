package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.microsoft.entity.Contact;
import fr.ynov.dap.microsoft.entity.Event;
import fr.ynov.dap.microsoft.entity.Message;
import fr.ynov.dap.microsoft.entity.OutlookFolder;
import fr.ynov.dap.microsoft.entity.PagedResult;
import fr.ynov.dap.repository.AppUserRepository;
import retrofit2.Response;

@Service
public class OutlookService extends MicrosoftService {

    /**
     * Default name for Inbox folder.
     */
    public static final String INBOX_FOLDER_NAME = "inbox";

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Get number of unread email for a user.
     * @param tenantId Tenant Id
     * @param email Email id
     * @param tokens Tokens
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when configuration is missing
     */
    private Integer getNbUnreadEmails(final String tenantId, final String email, final TokenResponse tokens)
            throws IOException, GeneralSecurityException {

        TokenResponse newTokens = MicrosoftService.ensureTokens(tokens, tenantId);
        OutlookApiCalls outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken());
        OutlookFolder inboxFolder = outlookService.getFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {
            return 0;
        }
        return inboxFolder.getUnreadItemCount();
    }

    public List<PagedResult<Message>> getMailForAllAccounts(String userKey) {

        List<PagedResult<Message>> messages = new ArrayList<PagedResult<Message>>();
        AppUser appUser = appUserRepository.findByUserKey(userKey);
        String folder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = 10;

        for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
            TokenResponse tokens = MicrosoftService.ensureTokens(account.getToken(), account.getTenantId());
            OutlookApiCalls outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
            try {
                PagedResult<Message> emails = outlookService.getMessages(folder, sort, properties, maxResults).execute()
                        .body();
                messages.add(emails);
            } catch (IOException e) {
                //TODO dur by Djer |Log4J| Ajoute du context dans tes messages (" for userKey : " + userKey)
                LOGGER.error("Error when trying get mail for all accounts.", e);
            }
        }
        return messages;
    }

    public final Integer getNbUnreadEmails(final AppUser user)
            throws IOException, GeneralSecurityException {

        Integer numberOfUnreadMails = 0;
        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

            String email = account.getEmail();
            String tenantId = account.getTenantId();
            TokenResponse tokens = account.getToken();

            numberOfUnreadMails += getNbUnreadEmails(tenantId, email, tokens);
        }
        return numberOfUnreadMails;
    }

    private Event getNextEvent(final String tenantId, final String email, final TokenResponse tokens)
            throws IOException {


        TokenResponse newTokens = MicrosoftService.ensureTokens(tokens, tenantId);

        String filter = "start/dateTime ge '" + Instant.now().toString() + "'";
        String sort = "start/dateTime ASC";
        String properties = "Organizer,subject,start,end,Attendees";
        Integer maxResults = 1;

        OutlookApiCalls outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken());

        Response<PagedResult<Event>> response = outlookService.getEvents(sort, filter, properties, maxResults)
                .execute();

        PagedResult<Event> events = response.body();

        if (events == null) {
            return null;
        }

        if (events.getValue() == null) {
            return null;
        }

        if (events.getValue().length == 0) {
            return null;
        }

        Event nextEvent = events.getValue()[0];

        return nextEvent;

    }

    public Event getNextEvent(AppUser user) throws IOException {

        /*if (user.getMicrosoftAccounts().size() == 0) {
            LOGGER.error
        }*/

        ArrayList<Event> events = new ArrayList<>();

        for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

            String email = msAcc.getEmail();
            String tenantId = msAcc.getTenantId();
            TokenResponse tokens = msAcc.getToken();

            Event evnt = getNextEvent(tenantId, email, tokens);

            if (evnt != null) {
                events.add(evnt);
            }
        }

        if (events.isEmpty()) {
          //TODO dur by Djer |Log4J| Ajoute du context dans tes messages (" for user : " + user.getUserKey())
            LOGGER.info("No upcoming events found for this user");
            return null;
        }

        Event nextEvent = events.get(0);

        for (Event event : events) {
            if (nextEvent.getStart().getDateTime().getTime() > event.getStart().getDateTime().getTime()) {
                nextEvent = event;
            }
        }

        //TODO dur by Djer |API Microsoft| Un oublie ? Tu voulais surement renvoyer "nextEvent"
        Event event = events.get(0);

        return event;

    }

    private final Integer getNumberOfContacts(final String tenantId, final String email, final TokenResponse tokens)
            throws IOException {

        String sort = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        TokenResponse newTokens = MicrosoftService.ensureTokens(tokens, tenantId);

        OutlookApiCalls outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken());

        Response<PagedResult<Contact>> response = outlookService.getContacts(sort, properties).execute();

        PagedResult<Contact> contacts = response.body();

        if (contacts != null && contacts.getValue() != null) {
            //TODO dur by Djer |API Microsoft| Quelle est la taille d'une "page de contact" par defaut ? 
            return contacts.getValue().length;
        }

        return 0;
    }

    public final Integer getNumberOfContacts(AppUser user) throws IOException {

        Integer nbOfContacts = 0;

        if (user.getMicrosoftAccounts().size() == 0) {
            //TODO dur by Djer |Log4J| Pas vraiment ue erreur, c'est autorisé de ne pas avoir de contacts !. Un level "Info" serait suffisant
            //TODO dur by DJer |Log4J| Contextualise tes messages
            LOGGER.error("This user don't have microsoft accounts");
        } else {

            for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

                String email = msAcc.getEmail();
                String tenantId = msAcc.getTenantId();
                TokenResponse tokens = msAcc.getToken();

                Integer nb = getNumberOfContacts(tenantId, email, tokens);

                nbOfContacts += nb;
            }
        }


        return nbOfContacts;

    }

}