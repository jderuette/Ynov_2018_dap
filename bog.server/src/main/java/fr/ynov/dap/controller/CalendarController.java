package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.service.CalendarService;
import fr.ynov.dap.service.GoogleService;

/**
 * @author Mon_PC
 * Class CalendarController
 * Manage every maps of Calendar
 */
@RestController
public class CalendarController extends GoogleService implements Callback {

    /**.
     * calendarService is managed by Spring on the loadConfig()
     */
    @Autowired
    private CalendarService calendarService;

    /**.
     * Constructor of CalendarController
     * @throws Exception si un problème est survenu lors de la création de l'instance CalendarController
     * @throws IOException si un problème est survenu lors de la création de l'instance CalendarController
     */
    public CalendarController() throws Exception, IOException {
        super();
    }

    /**
     * @param userKey
     * userKey put in parameter
     * @return NextEvent : Correspondant au prochain évènement de l'utilisateur passé en paramètre
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws Exception si un problème est survenu lors de l'appel à cette fonction
     */
    @RequestMapping("/calendar/event/{userKey}")
    public Event getFuturEvents(@PathVariable("userKey") final String userKey) {
        Event event = null;
        try {
            event = calendarService.getNextEvent(userKey);
        } catch (IOException | GeneralSecurityException e) {
            LOG.error("Un problème est survenu lors de l'appel du service calendar", "userKey = " + userKey, e);
        }
        return event;
    }
}
