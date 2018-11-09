package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
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

    public PagedResult<Message> getNextMessages(HttpSession session, TokenResponse tokens) throws IOException {

        String tenantId = (String) session.getAttribute("userTenantId");

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        String email = (String) session.getAttribute("userEmail");

        OutlookApiRequests outlookService = OutlookRequestsBuilder.getOutlookService(tokens.getAccessToken());

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = 10;

        return outlookService.getMessages(folder, sort, properties, maxResults).execute().body();
    }

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
}