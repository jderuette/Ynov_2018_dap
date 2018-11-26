package fr.ynov.dap.web;

import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.CalendarService;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A controller to display datas form the googleCalendar API.
 * @author Antoine
 *
 */
@RestController
public class CalendarController {

  /**
   * The service to access googleCalendar API.
   */
  @Autowired
  private CalendarService calendarService;

  /**
   * get the calendarService.
   * @return the calendarService
   */
  public CalendarService getCalendarService() {
    return calendarService;
  }

  /**
     * Concatenate all events in the list in one string to display in view.
     * @return string that contains all events in the list
     * @param userId the userKey specified in URL
     * @throws IOException nothing special
     */
  @RequestMapping("/events/nextEvents/{userKey}")
  @ResponseBody
  public String eventsToString(final @PathVariable String userId)
      throws IOException {
    List<Event> listeEvents = calendarService.get2nextEvents(userId);
    String stringRes = "Prochains évènements :<br><br>";
    for (Event event : listeEvents) {
      stringRes = stringRes + "<br><br>" + calendarService.eventToString(event);
    }
    if (stringRes.equals("")) {
      stringRes = "Aucun évènements à venir !";
    }
    return stringRes;
  }
}
