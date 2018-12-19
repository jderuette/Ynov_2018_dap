package fr.ynov.dap.web;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.GoogleCalendarService;

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
  private GoogleCalendarService calendarService;

  /**
   * the appUserRepository manages all database accesses for The AppUser.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
   * get the calendarService.
   * @return the calendarService
   */
//TODO kea by Djer |POO| Pourquoi public ? Aucune autre classe n'utilise ce getter, tu peux le supprimer.
  public GoogleCalendarService getCalendarService() {
    return calendarService;
  }

  /**
     * Concatenate all events in the list in one string to display in view.
     * @return string that contains all events in the list
     * @param appUser the userKey specified in URL
     * @throws IOException nothing special
     */
  //TODO kea by Djer |Rest API| Dans une API Rest il vaut mieux renvoyer du JSON (ou du XML) plutot que du HTML. C'est au client de géré l'affichage (éventuellement à la vue). Avec Spring tu peux renvoyer l'objet, il s'occupera de le transformer en JSON lors d'un appel "Rest"
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
    //TODO kea by Djer |API Google| Gestion de "mon" status ?
    return stringRes;
  }
}
