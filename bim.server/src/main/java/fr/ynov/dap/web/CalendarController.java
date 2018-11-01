package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.google.CalendarService;

/**
 * Calendar controller.
 * @author MBILLEMAZ
 *
 */
@RestController
public class CalendarController {

    /**
     * Gmail service.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * get next event.
     * @param user google user
     * @param userKey applicative user
     * @return next event
     * @throws Exception  if user not found
     */
    @RequestMapping("/event/next/{user}")
    public final Event getNextEvent(@PathVariable final String user, @RequestParam("userKey") final String userKey)
            throws Exception {
        //TODO bim by Djer La majorité de ce code devrait être dans le service (calendarService.getNexEvent()).
        //Le travail d'un controller est de traiter les "saisie" utilisateur et de répondre.
        // pas de connaitre le fonctionne de Calendar (ou tout autre processus metier).
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du prochain evenement du calendrier de l'utilisateur {}...", user);
        Calendar service = calendarService.getService(userKey);
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary").setOrderBy("startTime").setMaxResults(1).setTimeMin(now)
                .setSingleEvents(true).execute();
        if (events.getItems().size() == 0) {
            return null;
        }
        return events.getItems().get(0);

    }
}
