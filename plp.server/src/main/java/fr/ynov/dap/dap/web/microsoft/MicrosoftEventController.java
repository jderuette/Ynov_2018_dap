package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.OutlookService;
import fr.ynov.dap.dap.microsoft.models.EventMicrosoft;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftEventController {
  //TODO plp by Djer |POO| Attention si tu ne précise pas, par defaut cette attribut est public (comme la classe) !
    /**
     * instantiate OutlookService
     */
    @Autowired
    OutlookService outlookService;

    /**
     * return the last events microsoft of one user
     *
     * @param userKey : name user
     * @return return the last event microsoft
     */
    @RequestMapping("/events")
    public Map<String, EventMicrosoft> events(@RequestParam("userKey") final String userKey) {
        Map<String, EventMicrosoft> lastEvent = new HashMap<>();
        lastEvent.put("lastEvent", outlookService.events(userKey));
        return lastEvent;
    }
}
