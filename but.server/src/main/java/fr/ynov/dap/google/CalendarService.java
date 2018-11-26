package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;

/**
 * Manage Google Calendar Service.
 * @author thibault
 *
 */
@Service
public class CalendarService extends GoogleService {
    /**
     * Logger for the class.
     */
    private static Logger logger = LogManager.getLogger();

    /**
     * Connect to Google Calendar Service.
     * @param accountName ID of user (associate token)
     * @param owner Owner of google account
     * @return Calendar google calendar service
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Calendar getService(final String accountName, final AppUser owner)
            throws IOException, GeneralSecurityException {
        logger.info("Generate service Calendar for user '" + accountName + "'");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(
                    httpTransport, this.getJsonFactory(), getCredentials(accountName, owner)
                )
                .setApplicationName(this.getConfig().getApplicationName()).build();

        return service;
    }

    /**
     * Get the next event in calendar of client.
     * @param calendarId calendarID
     * @param accountName ID of user (associate token)
     * @param owner Owner of account
     * @return Event
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Event getNextEvent(final String calendarId, final String accountName, final AppUser owner)
            throws IOException, GeneralSecurityException {
        Event event = null;

        Calendar service = this.getService(accountName, owner);
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list(calendarId).setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (!items.isEmpty()) {
            event = events.getItems().get(0);
        }

        return event;
    }

    /**
     * Get the next event in calendar of User (in all accounts).
     * @param calendarId calendarID
     * @param owner Owner of accounts
     * @return Event
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Event getNextEventOfAllAccounts(final String calendarId, final AppUser owner)
            throws IOException, GeneralSecurityException {
        Event event = null;
        DateTime dateEvent = null;
        for (GoogleAccount gAccount : owner.getGoogleAccounts()) {
            Event eventTemp = getNextEvent(calendarId, gAccount.getAccountName(), owner);
            DateTime dateEventTemp = eventTemp.getStart().getDateTime();
            if (dateEventTemp == null) {
                dateEventTemp = eventTemp.getStart().getDate();
            }
            if (dateEvent == null
                    || dateEventTemp.getValue() < dateEvent.getValue()) {
                event = eventTemp;
                dateEvent = dateEventTemp;
            }
        }

        return event;
    }
}
