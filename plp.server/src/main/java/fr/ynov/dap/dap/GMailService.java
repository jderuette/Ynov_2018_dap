package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pierre
 */
@Service
public class GMailService extends GoogleService {
    /**
     * Instantiate logger.
     */
    private static final Logger log = LogManager.getLogger(GMailService.class);

    /**
     * get service.
     * @param userId : default user
     * @return Gmail
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    private Gmail getService(final String userId) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            log.error("Error when trying to get Service for user : " + userId, e);
            throw e;
        }

        return new Gmail.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

    /**
     * Find the email's number who haven't read.
     *
     * @param userId : default user
     * @return Map
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    public final Map<String, Integer> getNbUnreadEmails(final String userId)
            throws IOException, GeneralSecurityException {
        Label label = getService(userId).users().labels().get("me", "UNREAD").execute();
        Map<String, Integer> response = new HashMap<>();
        response.put("Unread", label.getMessagesUnread());
        return response;
    }

}
