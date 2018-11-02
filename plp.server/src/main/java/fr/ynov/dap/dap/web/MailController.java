package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.GMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@RestController
@RequestMapping("/email")
public class MailController {
    /**
     * Instantiate instance of GMailService.
     */
    @Autowired
    private GMailService gMailService;

    /**
     * get the number of unread mail.
     *
     * @param userId : userKey param
     * @return Map : key Total unread
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    @RequestMapping("/unread")
    public final Map<String, Integer> getUnread(@RequestParam("userKey") final String userId)
            throws IOException, GeneralSecurityException {
        return gMailService.getNbUnreadEmails(userId);
    }
}
