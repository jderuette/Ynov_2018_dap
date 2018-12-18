package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.App;
import fr.ynov.dap.dap.model.CalendarModel;
import fr.ynov.dap.dap.repository.AppUserRepository;

@RestController
//TODO for by Djer |SOA| (ancine TO-DO) Séparation service/Controller ?
public class CalendarService {
    //TODO fof by Djer |POO| Pourquoi public ?
	@Autowired
	public AppUserRepository appRepo;
	
/**
 * Retourne le prochain evennement du calendrier
 * @param userKey
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
  @RequestMapping("/nextEvent")
  public String getNextEvent(@RequestParam("userKey") final String userKey)
      throws IOException, GeneralSecurityException {
    Calendar service = getService(userKey);
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = service.events().list("primary").setOrderBy("startTime")
        .setMaxResults(1).setTimeMin(now)
        .setSingleEvents(true).execute();
    if (events.getItems().size() == 0) {
      //TODO for by Djer |POO| (ancien TO-DO) Evite les multiples return dans une même méthode
        //TODO for by DJer |Log4J| Une petite log ?
      return null;
    } else {
      CalendarModel event = new CalendarModel(events.getItems().get(0).getSummary(), 
          events.getItems().get(0).getStart(), events.getItems().get(0).getEnd(), 
          events.getItems().get(0).getStatus());
      //TODO for by Djer |API Google| gestion de "mon" status ? 
      return event.toString();
    }
  }

/**
 * Créer le Service pour accéder aux informations du calendrier
 * @param userKey
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
  private Calendar getService(String userKey) throws IOException, GeneralSecurityException {
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Calendar service = new Calendar.Builder(HTTP_TRANSPORT, 
            //TODO for by Djer |IOC| (ancien TO-DO) Utilise de l'injection pour acceder à la config !
        App.config.GetJsonFactory(), GoogleService.getCredentials(HTTP_TRANSPORT, userKey))
        .setApplicationName(App.config.GetApplicationName())
        .build();
        
    return service;
  }
}