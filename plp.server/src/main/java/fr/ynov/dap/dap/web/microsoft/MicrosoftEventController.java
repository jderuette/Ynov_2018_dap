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
    @Autowired
    OutlookService outlookService;

    @RequestMapping("/events")
    public Map<String, EventMicrosoft> events(@RequestParam("userKey") final String userKey) {
        Map<String, EventMicrosoft> lastEvent = new HashMap<>();
        lastEvent.put("lastEvent", outlookService.events(userKey));
        return lastEvent;
    }
}
