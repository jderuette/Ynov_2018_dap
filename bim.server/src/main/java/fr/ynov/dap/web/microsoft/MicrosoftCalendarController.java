package fr.ynov.dap.web.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.microsoft.MicrosoftEvent;

import fr.ynov.dap.microsoft.OutlookService;

@Controller
public class MicrosoftCalendarController {

    /**
     * outlook Service.
     */
    @Autowired
    private OutlookService outlookService;

    /**
     * User repository.
     */
    @Autowired
    private AppUserRepository userRepository;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * get next microsoft event.
     * @param userKey app user.
     * @return next event
     */
    @RequestMapping("/event/microsoft/next")
    @ResponseBody
    public MicrosoftEvent getNextEvent(@RequestParam("userKey") final String userKey) {
        AppUser user = userRepository.findByName(userKey);

        MicrosoftEvent nextEvent = null;
        try {
            nextEvent = outlookService.getNextEvent(user);
        } catch (IOException e) {
            LOGGER.error("Impossible de récupérer les le prochain evenement outlook de l'utilisateur " + userKey, e);
        }

        return nextEvent;
    }
}
