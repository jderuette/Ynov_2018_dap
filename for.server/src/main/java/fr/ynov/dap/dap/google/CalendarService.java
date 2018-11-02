package fr.ynov.dap.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.App;
import fr.ynov.dap.dap.model.CalendarModel;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//TODO for by Djer Séparation service/Controller ?
public class CalendarService {
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
      //TODO for by Djer ton IDE  te dit que ce n'est pas utilser. A supprimer ? A utiliser correctement ? A supprimer ?
    Logger logger = LogManager.getLogger();
    Calendar service = getService(userKey);
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = service.events().list("primary").setOrderBy("startTime")
        .setMaxResults(1).setTimeMin(now)
        .setSingleEvents(true).execute();
    if (events.getItems().size() == 0) {
      //TODO for by Djer Evite les multiples return dans une même méthode
      return null;
    } else {
      CalendarModel event = new CalendarModel(events.getItems().get(0).getSummary(), 
          events.getItems().get(0).getStart(), events.getItems().get(0).getEnd(), 
          events.getItems().get(0).getStatus());
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
            //TODO for by Djer Utilise de l'injection pour acceder à la config !
        App.config.GetJsonFactory(), GoogleService.getCredentials(HTTP_TRANSPORT, userKey))
        .setApplicationName(App.config.GetApplicationName())
        .build();
        
    return service;
  }
}