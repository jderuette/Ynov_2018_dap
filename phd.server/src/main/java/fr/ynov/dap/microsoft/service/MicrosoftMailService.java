package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.data.Message;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.PageResultForAllMessage;
import fr.ynov.dap.microsoft.data.PagedResult;

/**
 *
 * @author Dom
 *
 */
@Service
public class MicrosoftMailService {

    /**
    *
    */
    private static final int MAX_CONSTANT = 3;

    /**
    *
    */
    @Autowired
    private AppUserRepository userRepository;

    /**
     *
     * @param accessToken .
     * @param email .
     * @return .
     * @throws IOException .
     */
    public int getNbMail(final String accessToken, final String email) throws IOException {

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, email);

        String folder = "inbox";

        PageResultForAllMessage messagesUnreadAll = outlookService.getAllMessages(folder).execute().body();

        return messagesUnreadAll.getUnreadItemCount();
    }

    /**
     *
     * @param userId .
     * @return .
     * @throws IOException .
     */
  //TODO phd by Djer |Spring| @RequestParam n'est plus utile ici
    public int getNbMailAllAccount(@RequestParam("userId") final String userId) throws IOException {
        AppUser user = userRepository.findByName(userId);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();
        int nbMailAllAccount = 0;
        for (MicrosoftAccountData accountData : accounts) {
            int nbMailAccount = getNbMail(accountData.getTokens().getAccessToken(), accountData.getUserEmail());
            nbMailAllAccount = nbMailAllAccount + nbMailAccount;

        }
        return nbMailAllAccount;
    }

    /**
     *
     * @param userId .
     * @return .
     * @throws IOException .
     */
  //TODO phd by Djer |Spring| @RequestParam n'est plus utile ici
    public List<PagedResult<Message>> getMessagesAllAccount(@RequestParam("userId") final String userId)
            throws IOException {
        AppUser user = userRepository.findByName(userId);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();
        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = MAX_CONSTANT;

        List<PagedResult<Message>> listPageResult = new ArrayList<>();

        for (MicrosoftAccountData accountData : accounts) {
            OutlookService outlookService = OutlookServiceBuilder
                    .getOutlookService(accountData.getTokens().getAccessToken(), accountData.getUserEmail());
            PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
                    .body();
            listPageResult.add(messages);
        }

        return listPageResult;
    }

}
