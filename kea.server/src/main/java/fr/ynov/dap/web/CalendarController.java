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

@RestController
public class CalendarController {

  @Autowired
  CalendarService calendarService;

  /**
     * Concatenate all events in the list in one string to display in view.
     * @return string that contains all events in the list
     * @throws IOException nothing special
     */
  @RequestMapping("/events/nextEvents/{userKey}")
  @ResponseBody
  public String eventsToString(@PathVariable String userKey)
      throws IOException {
    List<Event> listeEvents = calendarService.get2nextEvents(userKey);
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
