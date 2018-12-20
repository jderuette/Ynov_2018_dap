package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.GoogleCalendar;

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
    @RequestMapping("/Calendar/event")
    public final String getNextEvent(final @RequestParam("userKey") String user)
            throws IOException, GeneralSecurityException {
        //TODO roa by Djer |roa| Intégroger les comtpes Microsoft ? 
        return calendar.getNextEvent(user);
    }
}
