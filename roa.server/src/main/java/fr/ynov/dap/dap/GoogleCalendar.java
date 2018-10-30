package fr.ynov.dap.dap;

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

/**
 * Service de gestion des évènements de calendrier avec l'API google.
 * @author alex
 */
@Service
public class GoogleCalendar {
    /**
     * Récupération de l'objet config par autowire.
     */
    @Autowired
    private Config config;
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
    public String getNextEvent(final String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Calendar service = new Calendar.Builder(httpTransport, jsonFactory,
                connexionGoogle.getCredentials(httpTransport, user)).setApplicationName(config.getApplicationName())
                        .build();
        // récupère le prochain évènement du calendrier
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            // TODO roa by Djer susout inutile sur un "serveur". A la limite un LOG.debug()
            System.out.println("No upcoming events found.");
            //TODO roa by Djer "contextualise" test Logs quand possible ("pour ltuilisateur xxxx")
            LogManager.getLogger().info("Aucun évènement calendrier trouvé");
            // TODO roa by Djer susout inutile sur un "serveur". A la limite un LOG.debug()
            return "No upcoming events found.";
        } else {
            System.out.println("Upcoming events");
            //TODO roa by Djer Evite les logs en "2 parties", dans un contexte multi-utilisateur ca devient "confus"
            LogManager.getLogger().info("prochain évènement:");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
                LogManager.getLogger().info(event.getSummary());
                // TODO roa by Djer idéalement renvoie un Objet, car on est dans un service,
                // laisse le client (ou le controller) décider de la representation
                return event.toString();
            }
        }
        // FIXME roa by Djer évite les "multiple return" dans un méthode. Utilise une
        // variable pour stocker le résultat et renvoi ce résultat à la fin
        return "";
    }
}
