package fr.ynov.dap.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.microsoft.service.MicrosoftMailService;
import fr.ynov.dap.service.GmailService;

/**
 * @author Mon_PC
 */
@Service
public class MailGlobalService {

    /**.
     * microsoftService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**.
     * gmailService is managed by Spring on the loadConfig()
     */
    @Autowired
    private GmailService gmailService;

    /**
     * @param userKey user passé en param
     * @return globalMailUnread of microsoft and google
     * @throws Exception exeception si problème rencontré
     */
    public int totalMailGoogleMicrosoft(final String userKey) throws Exception {
        int totalMailGoogle = 0;
        int totalMailMicrosoft = 0;
        int globalMailUnread = 0;

        totalMailGoogle = gmailService.getMsgsUnread(userKey);
        totalMailMicrosoft = microsoftMailService.getNbMailAllAccountMicrosoft(userKey);

        globalMailUnread = totalMailGoogle + totalMailMicrosoft;

        return globalMailUnread;
    }

}
