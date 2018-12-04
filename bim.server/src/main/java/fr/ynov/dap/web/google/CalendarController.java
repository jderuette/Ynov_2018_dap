package fr.ynov.dap.web.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param userKey applicative user
     * @return next event
     * @throws Exception  if user not found
     */
    @RequestMapping("/event/google/next")
    public final Event getNextEvent(@RequestParam("userKey") final String userKey)
            throws Exception {
        //TODO bim by Djer |Log4J| pas mal comme contexte mais ajoute le "userKey" qui est quand même vachement utile aussi (100% de tes utilisateurs vont demander "me" vue que tu ne gère pas la délagation dans ton code)
        LOGGER.info("Récupération du prochain evenement du calendrier de l'utilisateur {}...", "me");

        AppUser user = repository.findByName(userKey);

        return calendarService.getNextEvent(user, "me");

    }
}
