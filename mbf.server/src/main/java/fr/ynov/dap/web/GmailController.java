package fr.ynov.dap.web;

import fr.ynov.dap.services.Google.GMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * The GmailController handles all the client(s) requests related to the user emails.
 */
@RestController
public class GmailController {

    /**
     * The Gmail service attribute automatically wired by Spring.
     */
    @Autowired
    private GMailService gmailService;

    /**
     * This method handles the client's request on the endpoint /email/nbUnread.
     * @param userKey This is the userKey of the currently authenticated user.
     * @return It returns the response of the request which contains the number of unread emails.
     * @throws IOException The GMailService can throw an IOException.
     */
    @RequestMapping("/email/nbUnread")
    public final Map<String, Object> getUnreadEmail(@RequestParam("userKey") final String userKey) throws IOException {
        return gmailService.getUnreadEmail(userKey);
    }
}
