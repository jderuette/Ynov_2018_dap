package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.CalendarService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pierre Plessy
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    /**
     * Instantiate instance of CalendarService.
     */
    @Autowired
    private CalendarService calendarService;
    /**
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;
    /**
     * Instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(CalendarController.class);


    /**
     * get the next event for a user when there is a request in /calendar/nextEvent.
     *
     * @param userId : userkey param
     * @return Map : contains accessRole, subject, start (date or/and time), finish (date or/and time)
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    @RequestMapping("/nextEvent")
    public final Map<String, String> getNextEvent(@RequestParam("userKey") final String userId)
            throws IOException, GeneralSecurityException {
        AppUser user = userRepository.findByName(userId);
        List<GoogleAccount> listGoogleAccount = user.getGoogleAccount();
        Map<String, String> response = new HashMap<>();
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");

        for(GoogleAccount currentAccount : listGoogleAccount) {
            Map<String, String> nextCurrentEvent = calendarService.getNextEvent(currentAccount.getName());

            if(response.isEmpty()) {
                response = nextCurrentEvent;
            }
            else {
                try {
                    Date currentEvent = format.parse(nextCurrentEvent.get("start"));
                    Date responseEvent = format.parse(response.get("start"));
                    if (currentEvent.before(responseEvent)) {
                        response = nextCurrentEvent;
                    }
                }
                catch (Exception e) {
                    log.error("Failed to parse the event date fur user : " + userId, e);
                }
            }
        }
        
        return response;
    }

}
