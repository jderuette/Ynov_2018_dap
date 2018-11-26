package fr.ynov.dap.controllers.common;

import fr.ynov.dap.services.google.GoogleMailService;
import fr.ynov.dap.services.microsoft.MicrosoftMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * MailController
 */
@RestController
public class MailController {

    /**
     * Autowired GmailService
     */
    @Autowired
    private GoogleMailService googleMailService;

    /**
     * Autowired MicrosoftMailService
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     * Return JSON with number of unread mails of all account of a user
     *
     * @param userName user name
     * @return Map
     */
    @RequestMapping(value = "/mail/{userName}")
    public Map<String, Integer> getUnreadEmailNumber(@PathVariable final String userName) {

        Map<String, Integer> response = new HashMap<>();

        int totalUnreadEmail = googleMailService.getNumberUnreadEmails(userName) + microsoftMailService.getNumberUnreadEmails(userName);

        response.put("total-email-unread", totalUnreadEmail);

        return response;
    }


}
