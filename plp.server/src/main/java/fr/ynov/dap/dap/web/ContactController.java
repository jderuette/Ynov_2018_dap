package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.PeopleGService;
import fr.ynov.dap.dap.microsoft.OutlookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {
    @Autowired
    PeopleGService peopleGService;

    @Autowired
    OutlookService outlookService;

    @RequestMapping("/total")
    public final Map<String, Integer> getNbContacts(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {
        Integer nbContacts = 0;

        Map<String, Integer> response = new HashMap<>();
        nbContacts += peopleGService.getNbContacts(userKey);
        nbContacts += outlookService.contacts(userKey);
        response.put("nbContacts", nbContacts);
        return response;
    }
}
