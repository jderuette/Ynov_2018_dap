package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.service.CalendarService;
import fr.ynov.dap.dap.service.GoogleService;

/**
 * @author Florian BRANCHEREAU
 *
 */
@RestController
public class CalendarController extends GoogleService implements Callback {

    /**.
     * Declaration de CalendarService
     */
    @Autowired
    private CalendarService calendarservice;

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public CalendarController() throws Exception, IOException {
    }

    /**
     * @param userKey Nom du compte
     * @return Le prochain évènement
     * @throws IOException fonction
     * @throws Exception fonction
     */
    @RequestMapping("/calendarNextEvent")
    public String getNextEvent(@RequestParam("userKey") final String userKey) throws IOException, Exception {
        return calendarservice.getNextEvents(userKey);
    }

    /**.
     * @param userKey Nom de l'utilisateur
     * @return le prochain evenement
     * @throws GeneralSecurityException fonction
     * @throws IOException fonction
     */
    @RequestMapping("/calendarNextEventORM")
    public String getEventForAll(@RequestParam("userKey") final String userKey)
            throws GeneralSecurityException, IOException {
        return calendarservice.getNextEventORM(userKey);

    }
}
