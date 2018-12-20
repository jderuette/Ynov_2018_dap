package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.service.GmailService;

/**
 *
 * @author Dom
 *This class return the number of email unread with a string param "userId" in format Map<String,Integer>
 */
@RestController
public class GMailController {
    /**.
     *  Return the unique instance of GmailService with annotation Autowired
     */
    @Autowired
    private GmailService gmailService;

    /**.
     * This function return the number of email unread with string param userId according to the annotated route
     * @param userKey .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    @RequestMapping("/google/getEmailUnread")
    public Map<String, Integer> getEmailUnread(@RequestParam("userKey") final String userKey)
            throws GeneralSecurityException, IOException {

        int nbMessageUnread = gmailService.nbMessageUnread(userKey);

        return Collections.singletonMap("nbMessageUnread", nbMessageUnread);
    }

    /**
     *
     * @param userKey .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */

    @RequestMapping("/google/getAllEmailUnread")
    public Map<String, Integer> getAllEmailUnread(@RequestParam("userKey") final String userKey)
            throws GeneralSecurityException, IOException {

        int nbMessageUnread = gmailService.nbMessageUnreadAll(userKey);
        //TODO phd by Djer |Rest API| pas de SysOut sur un serveur
        System.out.println(nbMessageUnread);
        return Collections.singletonMap("nbMessageUnreadAll", nbMessageUnread);
    }

}
