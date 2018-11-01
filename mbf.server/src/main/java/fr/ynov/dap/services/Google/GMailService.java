package fr.ynov.dap.services.Google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * The GMailService is the service that communicates with the GMail API.
 */
@Service
public class GMailService extends GoogleService {

    /**
     * The logger of the GMailService class.
     */
    //TODO mbf by Djer Utilise le nom, qualifié, de la classe comme category.
    private Logger logger  = Logger.getLogger("GMailService");

    /**
     * This method returns the Gmail service that allows the app to access Gmail mailboxes including sending user email.
     * @param userKey This is the userKey of the currently authenticated user.
     * @return The Gmail service which allows the app to access Gmail mailboxes including sending user email.
     * @throws GeneralSecurityException The GMailService can throw an GeneralSecurityException.
     * @throws IOException The GMailService can throw an IOException.
     */
    private Gmail getService(final String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfiguration().getApplicationName())
                .build();
    }

    /**
     * This returns the response of the /email/nbUnread client's request.
     * @param userKey This is the userKey of the currently authenticated user.
     * @return It returns the response of the request.
     */
    //TODO mbf by Djer Google renvoie un Object de type "Label", ca serait plus prepre de l'utiliser !
    public final Map<String, Object> getUnreadEmail (final String userKey) {
        try {
            return getService(userKey).users().labels().get("me", "INBOX").execute();
        } catch (IOException | GeneralSecurityException e) {
            //TODO mbf by Djer cf mes remarques dans ton client
            logger.finer(e.getMessage());
        }
        return null;
    }
}
