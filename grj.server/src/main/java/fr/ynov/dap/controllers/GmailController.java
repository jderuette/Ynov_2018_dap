package fr.ynov.dap.controllers;

import fr.ynov.dap.services.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * GmailController
 */
@RestController
public class GmailController {

    @Autowired
    private GmailService gmailService;

    /**
     * Return HashMap with value of unread email.
     *
     * @param userKey userKey to log
     * @return Unread email in a Map
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    @RequestMapping(value = "/gmail/{userKey}")
    public final Map<String, String> getNumberUnreadEmails(@PathVariable final String userKey) throws GeneralSecurityException, IOException {

        Map<String, String> response = new HashMap<>();
        response.put("email_unread", gmailService.getNumberUnreadEmails(userKey));

        return response;
    }


}
