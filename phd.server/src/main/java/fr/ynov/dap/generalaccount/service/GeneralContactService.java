package fr.ynov.dap.generalaccount.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.microsoft.service.MicrosoftContactService;
import fr.ynov.dap.service.PeopleGService;

/**
 *
 * @author Dom
 *
 */
@Service
public class GeneralContactService {
    /**
    *
    */
    @Autowired
    private PeopleGService peopleGService;

    /**
    *
    */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
    *
    * @return .
    * @throws GeneralSecurityException .
    * @throws IOException .
    * @param userId .
    */
    public int getTotalContactGoogleAndMicrosoft(final String userId) throws IOException, GeneralSecurityException {
        int nbContactGoogle = peopleGService.nbContact(userId);
        int nbContactMicrosoft = microsoftContactService.getNbContactAllAccount(userId);
        return nbContactGoogle + nbContactMicrosoft;
    }
}
