package fr.ynov.dap.controllers.common;

import fr.ynov.dap.services.google.GoogleContactService;
import fr.ynov.dap.services.microsoft.MicrosoftContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * ContactController
 */
@RestController
public class ContactController {

    /**
     * TODO grj by Djer |Audit Code| La description DOIT se terminer par un ".". Cela fera plaisir à CheckStyle en plus
     * Autowired GoogleContactService
     */
    @Autowired
    private GoogleContactService googleContactService;

    /**
     * Autowired MicrosoftContactService
     */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * Get contact number of all account of a user
     *
     * @param userName userName
     * @return contact total
     */
    @RequestMapping(value = "/contact/{userName}", produces = "application/json", method = GET)
    public Map<String, Integer> getNumberContact(@PathVariable final String userName) {

        Map<String, Integer> response = new HashMap<>();

        //TODO grj by Djer |IDE| Configure les "save action" de ton IDE pour formater ton code lors de la sauvegarde pour éviter ces lignes à rallonge
        int totalUnreadEmail = googleContactService.getNumberContacts(userName) + microsoftContactService.getNumberUnreadEmails(userName);

        response.put("total-contacts", totalUnreadEmail);

        return response;
    }


}
