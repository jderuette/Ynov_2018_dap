package fr.ynov.dap.google;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;

/**
 * Calendar service.
 * @author MBILLEMAZ
 *
 */

@Service
public final class CalendarService extends CommonGoogleService {

    /**
     * Logger.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * Private constructor.
     */
    public CalendarService() {
        super();
    }

    /**
     * get calender service.
     * @param userKey applicative user
     * @return calendar service
     * @throws Exception if user not found
     */

    public Calendar getService(final String userKey) throws Exception {
        LOGGER.info("Récupération du service Calendar...");
        return new Calendar.Builder(getHttpTransport(), JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName()).build();

    }

    /**
     * get next event for user.
     * @param user applicative user
     * @param gUser google user
     * @return next event
     * @throws Exception if user not found
     */
    public Event getNextEvent(final AppUser user, final String gUser) throws Exception {
        List<GoogleAccount> accountNames = user.getGoogleAccount();
        Events listEvent = new Events();
        for (int i = 0; i < accountNames.size(); i++) {
            Calendar service = getService(accountNames.get(i).getName());
            DateTime now = new DateTime(System.currentTimeMillis());
            Events events = service.events().list("primary").setOrderBy("startTime").setMaxResults(1).setTimeMin(now)
                    .setSingleEvents(true).execute();
            listEvent.putAll(events);
        }
        List<Event> events = listEvent.getItems();
        if (events.size() == 0) {
            return null;
        }

        Event nextEvent = events.get(0);
        for (int i = 1; i < events.size(); i++) {
            Event e = events.get(i);
            if (e.getStart().getDateTime().getValue() < nextEvent.getStart().getDateTime().getValue()) {
                nextEvent = e;
            }
        }

        return nextEvent;
    }

}
