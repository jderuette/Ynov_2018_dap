package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.model.MailModel;
import fr.ynov.dap.dap.service.GmailService;

/**
 * The Class GmailController.
 */
@RestController
@RequestMapping("/mail")
public class GmailController {
    
    /** The gmail service. */
    @Autowired
    private GmailService gmailService;

    
    /**
     * Gets the mail in box.
     *
     * @param userId the user id
     * @return the mail in box
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws Exception the exception
     */
    @RequestMapping("/inbox/{userId}")
    public MailModel getMailInBox(@PathVariable final String userId) throws IOException, Exception {
        return gmailService.getMailInboxTotal(userId);
    }

    
    /**
     * Gets the mail inbox unread.
     *
     * @param userId the user id
     * @return the mail inbox unread
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping(value = "/inbox/unread/{userId}")
    public MailModel getMailInboxUnread(@PathVariable final String userId)
         throws IOException, GeneralSecurityException {
         return gmailService.getMailInBoxUnread(userId);
    }
}
