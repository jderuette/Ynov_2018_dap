package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;

/**
 * Class to manage Gmail API.
 * @author Robin DUDEK
 *
 */
@Service
public class GmailService extends GoogleService {

    @Override
    protected final String getClassName() {
        return GmailService.class.getName();
    }

    public Gmail getService(final String userKey) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(userKey))
                .setApplicationName(config.getApplicationName()).build();
        return service;
    }

    /**
     * Get number of unread email for a user.
     * @param accountName id of the targeted user
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     */
    private Integer getNbUnreadEmails(final String userKey)
            throws IOException, GeneralSecurityException {

        Gmail gmailService = getService(userKey);

        Label inboxLabel = gmailService.users().labels().get("me", "INBOX").execute();

        return inboxLabel.getMessagesUnread();

    }

    public Integer getNbUnreadEmails(AppUser user)
            throws IOException, GeneralSecurityException {

        Integer nbUnreadMail = 0;

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {
            nbUnreadMail += getNbUnreadEmails(gAcc.getAccountName());
        }

        return nbUnreadMail;

    }

}
