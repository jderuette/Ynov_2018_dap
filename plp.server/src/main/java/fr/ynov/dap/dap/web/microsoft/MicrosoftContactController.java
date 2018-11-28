package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.OutlookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftContactController {
    @Autowired
    OutlookService outlookService;

    @RequestMapping("/contacts")
    public Map<String, Integer> contacts(@RequestParam("userKey") final String userKey) {
        Map<String, Integer> response = new HashMap<>();
        response.put("nbContacts", outlookService.contacts(userKey));
        return response;
    }
}
