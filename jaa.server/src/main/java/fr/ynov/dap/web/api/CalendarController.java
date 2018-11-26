package fr.ynov.dap.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.google.GCalendarService;
import fr.ynov.dap.services.microsoft.MicrosoftCalendarService;

/**
 * CalendarController of the application that uses the Google and the Microsoft API.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController extends DapController {
    /**
     * Get the google calendar service thanks Spring.
     */
    @Autowired
    private GCalendarService googleCalendarService;

    /**
     * Get the Microsoft calendar thanks to Spring.
     */
    @Autowired
    private MicrosoftCalendarService microsoftCalendarService;

    /**
     * Get the next Event from now.
     * @param userKey user key needed for authentication
     * @return near event from now.
     * It's an object because we can't now if we will get a Google or a Microsoft event.
     * @throws Exception exception
     */
    @RequestMapping(value = "/event/next", method = RequestMethod.GET)
    public Object getNextEvent(@RequestParam("userKey") final String userKey) throws Exception {
        return googleCalendarService.getNextEventForAllAccount(userKey);
    }

}
