package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.stereotype.Service;

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.microsoft.builder.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.contract.OutlookApiService;
import fr.ynov.dap.microsoft.model.OutlookContact;
import fr.ynov.dap.microsoft.model.OutlookEvent;
import fr.ynov.dap.microsoft.model.OutlookFolder;
import fr.ynov.dap.microsoft.model.PagedResult;
import fr.ynov.dap.microsoft.model.TokenResponse;
import fr.ynov.dap.model.AppUser;
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

        TokenResponse newTokens = OutlookAPIService.ensureTokens(tokens, tenantId);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        OutlookFolder inboxFolder = outlookService.getFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {
            return 0;
        }

        return inboxFolder.getUnreadItemCount();

    }

    /**
     * Get number of email for every Microsoft account of a user.
     * @param user Dap User to use
     * @throws NoMicrosoftAccountException Fired when the user passed on params haven't any Microsoft account registered
     * @throws NoConfigurationException Thrown when configuration is missing
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @return Number of unread email
     */
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

    /**
     * Get next event of a specific Microsoft account.
     * @param tenantId Microsoft user tenant id
     * @param email Microsoft account email
     * @param tokens Token of Microsoft account
     * @return Next event of the current Microsoft account
     * @throws IOException Exception
     */
    private MicrosoftCalendarEvent getNextEvent(final String tenantId, final String email, final TokenResponse tokens)
            throws IOException {

        if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email) || tokens == null) {
            return null;
        }

        TokenResponse newTokens = OutlookAPIService.ensureTokens(tokens, tenantId);

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
     * @throws NoNextEventException If there no next event
     * @throws NoMicrosoftAccountException If the user haven't any Microsoft account linked.
     */
    public MicrosoftCalendarEvent getNextEvent(final AppUser user)
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

        TokenResponse newTokens = OutlookAPIService.ensureTokens(tokens, tenantId);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        Response<PagedResult<OutlookContact>> response = outlookService.getContacts(sort, properties).execute();

        PagedResult<OutlookContact> contacts = response.body();

        if (contacts != null && contacts.getValue() != null) {
            return contacts.getValue().length;
        }

        return 0;

    }

    public final Integer getNumberOfContacts(final AppUser user) throws IOException, NoMicrosoftAccountException {

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
