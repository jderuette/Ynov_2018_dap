package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;

/**
 * Manage Google GMAIL Service.
 * @author thibault
 *
 */
@Service
public class GMAILService extends GoogleService {

    /**
     * Logger for the class.
     */
    //TODO but by Djer Logger generalement en static (pas la peine d'en avoir un par instance)
    // final (pseudo-référence non modifiable)
    private Logger logger = LogManager.getLogger();

    /**
     * Connect to google Calendar service.
     * @param userId ID of user (associate token)
     * @return calendar service connected
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Gmail getService(final String userId) throws IOException, GeneralSecurityException {
        this.logger.info("Generate service GMAIL for user '" + userId + "'");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, this.getJsonFactory(), this.getCredentials(userId))
                .setApplicationName(this.getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * Get number of email unread.
     * @param userId id of google user
     * @return number of email unread
     * @throws IOException google server response error HTTP
     */
    public int getUnreadEmailsNumber(@RequestParam("userId") final String userId) throws IOException {
        int numberUnread = 0;
        try {
            Gmail service = this.getService(userId);
            Gmail.Users.Messages.List requestMessages = service.users().messages().list("me")
                    .setQ("category:primary is:unread");

            do {
                ListMessagesResponse response = requestMessages.execute();
                if (response.getMessages() != null) {
                    numberUnread += response.getMessages().size();
                }
                requestMessages.setPageToken(response.getNextPageToken());
            } while (requestMessages.getPageToken() != null);
        } catch (GeneralSecurityException e) {
            this.logger.error("Error when fetching email for user '" + userId + "'.", e);
        }

        return numberUnread;
    }
}
