package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pierre
 */
@RestController
public class CalendarService extends GoogleService {
    /**
     * Instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(CalendarService.class);
    /**
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;

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
        } catch (IOException | GeneralSecurityException e) {
            log.error("Error when trying to get Service for user : " + userId, e);
            throw e;
        }

        return new Calendar.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

    /**
     * Get the next event in calendar.
     *
     * @param userKey : userKey
     * @return Map : accessRole, subject, start, finish
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    public final Event getNextEvent(final String userKey) throws IOException, GeneralSecurityException {
        AppUser appUser = userRepository.findByName(userKey);

        List<Event> events = new ArrayList<>();
        Event nextEvent = new Event();
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        if (appUser.getGoogleAccount() == null) {
            return nextEvent;
        }

        for (GoogleAccount googleAccount : appUser.getGoogleAccount()) {
            DateTime now = new DateTime(System.currentTimeMillis());
            Events firstEvent = getService(googleAccount.getName()).events().list("primary")
                    .setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                    .setSingleEvents(true).execute();

            Event itemFirst = firstEvent.getItems().get(0);
            if (!itemFirst.isEmpty()) {
                if (nextEvent.isEmpty()) {
                    nextEvent = itemFirst;
                }
                else {
                    Date itemStart;
                    Date next;
                    try {
                        if (nextEvent.getStart().getDate() == null) {
                            next = format.parse(nextEvent.getStart().getDateTime().toString());
                        }
                        else {
                            next = format.parse(nextEvent.getStart().getDate().toString());
                        }

                        if (itemFirst.getStart().getDate() == null) {
                             itemStart = format.parse(itemFirst.getStart().getDateTime().toString());
                        } else {
                            itemStart = format.parse(itemFirst.getStart().getDate().toString());
                        }

                        if (itemStart.before(next)) {
                            nextEvent = itemFirst;
                        }
                    }
                    catch (Exception e) {
                        log.error("Failed to parse the event date fur user : " + googleAccount.getName(), e);
                    }


                }
            }

//            Map<String, String> response = new HashMap<>();
//        if (itemFirst.isEmpty()) {
//            response.put("subject", "No incomming event");
//        } else {
//            response.put("accessRole", itemFirst.getStatus());
//            response.put("subject", itemFirst.getSummary());
//            if (itemFirst.getStart().getDate() == null) {
//                response.put("start", itemFirst.getStart().getDateTime().toString());
//                response.put("finish", itemFirst.getEnd().getDateTime().toString());
//            }
//            else {
//                response.put("start", itemFirst.getStart().getDate().toString());
//                response.put("finish", itemFirst.getEnd().getDate().toString());
//            }
//        }
        }

        return nextEvent;
    }
}
