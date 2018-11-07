package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pierre
 */
@RestController
public class CalendarService extends GoogleService {
    /**
     * Instantiate Logger.
     */
    private Logger log = LogManager.getLogger("GMailService");

    /**
     * Get flow in which an end-user authorize the application to access data.
     *
     * @param userId : userKey
     * @return Calendar
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    private Calendar getService(final String userId) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            //TODO plp by Djer Catch uniquement ce dont tu as besoin. Quite à faire un "multi-catch"
        } catch (Exception e) {
            //TODO plp by Djer Utilise le deuxième argument pour indiquer la cause (l'exception) et laisse Log4J gèrer
            log.error("Error when trying to get Service : " + e.toString());
            throw e;
        }

        return new Calendar.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName).build();
    }

    /**
     * Get the next event in calendar.
     *
     * @param userId : userKey
     * @return Map : accessRole, subject, start, finish
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    public final Map<String, String> getNextEvent(final String userId) throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events firstEvent = getService(userId).events().list("primary")
                .setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();

        Event itemFirst = firstEvent.getItems().get(0);
        Map<String, String> response = new HashMap<>();
        if (itemFirst.isEmpty()) {
            response.put("Subject", "No incomming event");
            //TODO plp by Djer Evite les multiple return dans une même méthode.
            return response;
        }
        response.put("accessRole", itemFirst.getStatus());
        //TODO plp by Djer Il y a un S majuscule en cas de "no Event", risqué.
        response.put("subject", itemFirst.getSummary());
        try {
            //TODO plp by Djer tu pourrais faire un test "if null == itemFirst.getStart().getDateTime()" plutot qu'un try catch "hasardeux"
            response.put("start", itemFirst.getStart().getDateTime().toString());
            response.put("finish", itemFirst.getEnd().getDateTime().toString());
          //TODO plp by Djer Catch uniquement ce dont tu as besoin. Quite à faire un "multi-catch"
        } catch (Exception e) {
            response.put("start", itemFirst.getStart().getDate().toString());
            response.put("finish", itemFirst.getEnd().getDate().toString());
        }
        return response;
    }
}
