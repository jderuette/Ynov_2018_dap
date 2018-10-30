package fr.ynov.dap.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * @author adrij
 *
 */
@Service
public final class CalendarService extends GoogleService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * @param userKey user key for authentication.
     * @return Calendar.
     * @throws Exception exception
     */
    //TODO jaa by Djer ne pas lever "Exception", il faut "corriger" getCredentials(...) puis lever les exception spécifiques. 
    public Calendar getService(final String userKey) throws Exception {
        log.info("getCalendarService called with userKey=" + userKey);
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName())
                .build();
        return service;
    }

    /**
     * Get the next event from now.
     * @param userKey used for authentication.
     * @return near event from now
     * @throws Exception exception
     */
    public List<Event> getNextEvent(final String userKey) throws Exception {
        final Integer maxResults = 1;
        DateTime now = new DateTime(System.currentTimeMillis());
        log.info("getNextEvent called. userKey=" + userKey + "; with maxResults="
                + maxResults + "; DateNow=" + now.toString());
        Events events = getService(userKey).events().list("primary")
                .setMaxResults(maxResults)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        log.info("found event(s)=" + events.toString());
        return items;
    }

}
