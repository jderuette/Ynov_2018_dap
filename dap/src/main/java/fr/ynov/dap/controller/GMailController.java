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
     * @param userId .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    @RequestMapping("/getEmailUnread")
    public Map<String, Integer> getEmailUnread(@RequestParam("userId") final String userId)
            throws GeneralSecurityException, IOException {

        int nbMessageUnread = gmailService.nbMessageUnread(userId);

        return Collections.singletonMap("nbMessageUnread", nbMessageUnread);
    }
}
