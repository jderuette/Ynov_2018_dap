package fr.ynov.dap.dap.google.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * @author David_tepoche
 */
@Service
public class CalendarService extends GoogleBaseService {

    /**
     *
     * @param userId user key
     * @return Gmail service.
     * @throws IOException              throw by the getCredential from the
     *                                  baseService
     * @throws GeneralSecurityException throw by the getCredential from the
     *                                  baseService
     */
    private Calendar getService(final String userId) throws GeneralSecurityException, IOException {
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY,
                getCredential(userId)).setApplicationName(getConfig().getApplicationName()).build();
    }

    @Override
    protected final String getClassName() {

        return CalendarService.class.getName();
    }

    /**
     * return a list of the next event .
     *
     * @param nbrOfResult number of the event wanted
     * @param user        user key
     * @return list of event
     * @throws IOException              throw by the getService
     * @throws GeneralSecurityException throw by the getService
     */
    public List<Event> getLastEvent(final Integer nbrOfResult, final String user)
            throws IOException, GeneralSecurityException {

        DateTime now = new DateTime(System.currentTimeMillis());

        Events events;

        events = getService(user).events().list("primary").setMaxResults(nbrOfResult).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        return items;
    }
}
