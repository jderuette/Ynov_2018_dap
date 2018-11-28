package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.data.Message;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.data.UnreadmailResult;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Mon_PC
 */
@Service
public class MicrosoftMailService {

    /**.
     * propriété maxResult
     */
    private static final Integer MAX_RESULTS = 3;

    /**.
     * repositoryUser is managed by Spring on the loadConfig()
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     *
     * @param accessToken token
     * @param email mail user
     * @return nb mail unread microsoft
     * @throws IOException problème lors de l'éxécution de la fonction
     */
    public int getNbMailMicrosoft(final String accessToken, final String email) throws IOException {

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, email);

        String folderId = "inbox";

        UnreadmailResult messagesUnread = outlookService.getUnreadMessages(folderId).execute().body();

        return messagesUnread.getUnreadItemCount();
    }

    /**
     * @param userKey correspondant à l'userId passé
     * @return nb unread mail for all accounts microsoft
     * @throws IOException problème lors de l'éxécution de la fonction
     */
    public int getNbMailAllAccountMicrosoft(final String userKey) throws IOException {

        AppUser user = repositoryUser.findByName(userKey);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();

        int nbMailAllAccount = 0;
        for (MicrosoftAccountData account : accounts) {
            nbMailAllAccount += getNbMailMicrosoft(account.getToken().getAccessToken(), account.getUserEmail());
        }
        return nbMailAllAccount;
    }

    /**
     * @param userId correspond à l'userId passé
     * @return mails
     * @throws IOException exception levée si problème
     */
    public List<PagedResult<Message>> getContenusMailMicrosoft(final String userId) throws IOException {

        AppUser user = repositoryUser.findByName(userId);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();

        List<PagedResult<Message>> results = new ArrayList<PagedResult<Message>>();
        for (MicrosoftAccountData account : accounts) {
            results.add(getContenuChaqueMailMicrosoft(account.getToken().getAccessToken(), account.getUserEmail()));
        }
        return results;
    }

    /**
     * @param token token passé
     * @param email mail
     * @return message
     * @throws IOException exception levée si problème
     */
    public PagedResult<Message> getContenuChaqueMailMicrosoft(final String token, final String email)
            throws IOException {
        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(token, email);

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";

        PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, MAX_RESULTS).execute()
                .body();

        return messages;
    }
}
