package fr.ynov.dap.dap.web.google;

import fr.ynov.dap.dap.GMailService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.microsoft.OutlookService;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@RestController
@RequestMapping("/google/email")
public class GMailController {
    /**
     * Instantiate instance of GMailService.
     */
    @Autowired
    private GMailService gMailService;

    /**
     * get the number of unread mail.
     *
     * @param userKey : userKey param
     * @return Map : key Total unread
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    @RequestMapping("/unread")
    public final Map<String, Integer> getUnread(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {
        Integer nbEmailUnread = 0;
        nbEmailUnread += gMailService.getNbUnreadEmails(userKey);
        Map<String, Integer> response = new HashMap<>();
        response.put("unread", nbEmailUnread);
        return response;
    }
}
