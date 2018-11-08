package fr.ynov.dap.services;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.exceptions.ServiceException;

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
     * AppUserRepository.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * @param userKey user key for authentication.
     * @return Calendar.
     * @throws ServiceException exception
     * @throws Exception exception
     */
    public Calendar getService(final String userKey) throws ServiceException {
        log.info("getCalendarService called with userKey=" + userKey);
        NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                    .setApplicationName(getConfig().getApplicationName())
                    .build();
            return service;
        } catch (GeneralSecurityException e) {
            log.error(e);
            throw new ServiceException("can't get the Calendar service. Error: " + e.getMessage(), e);
        } catch (IOException e) {
            log.error(e);
            throw new ServiceException("can't get the Calendar service. Error: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException("can't get the Calendar service. Error: " + e.getMessage(), e);
        }
    }

    /**
     * Get the next event from now.
     * @param userKey used for authentication.
     * @return near event from now
     * @throws Exception exception
     */
    public Event getNextEvent(final String userKey) throws Exception {
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
        if (items.isEmpty()) {
            return null;
        }

        return items.get(0);
    }

    /**
     * Get next event for all GoogleAccount of a UserApp.
     * @param userKey userKey.
     * @return next Event.
     * @throws Exception exception.
     */
    public Event getNextEventForAllAccount(final String userKey) throws Exception {
        AppUser appUser = repository.findByUserKey(userKey);
        List<String> names = appUser.getGoogleAccountNames();
        List<Event> events = new ArrayList<Event>();

        for (String name : names) {
            events.add(getNextEvent(name));
        }

        if (events.isEmpty()) {
            return null;
        }

        Event nextEvent = events.get(0);

        for (Event event : events) {
            if (nextEvent.getStart().getDateTime().getValue() > event.getStart().getDateTime().getValue()) {
                nextEvent = event;
            }
        }

        return nextEvent;
    }
}
