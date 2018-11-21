package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;

import org.springframework.stereotype.Service;

import fr.ynov.dap.contract.OutlookApiService;
import fr.ynov.dap.data.OutlookFolder;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.exception.NoConfigurationException;
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
    public Integer getNbUnreadEmails(final String tenantId, final String email, final TokenResponse tokens)
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

    public MicrosoftCalendarEvent getNextEvent(final String tenantId, final String email, final TokenResponse tokens)
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

}
