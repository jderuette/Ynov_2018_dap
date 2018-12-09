package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.web.EventResponse;

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
    //TODO but by Djer |Log4J| Pourquoi pas final ? Tu as besoin de modifier la référence de cette objet durant l'éxécution ?
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
        Calendar service = new Calendar.Builder(httpTransport, this.getJsonFactory(),
                getCredentials(accountName, owner)).setApplicationName(this.getConfig().getApplicationName()).build();

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
            //TODO but by Djer |POO| Comme tu as déja une référence vers "events.getItems()" au travers de "items" tu pourrais utiliser la variable "items". Ici "getItems()" ne **semble** aps faire grand chose donc tu ne prend pas de gros risque, masi tu peux prendre "zéro risque", même si Google fait évoluer son module.
            event = events.getItems().get(0);
        }

        return event;
    }

    /**
     * Get the next event in calendar of User (in all accounts).
     * @param calendarId calendarID
     * @param owner Owner of accounts
     * @return EventResponse last event or null
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public EventResponse getNextEventOfAllAccounts(final String calendarId, final AppUser owner)
            throws IOException, GeneralSecurityException {
        Event event = null;
        //TODO but by Djer |POO| La gestion de "DateTime VS Date" pouvant être null, alourdie cette algo. Tu pourais l'extraire dans une méthode (éventuellement dans une classe utilitaire, ou directement dans cette classe) avec une méthode du type "getEventStartDate(Event event)"
        DateTime dateEvent = null;
        for (GoogleAccount gAccount : owner.getGoogleAccounts()) {
            //TODO but by Djer |POO| Cette signature de méthode laisse croire que "nimporte" quel AppUser peut appeler sur "n'importe quel" AccountName. Je ne pense pas que se soit lecas dans ton code. "GoogleAccount" ayant uen référence vers sont "owner" tu pourrais seulement passer l'instance de googleAcount. Cela clarifirais ton intention.
            Event eventTemp = getNextEvent(calendarId, gAccount.getAccountName(), owner);
            DateTime dateEventTemp = eventTemp.getStart().getDateTime();
            if (dateEventTemp == null) {
                dateEventTemp = eventTemp.getStart().getDate();
            }
            if (dateEvent == null || dateEventTemp.getValue() < dateEvent.getValue()) {
                event = eventTemp;
                dateEvent = dateEventTemp;
            }
        }
        EventResponse result = null;

        if (event != null) {
          //TODO but by Djer |POO| Tu pourrais avoir un méthode "utilitaire" simillaire à "getEventStartDate(Event event)"
            DateTime dateEventEnd = event.getEnd().getDateTime();
            if (dateEventEnd == null) {
                dateEventEnd = event.getEnd().getDate();
            }
            result = new EventResponse();
            result.setStart(new Timestamp(dateEvent.getValue()));
            result.setEnd(new Timestamp(dateEventEnd.getValue()));
            result.setSubject(event.getSummary());
            result.setOrganizer(false);
            if (event.getAttendees() != null) {
                for (EventAttendee attendee : event.getAttendees()) {
                    if (attendee.getSelf()) {
                        if (attendee.getOrganizer() != null) {
                            result.setOrganizer(attendee.getOrganizer());
                        }
                        result.setStatus(attendee.getResponseStatus());
                    }
                }
            } else {
                result.setOrganizer(true);
                result.setStatus(event.getStatus());
            }
        }

        return result;
    }
}
