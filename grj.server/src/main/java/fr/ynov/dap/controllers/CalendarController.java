package fr.ynov.dap.controllers;

import fr.ynov.dap.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
//TODO grj by Djer JavaDoc ?
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    /**
     * Return the next event
     *
     * @param userKey userKey to log
     * @return HashMap with the next event
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    //TODO grj by Djer "produces" et "method" ont déja ces valeurs par defaut dans un @RestController
    @RequestMapping(value = "/event/{userKey}", produces = "application/json", method = GET)
    public final Map<String, String> getNextEvent(@PathVariable final String userKey) throws GeneralSecurityException, IOException {
        return calendarService.getNextEvent(userKey);
    }
}
