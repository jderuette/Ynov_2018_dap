package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.contract.OutlookApiService;
import fr.ynov.dap.data.OutlookFolder;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.exception.NoConfigurationException;

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

        TokenResponse newTokens = MicrosoftAPIService.ensureTokens(tokens, tenantId);

        OutlookApiService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

        OutlookFolder inboxFolder = outlookService.getFolder(INBOX_FOLDER_NAME).execute().body();

        if (inboxFolder == null) {
            return 0;
        }

        return inboxFolder.getUnreadItemCount();

    }

}
