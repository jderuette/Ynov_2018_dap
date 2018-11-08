package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;

import fr.ynov.dap.contract.OutlookApiService;
import fr.ynov.dap.data.Message;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.exception.NoConfigurationException;

/**
 * Service to manage outlook API.
 * @author Kévin Sibué
 *
 */
public class OutlookService extends MicrosoftAPIService {

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

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String folder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = 0;

        PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
                .body();

        Message[] val = messages.getValue();

        return val.length;

    }

}
