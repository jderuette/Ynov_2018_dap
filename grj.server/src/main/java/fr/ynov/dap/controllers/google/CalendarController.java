package fr.ynov.dap.controllers.google;

import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.google.CalendarService;
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

    /**
     * Autowired CalendarService class
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * Autowired UserRepository class
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Log4j logger
     */
    private final static Logger LOGGER = LogManager.getLogger(CalendarController.class);

    /**
     * Retrieve the next event of all google account of a user.
     *
     * @param userName name of the user to retrieve
     * @return Map with the next event
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    @RequestMapping(value = "/event/{userName}")
    public final Map<String, String> getNextEvent(@PathVariable final String userName) throws GeneralSecurityException, IOException {

        Map<String, String> response = new HashMap<>();

        User user = userRepository.findByName(userName);

        if (user == null) {
            response.put("error", "User does not exist");
            return response;
        }

        List<GoogleAccount> userGoogleAccountList = user.getGoogleAccountList();

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
