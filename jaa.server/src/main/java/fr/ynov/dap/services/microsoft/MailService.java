package fr.ynov.dap.services.microsoft;

import java.io.IOException;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Mail Service.
 *
 */
@Service
public class MailService extends MicrosoftService {
    /**
     * Constructor.
     * @param userKey You have to provide the userKey of the AppUser to use.
     */
    public MailService(final String userKey) {
        super(userKey);
    }

    /**
     * Get Mails of a Microsoft Account.
     * @param account Specify the Microsoft Account.
     * @return ResultPages that contains the mails (messages) of the specified Microsoft Account.
     * @throws ServiceException exception of this service.
     */
    public PagedResult<Message> getMails(final MicrosoftAccount account) throws ServiceException {
        getLog().info("Try to get microsoft mails with accountName=" + account.getAccountName());

        TokenResponse tokens = getTokens(account);
        String email = getEmail(account);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String messageFolder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = 10;

        try {
            PagedResult<Message> messages = outlookService.getMessages(messageFolder, sort, properties, maxResults)
                .execute()
                .body();
            return messages;
        } catch (IOException ioe) {
            getLog().error("An occured while trying to get microsoft mails from the API.", ioe);
            throw new ServiceException("Get Microsoft mails failed.", ioe);
        }
    }

}
