package fr.ynov.dap.web;

import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.CalendarService;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import java.util.ArrayList;
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
   * the appUserRepository manages all database accesses for The AppUser.
   */
  @Autowired
  private AppUserRepository appUserRepo;

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
     * @param appUser the userKey specified in URL
     * @throws IOException nothing special
     */
  @RequestMapping("/events/nextEvent/{appUser}")
  @ResponseBody
  public String eventsToString(final @PathVariable String appUser)
      throws IOException {
    List<GoogleAccount> userGoogleAccounts = appUserRepo
        .findByUserKey(appUser).getGoogleAccounts();
    String stringRes = "Prochains évènements :<br><br>";
    ArrayList<Event> listeEvents = new ArrayList<Event>();
    for (int i = 0; i < userGoogleAccounts.size(); i++) {
      listeEvents.add(calendarService.getnextEvent(userGoogleAccounts.get(i).getGoogleAccountName()));
    }
    for (Event event : listeEvents) {
      stringRes = stringRes + "<br><br>" + calendarService.eventToString(event);
    }
    if (stringRes.equals("")) {
      stringRes = "Aucun évènements à venir !";
    }
    return stringRes;
  }
}
