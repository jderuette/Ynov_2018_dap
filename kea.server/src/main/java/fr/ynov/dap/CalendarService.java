package fr.ynov.dap;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This service has access to the GoogleCalendar API.
 * @author Antoine
 *
 */
@Service
public class CalendarService extends GoogleService {
  /**
   * the googlecalendar service from google.
   */
  private Calendar calendarService = null;
  /**
   * the object used to write logs.
   */
  private static final Logger LOGGER = LogManager
      .getLogger(GoogleService.class);

  /**
   * Get all dependencies needed to create the service.
   * @throws IOException nothing special
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public CalendarService()
      throws IOException, InstantiationException, IllegalAccessException {
    super();
  }

  /**
   * Creates a new service if it has never been created
   * else it returns the service instantiated previously.
   * @param userKey the user that wants to access his account datas
   * @return the Calendar service
   * @throws IOException nothing special
   */
  public Calendar getService(final String userKey) throws IOException {
    if (calendarService == null) {
      calendarService = new Calendar.Builder(
          getCustomConfig().getHttpTransport(), JSON_FACTORY,
          getCredentials(userKey))
              .setApplicationName(getCustomConfig().getApplicationName())
              .build();
    }
    return calendarService;
  }

  /**
   * Uses the Calendar Service to get the 2
   * next events and the date of beginning.
   * @param userKey the user that wants to access his account datas
   * @return an event list
   * @throws IOException nothing special
   */
  public Event getnextEvent(final String userKey) throws IOException {
    calendarService = getService(userKey);
    Event evenement = null;
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = calendarService.events().list("primary").setTimeMin(now)
        .setMaxResults(1).setOrderBy("startTime").setSingleEvents(true)
        .execute();
    List<Event> items = events.getItems();
    eventToString(items.get(0));
    if (items.isEmpty()) {
      LOGGER.info("Aucun evenements trouves");
    } else {
      evenement = items.get(0);
    }
    return evenement;
  }

  /**
   * Uses data from CalendarAPI and adds some text to it.
   * @param evenement the event from googleCalendar Service
   * @return formatted event in string
   */
  public String eventToString(final Event evenement) {
    DateTime eventDateTime = evenement.getStart().getDateTime();
    String eventName = evenement.getSummary();
    if (eventDateTime == null) {
      eventDateTime = evenement.getStart().getDate();
    }
    String stringEvent = "nom évènement:\n" + eventName + " : " + eventDateTime;
    return stringEvent;
  }

  /**
   * get the singleton logger.
   * @return the logger
   */
  public Logger getLogger() {
    return LOGGER;
  }
}
