package fr.ynov.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.Config;
import fr.ynov.dap.web.ConnexionGoogle;

/**
 * Service de gestion des évènements de calendrier avec l'API google.
 * @author alex
 */
@Service
public class GoogleCalendar {
    /**
     * Récupération de l'objet connexionGoogle par autowire.
     */
    @Autowired
    private ConnexionGoogle connexionGoogle;

    /**
     * Récupère le prochain évènement du calendrier google de l'utilisateur connecté
     * et le renvois.
     *
     * @param user userID
     * @throws IOException              exception IO
     * @throws GeneralSecurityException exception de sécurité
     * @return String prochain évènement
     */
    //TODO roa by Djer |SOA| Dans tes services, renvoie de préférences des objets. Laisse au controller (via la Vue) ou au client le travail du formatage. En plus avec Spring si un RestController renvoie un objet, Spring le tranforme automatiquement (en JSON par défaut)
    public String getNextEvent(final String user) throws IOException, GeneralSecurityException {
        String message = "";
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Calendar service = new Calendar.Builder(httpTransport, jsonFactory,
                connexionGoogle.getCredentials(httpTransport, user)).setApplicationName(Config.getApplicationName())
                        .build();
        // récupère le prochain évènement du calendrier
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            //TODO roa by Djer |log4J| Evite de récupérer uen instance du logger lorsque tu souhaites publier un message. Créer un Logger est couteux. La bonne pratique est de créer une consontante "LOG" par classe ayant besoin de logger des messages
            LogManager.getLogger().info("utilisateur " + user + ": Aucun évènement calendrier trouvé");
            message = "No upcoming events found.";
        } else {
            //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
              //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
                System.out.printf("%s (%s)\n", event.getSummary(), start);
                LogManager.getLogger().info("utilisateur " + user + ": " + event.getSummary());
                message = event.toString();
            }
        }
        //TODO roa by Djer |API Google| gestion de "mon" status ?
        return message;
    }
}
