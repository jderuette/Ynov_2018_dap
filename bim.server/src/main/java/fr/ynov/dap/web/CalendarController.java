package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.CalendarService;

/**
 * Calendar controller.
 * @author MBILLEMAZ
 *
 */
@RestController
public class CalendarController {

    /**
     * Logger.
     */
    public static final Logger LOGGER = LogManager.getLogger();
    /**
     * Gmail service.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * Repository.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * get next event.
     * @param gUser google user
     * @param userKey applicative user
     * @return next event
     * @throws Exception  if user not found
     */
    @RequestMapping("/event/next/{gUser}")
    public final Event getNextEvent(@PathVariable final String gUser, @RequestParam("userKey") final String userKey)
            throws Exception {
        LOGGER.info("Récupération du prochain evenement du calendrier de l'utilisateur {}...", gUser);

        AppUser user = repository.findByName(userKey);

        return calendarService.getNextEvent(user, gUser);

    }
}
