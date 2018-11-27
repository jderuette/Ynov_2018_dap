package fr.ynov.dap.services.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Mail Service.
 */
@Service
public class MicrosoftMailService extends MicrosoftService {
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
        Integer maxResults = MAX_RESULTS;

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

    /**
     * Get the number of unread emails.
     * @param account Microsoft Account
     * @return the number of unread emails.
     * @throws ServiceException exception of this service.
     */
    public Integer getUnreadMailsNumber(final MicrosoftAccount account) throws ServiceException {
        getLog().info("Try to get microsoft mails with accountName=" + account.getAccountName());

        TokenResponse tokens = getTokens(account);
        String email = getEmail(account);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String messageFolder = "inbox";
        String sort = "receivedDateTime DESC";
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        Integer maxResults = MAX_RESULTS;
        String filter = "isRead eq false";
        Boolean count = true;

        try {
            PagedResult<Message> messages = outlookService.getMessages(
                    messageFolder, sort, properties, maxResults, filter, count)
                .execute()
                .body();
            return messages.getCount();
        } catch (IOException ioe) {
            getLog().error("An occured while trying to get microsoft mails from the API.", ioe);
            throw new ServiceException("Get Microsoft mails failed.", ioe);
        }
    }

    /**
     * Get the number of unread emails for all Microsoft account of an AppUser account.
     * @param userKey userKey of the AppUser account.
     * @return total number of unread microsoft mail for an AppUser.
     * @throws ServiceException exception
     */
    public Integer getUnreadEmailsNumberOfAllAccount(final String userKey) throws ServiceException {
        AppUser appUser = getRepository().findByUserKey(userKey);
        List<MicrosoftAccount> accounts = appUser.getMicrosoftAccounts();
        Integer totalNumberofUnreadMail = 0;

        for (MicrosoftAccount account : accounts) {
            totalNumberofUnreadMail += getUnreadMailsNumber(account);
        }

        return totalNumberofUnreadMail;
    }

    /**
     * Get first email for each Microsoft account owned by an AppUser.
     * @param userKey user key of the appUser.
     * @return a list for each Microsoft account that contains a list of messages.
     * @throws ServiceException exception.
     */
    public List<List<Message>> getFirstEmails(final String userKey) throws ServiceException {
        AppUser appUser = getRepository().findByUserKey(userKey);
        List<MicrosoftAccount> accounts = appUser.getMicrosoftAccounts();

        List<List<Message>> mailsForMicrosoftAccounts = new ArrayList<List<Message>>();

        for (MicrosoftAccount account : accounts) {
            List<Message> messages = new ArrayList<Message>();
            try {
                PagedResult<Message> emailsPage = getMails(account);
                messages = new ArrayList<>(Arrays.asList(emailsPage.getValue()));
            } catch (ServiceException se) {
                getLog().error("Faild to get microsoft emails", se);
                throw new ServiceException("Failed to get microsoft emails.", se);
            }

            mailsForMicrosoftAccounts.add(messages);
        }

        return mailsForMicrosoftAccounts;
    }

}
