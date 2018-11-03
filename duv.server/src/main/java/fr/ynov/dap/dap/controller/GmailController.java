package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.GMailService;

/**
 *
 * @author David_tepoche
 *
 */
@RestController
@RequestMapping("/emails")
public class GmailController {

    /**
     * dunno.
     */
    @Autowired
    private GMailService gmailService;

    /**
     * get the nomber of mail unread.
     *
     * @param userId user key.
     * @return number of gmail unread.
     * @throws IOException              throw from gmailService
     * @throws GeneralSecurityException throw from gmailService
     */
    @RequestMapping("/nbrunreadmail/{userId}")
    public @ResponseBody Object getNbrUnreadMail(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException {
        Integer nbrEmailUnread = null;

        nbrEmailUnread = gmailService.nbrEmailUnread(userId);

        return nbrEmailUnread;
    }
}
