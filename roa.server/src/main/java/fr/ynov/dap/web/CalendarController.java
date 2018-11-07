package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.GoogleCalendar;

/**
 * Controller pour la manipulation du calendrier google.
 * @author alex
 */
@RestController
public class CalendarController {
    /**
     * Récupération du service google Calendar.
     */
    @Autowired
    private GoogleCalendar calendar;
    /**
     * Récupération du prochain évènement du calendrier de l'utilisateur.
     * @param user utilisateur
     * @return String le prochain évènement
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/calendar/event")
    public String getNextEvent(final @RequestParam("userKey") String user)
            throws IOException, GeneralSecurityException {
        return calendar.getNextEvent(user);
    }
}
