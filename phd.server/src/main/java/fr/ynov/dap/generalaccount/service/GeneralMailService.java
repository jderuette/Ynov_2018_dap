package fr.ynov.dap.generalaccount.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.microsoft.service.MicrosoftMailService;
import fr.ynov.dap.service.GmailService;

/**
 *
 * @author Dom
 *
 */
@Service
public class GeneralMailService {

    /**
     *
     */
    @Autowired
    private GmailService gmailService;

    /**
     *
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     *
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     * @param userId .
     */
    public int getTotalMailGoogleAndMicrosoft(final String userId) throws IOException, GeneralSecurityException {
        int nbMailGoogle = gmailService.nbMessageUnreadAll(userId);
        int nbMailMicrosoft = microsoftMailService.getNbMailAllAccount(userId);
        return nbMailGoogle + nbMailMicrosoft;
    }
}
