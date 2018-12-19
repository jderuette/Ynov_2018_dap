package fr.ynov.dap.service;

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
public class GoogleCalendarService extends GoogleService {
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
  public GoogleCalendarService()
      throws IOException, InstantiationException, IllegalAccessException {
    super();
  }

  /**
   * Creates a new service if it has never been created
   * else it returns the service instantiated previously.
   * TODO kea by Djer |POO| Tu devrais renomer ce paramètre en "accountName"
   * @param userKey the user that wants to access his account datas
   * @return the Calendar service
   * @throws IOException nothing special
   */
  public Calendar getService(final String userKey) throws IOException {
      //TODO kea by Djer |POO| Attnetion, tu ne peux pas avoir qu'une seul instance du Calendar Google, chaque instance est prpopre à chaque account ! Ce service sera potentiellement utilisé avec différents compte Google, par différents utilisateurs
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
   * TODO kea by DJer |JavaDoc| Cette description n'est pas (plus) juste
   * Uses the Calendar Service to get the 2
   * next events and the date of beginning.
   * @param userKey the user that wants to access his account datas
   * @return an event list
   * @throws IOException nothing special
   */
  //TODO kea by Djer |POO| Le "n" de next devrait être en majuscule
  public Event getnextEvent(final String userKey) throws IOException {
    calendarService = getService(userKey);
    Event evenement = null;
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = calendarService.events().list("primary").setTimeMin(now)
        .setMaxResults(1).setOrderBy("startTime").setSingleEvents(true)
        .execute();
    List<Event> items = events.getItems();
    //TODO kea by Djer |POO| Tu ne récupère pas la valeur de retour de cette méthode !
    //TODO kea by Djer |POO| Tu vas avoir une erreur si la liste est vide (car tune le vérifie que ensuite)
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
  //TODO kea by Djer |POO| Pourquoi public ? Aucune classe n'utilise cette méthode, tu peux la supprimer
  public Logger getLogger() {
    return LOGGER;
  }
}
