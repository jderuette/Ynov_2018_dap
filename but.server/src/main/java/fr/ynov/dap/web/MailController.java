package fr.ynov.dap.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.google.GMAILService;

/**
 * @author thibault
 *
 */
@RestController
public class MailController extends HandlerErrorController {
    /**
     * Mail Google service.
     */
    @Autowired
    private GMAILService service;

    /**
     * Route to get number of email unread.
     * @param userId id of google user
     * @return number of email unread
     * @throws IOException google server response error HTTP
     */
    @RequestMapping("/emails/unread")
    public int getUnreadEmailsNumber(@RequestParam("userId") final String userId) throws IOException {
        return this.service.getUnreadEmailsNumber(userId);
    }
}
