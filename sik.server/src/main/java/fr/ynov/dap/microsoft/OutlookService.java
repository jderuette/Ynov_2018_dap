package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.contract.OutlookApiService;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.OutlookContact;
import fr.ynov.dap.data.OutlookFolder;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.model.MicrosoftCalendarEvent;
import fr.ynov.dap.model.OutlookEvent;
import fr.ynov.dap.utils.StrUtils;
import retrofit2.Response;

/**
 * Service to manage outlook API.
 * @author Kévin Sibué
 *
 */
@Service
public class OutlookService extends MicrosoftAPIService {

    /**
     * Default name for Inbox folder.
     */
    public static final String INBOX_FOLDER_NAME = "inbox";

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
            throws NoConfigurationException, IOException, GeneralSecurityException {

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {
            return 0;
        }

        TokenResponse newTokens = MicrosoftAPIService.ensureTokens(tokens, tenantId);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        OutlookFolder inboxFolder = outlookService.getFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {
            return 0;
        }

        return inboxFolder.getUnreadItemCount();

    }

    public final Integer getNbUnreadEmails(final AppUser user)
            throws NoMicrosoftAccountException, NoConfigurationException, IOException, GeneralSecurityException {

        if (user.getMicrosoftAccounts().size() == 0) {
            throw new NoMicrosoftAccountException();
        }

        Integer numberOfUnreadMails = 0;

        for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

            String email = account.getEmail();
            String tenantId = account.getTenantId();
            TokenResponse tokens = account.getToken();

            numberOfUnreadMails += getNbUnreadEmails(tenantId, email, tokens);

        }

        return numberOfUnreadMails;

    }

    private MicrosoftCalendarEvent getNextEvent(final String tenantId, final String email, final TokenResponse tokens)
            throws IOException {

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {
            return null;
        }

        TokenResponse newTokens = MicrosoftAPIService.ensureTokens(tokens, tenantId);

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

    public MicrosoftCalendarEvent getNextEvent(AppUser user)
            throws NoMicrosoftAccountException, NoNextEventException, IOException {

        if (user.getMicrosoftAccounts().size() == 0) {
            throw new NoMicrosoftAccountException();
        }

        ArrayList<MicrosoftCalendarEvent> events = new ArrayList<>();

        for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

            String email = msAcc.getEmail();
            String tenantId = msAcc.getTenantId();
            TokenResponse tokens = msAcc.getToken();

            MicrosoftCalendarEvent evnt = getNextEvent(tenantId, email, tokens);

            if (evnt != null) {
                events.add(evnt);
            }

        }

        if (events.size() == 0) {
            throw new NoNextEventException();
        }

        Collections.sort(events, new SortByNearest());

        MicrosoftCalendarEvent evnt = events.get(0);

        return evnt;

    }

    private final Integer getNumberOfContacts(final String tenantId, final String email, final TokenResponse tokens)
            throws IOException {

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {
            return null;
        }

        String sort = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        TokenResponse newTokens = MicrosoftAPIService.ensureTokens(tokens, tenantId);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<OutlookContact>> response = outlookService.getContacts(sort, properties).execute();

        PagedResult<OutlookContact> contacts = response.body();

        if (contacts != null && contacts.getValue() != null) {
            return contacts.getValue().length;
        }

        return 0;

    }

    public final Integer getNumberOfContacts(AppUser user) throws IOException, NoMicrosoftAccountException {

        if (user.getMicrosoftAccounts().size() == 0) {
            throw new NoMicrosoftAccountException();
        }

        Integer nbOfContacts = 0;

        for (MicrosoftAccount msAcc : user.getMicrosoftAccounts()) {

            String email = msAcc.getEmail();
            String tenantId = msAcc.getTenantId();
            TokenResponse tokens = msAcc.getToken();

            Integer nb = getNumberOfContacts(tenantId, email, tokens);

            nbOfContacts += nb;

        }

        return nbOfContacts;

    }

}
