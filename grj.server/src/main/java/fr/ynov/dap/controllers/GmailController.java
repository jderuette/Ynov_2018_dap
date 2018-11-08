package fr.ynov.dap.controllers;

import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.GmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * GmailController
 */
@RestController
public class GmailController {

    @Autowired
    private GmailService gmailService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Return HashMap with value of unread email.
     *
     * @param userName userKey to log
     * @return Unread email in a Map
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    @RequestMapping(value = "/gmail/{userName}")
    public final Map<String, String> getNumberUnreadEmails(@PathVariable final String userName) throws GeneralSecurityException, IOException {

        Map<String, String> response = new HashMap<>();

        User                    user                  = userRepository.findByName(userName);
        List<GoogleAccount>     userGoogleAccountList = user.getAccounts();
        Iterator<GoogleAccount> iterator              = userGoogleAccountList.listIterator();
        int                     emailTotalNumber      = 0;

        while (iterator.hasNext()) {
            GoogleAccount currentGoogleAccount = iterator.next();
            emailTotalNumber += Integer.valueOf(gmailService.getNumberUnreadEmails(currentGoogleAccount.getName()));
        }

        response.put("email_unread", String.valueOf(emailTotalNumber));

        return response;
    }


}
