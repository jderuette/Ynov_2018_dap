package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.service.CalendarService;

/**
 * @author Dom
 * Class CalendarController
 */
@RestController
public class CalendarController {
    /**.
     * Return the unique instance of calendarService with annotation Autowired
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * @param userKey .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    @RequestMapping("/google/getEventUncomming")
    public Map<String, Object> getEvent(@RequestParam("userKey") final String userKey)
            throws GeneralSecurityException, IOException {
        return calendarService.getNextEventFormatMap(userKey);

    }

    /**
     *
     * @param userKey .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    @RequestMapping("/google/getEventUncommingForAll")
    public Event getEventForAll(@RequestParam("userKey") final String userKey)
            throws GeneralSecurityException, IOException {
        return calendarService.getNextEventForAll(userKey);

    }

}
