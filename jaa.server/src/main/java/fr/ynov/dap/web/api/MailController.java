package fr.ynov.dap.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.google.GMailService;

/**
 * @author adrij
 *
 */
@RestController
@RequestMapping("/email")
public class MailController extends GoogleController {
    /**
     * Gmail service with the magic of Spring.
     */
    @Autowired
    private GMailService gmailService;

    /**
     * Get the number of unread emails.
     * @param user specify the user. For example: "me"
     * @param userKey specify the userKey needed for authentication.
     * @return number of unread email
     * @throws Exception exception
     */
    @RequestMapping(value = "/nbUnread", method = RequestMethod.GET)
    public Integer getUnreadEmailsNumber(
            @RequestParam("user") final String user,
            @RequestParam("userKey") final String userKey) throws Exception {

        return gmailService.getUnreadEmailsNumberOfAllAccount(user, userKey);
    }
}
