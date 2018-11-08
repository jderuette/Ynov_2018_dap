package fr.ynov.dap.controllers;

import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.CalendarService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.*;
import java.util.*;

/**
 * Calendar Controller
 */
@RestController
public class CalendarController {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private UserRepository userRepository;

    private final static Logger LOGGER = LogManager.getLogger(CalendarController.class);

    /**
     * Return the next event
     *
     * @param userName userKey to log
     * @return HashMap with the next event
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    @RequestMapping(value = "/event/{userName}")
    public final Map<String, String> getNextEvent(@PathVariable final String userName) throws GeneralSecurityException, IOException {

        Map<String, String> response = new HashMap<>();

        User                user                  = userRepository.findByName(userName);
        List<GoogleAccount> userGoogleAccountList = user.getAccounts();

        for (GoogleAccount currentGoogleAccount : userGoogleAccountList) {
            Map<String, String> currentNextEvent = calendarService.getNextEvent(currentGoogleAccount.getName());

            if (response.isEmpty()) {
                response = currentNextEvent;
            } else {
                try {
                    Date currentNextEventStartDate = new SimpleDateFormat("yyyy-MM-dd").parse(currentNextEvent.get("start_date"));
                    Date currentCloserDate         = new SimpleDateFormat("yyyy-MM-dd").parse(response.get("start_date"));
                    if (currentNextEventStartDate.before(currentCloserDate)) {
                        response = currentNextEvent;
                    }
                } catch (ParseException e) {
                    LOGGER.error("Error when trying to parse date", e);
                }
            }
        }

        return response;
    }
}
