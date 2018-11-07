package fr.ynov.dap.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

/**
 * @author adrij
 *
 */
@Service
public final class GMailService extends GoogleService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    @Autowired
    private AppUserRepository repository;

    /**
     * get gmail service.
     * @param userKey user key for authentication
     * @return Gmail.
     * @throws Exception exception
     */
    public Gmail getService(final String userKey) throws Exception {
        log.info("getGmailService called with userKey=" + userKey);
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * Get number of unread email.
     * @param user The user's email address. The special value me can be used to indicate the authenticated user.
     * @param accountName user key used to authenticate the user
     * @return The number of unread email.
     * @throws Exception exception
     */
    public Integer getUnreadEmailsNumber(final String user, final String accountName) throws Exception {
        log.info("getUnreadEmailNumber called with accountName=" + accountName + "; user=" + user);
        String query = "category:primary is:unread";
        Gmail.Users.Messages.List request = getService(accountName).users().messages().list(user).setQ(query);

        int numberOfUnreadEmail = 0;

        do {
            ListMessagesResponse response = request.execute();
            if (response.getMessages() != null) {
                numberOfUnreadEmail += response.getMessages().size();
            }

            request.setPageToken(response.getNextPageToken());

        } while (request.getPageToken() != null && !request.getPageToken().equals(""));

        return numberOfUnreadEmail;
    }

    public Integer getUnreadEmailsNumberOfAllAccount(final String user, final String userKey) throws Exception {
        AppUser appUser = repository.findByUserKey(userKey);
        List<String> names = appUser.getGoogleAccountNames();
        Integer totalNumberofUnreadMail = 0;

        for (String name : names) {
            totalNumberofUnreadMail += getUnreadEmailsNumber(user, name);
        }

        return totalNumberofUnreadMail;
    }
}
