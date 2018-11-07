package fr.ynov.dap.controllers;

import fr.ynov.dap.services.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * Calendar Controller
 */
@RestController
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
    @RequestMapping(value = "/event/{userKey}")
    public final Map<String, String> getNextEvent(@PathVariable final String userKey) throws GeneralSecurityException, IOException {
        return calendarService.getNextEvent(userKey);
    }
}
