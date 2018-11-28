package fr.ynov.dap.microsoft;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.authentication.AuthHelper;

/**
 * Service to manage mail of microsoft.
 * @author thibault
 *
 */
@Service
public class MicrosoftMailService {

    /**
     * Repository of MicrosoftAccount.
     */
    @Autowired
    private MicrosoftAccountRepository repositoryMicrosoftAccount;

    /**
     * Get emails of one app user (all accounts).
     * @param user the app user.
     * @return all emails.
     * @throws IOException if http erro.
     */
    public Map<MicrosoftAccount, Message[]> getEmails(final AppUser user) throws IOException {
        Map<MicrosoftAccount, Message[]> result = new HashMap<MicrosoftAccount, Message[]>();
        for (MicrosoftAccount mAccount : user.getMicrosoftAccounts()) {

            mAccount = AuthHelper.ensureTokens(mAccount);
            repositoryMicrosoftAccount.save(mAccount);

            OutlookService outlookService = OutlookServiceBuilder
                    .getOutlookService(mAccount.getAccessToken(), mAccount.getEmail());

            // Retrieve messages from the inbox
            String folder = "inbox";
            // Sort by time received in descending order
            String sort = "receivedDateTime DESC";
            // Only return the properties we care about
            String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
            // Return at most 10 messages
            Integer maxResults = null;

            PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults)
                    .execute().body();
            result.put(mAccount, messages.getValue());
        }
        return result;
    }

    /**
     * Get number of unread email.
     * @param user the app user.
     * @return the count of unread email.
     * @throws IOException if http error.
     */
    public int getUnreadEmailCount(final AppUser user) throws IOException {
        int result = 0;
        for (MicrosoftAccount mAccount : user.getMicrosoftAccounts()) {

            mAccount = AuthHelper.ensureTokens(mAccount);
            repositoryMicrosoftAccount.save(mAccount);

            OutlookService outlookService = OutlookServiceBuilder
                    .getOutlookService(mAccount.getAccessToken(), mAccount.getEmail());

            Folder folder = outlookService.getFolder("inbox").execute().body();
            result += folder.getUnreadItemCount();
        }
        return result;
    }
}
