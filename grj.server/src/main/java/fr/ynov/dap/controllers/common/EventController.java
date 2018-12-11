package fr.ynov.dap.controllers.common;

import fr.ynov.dap.models.common.Event;
import fr.ynov.dap.services.google.GoogleEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * EventController
 */
@RestController
public class EventController {

    /**
     * Autowired GoogleEventService
     */
    @Autowired
    private GoogleEventService googleEventService;

    /**
     * TODO grj by DJer |javaDoc| Pense à vérifier que ta Javadoc est toujours "juste" ... (essaye d'éviter les copier/coller d'une façon général)
     * Get contact number of all account of a user
     *
     * @param userName userName
     * @return contact total
     */
    @RequestMapping(value = "/event/{userName}", produces = "application/json", method = GET)
    public Event getNextEvent(@PathVariable final String userName) {

        Event nextEvent;

        nextEvent = googleEventService.getNextEvent(userName);
        
        //TODO grj by Djer |API Microsoft| Intégrogation des comtpes Microsoft ?

        return nextEvent;
    }

}
