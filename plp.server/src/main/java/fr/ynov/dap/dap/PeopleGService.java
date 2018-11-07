package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@Service
public class PeopleGService extends GoogleService {
    /**
     * instantiate Logger.
     */
    //TODO plp by Djer Devrait être static final.
    //TODO plp by Djer SI tu souhaite préciser la category, utilise le nom, qualifié, de la classe. Ou laisse Log4J le faire (ne met pas de paramètres)
    private Logger log = LogManager.getLogger("PeopleService");

    /**
     * Get service.
     *
     * @param userId : default user
     * @return Gmail
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    private PeopleService getService(final String userId) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
          //TODO plp by Djer Utilise le deuxième argument pour indiquer la cause (l'exception) et laisse Log4J gèrer
            //tODO plp by Djer Contextualise ton message ("for user : " + userId).
            log.error("Error when trying to get Service : " + e.toString());
            throw e;
        }
        return new PeopleService.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName).build();
    }

    /**
     * Find the number of contact you have.
     *
     * @param userId : default user
     * @return Map : total connection
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    public final Map<String, Integer> getNbContacts(final String userId) throws IOException, GeneralSecurityException {
        ListConnectionsResponse connectionsResponse = getService(userId).people().connections().list("people/me")
                .setPersonFields("names,emailAddresses").execute();
        Map<String, Integer> response = new HashMap<>();
        response.put("Total connection", connectionsResponse.getTotalPeople());
        return response;
    }
}
