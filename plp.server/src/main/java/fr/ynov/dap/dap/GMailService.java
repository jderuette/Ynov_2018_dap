package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    //TODO plp by Djer Devrait être static final.
    //TODO plp by Djer SI tu souhaite préciser la category, utilise le nom, qualifié, de la classe. Ou laisse Log4J le faire (ne met pas de paramètres)
    private Logger log = LogManager.getLogger("GMailService");

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
            //TODO plp by Djer Utilise le deuxième argument pour indiquer la cause (l'exception) et laisse Log4J gèrer
            //tODO plp by Djer Contextualise ton message ("for user : " + userId).
            log.error("Error when trying to get Service : " + e.toString());
            throw e;
        }

        return new Gmail.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName).build();
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
        //FIXME labels().get(xxxx) doit contenir le Google User Id ("me par defaut").
        Label label = getService(userId).users().labels().get(userId, "UNREAD").execute();
        Map<String, Integer> response = new HashMap<>();
        response.put("Unread", label.getMessagesUnread());
        return response;
    }

}
