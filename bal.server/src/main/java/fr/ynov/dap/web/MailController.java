package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.GoogleAccountException;
import fr.ynov.dap.exception.MicrosoftAccountException;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.GMailService;
import fr.ynov.dap.microsoft.OutlookService;

@RestController
@RequestMapping("/mail")
public class MailController extends BaseController {
    @Autowired
    private GMailService gmailService;
    @Autowired
    private OutlookService outlookService;
    
    @Override
    protected final String getClassName() {
        return MailController.class.getName();
    }

    @RequestMapping("/nbUnread/{userId}")
	public final int getNumberOfUnreadMessage(@PathVariable("usersId") final String userId)
            throws UserException, GoogleAccountException, IOException,
            GeneralSecurityException, MicrosoftAccountException {
        AppUser user = getUserById(userId);
        int googleNbUnreadMail = gmailService.getNbUnreadMailAllAccount(user);
        int microsoftNbUnreadMail = outlookService.getNbUnreadMails(user);
        return googleNbUnreadMail + microsoftNbUnreadMail;

    }

    @RequestMapping("/google/nbUnread/{userId}")
    public final int getGoogleNumberOfUnreadMessage(@PathVariable("userId") final String userId)
            throws IOException, GeneralSecurityException, UserException,
            GoogleAccountException {
        AppUser user = getUserById(userId);
        int nbUnreadMail = gmailService.getNbUnreadMailAllAccount(user);
        return nbUnreadMail;

    }

    @RequestMapping("/microsoft/nbUnread/{userId}")
    public final int getMicrosoftNumberOfUnreadMessage(@PathVariable("userId") final String userId,
            final HttpServletRequest request) throws UserException, MicrosoftAccountException, IOException, GeneralSecurityException {
        AppUser user = getUserById(userId);
        int numberOfUnreadMails = outlookService.getNbUnreadMails(user);
        return numberOfUnreadMails;

    }

}
