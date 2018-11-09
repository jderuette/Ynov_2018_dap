package fr.ynov.dap;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CalendarService extends GoogleService {
  private Calendar calendarService = null;
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
   * @return the Calendar service
   * @throws IOException nothing special
   */
  public Calendar getService(String userKey) throws IOException {
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
   * @return an event list
   * @throws IOException nothing special
   */
  public ArrayList<Event> get2nextEvents(String userKey) throws IOException {
    calendarService = getService(userKey);
    ArrayList<Event> listeEvenements = new ArrayList<Event>();
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = calendarService.events().list("primary").setMaxResults(2)
        .setTimeMin(now).setOrderBy("startTime").setSingleEvents(true)
        .execute();
    List<Event> items = events.getItems();
    if (items.isEmpty()) {
      LOGGER.info("Aucun evenements trouves");
    } else {
      //System.out.println("Evènements à venir :");
      for (Event event : items) {
        listeEvenements.add(event);
      }
    }
    return listeEvenements;
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
}
