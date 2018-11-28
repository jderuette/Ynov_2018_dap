package fr.ynov.dap.web;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.GMAILService;
import fr.ynov.dap.microsoft.MicrosoftMailService;

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
     * Mail Microsft service.
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * Route to get number of email unread.
     * @param userKey id of google user
     * @return number of email unread
     * @throws IOException google server response error HTTP
     */
    @RequestMapping("/emails/unread")
    public int getUnreadEmailsNumber(@RequestParam("userKey") final String userKey) throws IOException {
        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        return this.service.getUnreadEmailsNumberOfAllEmails(user) + microsoftMailService.getUnreadEmailCount(user);
    }
}
