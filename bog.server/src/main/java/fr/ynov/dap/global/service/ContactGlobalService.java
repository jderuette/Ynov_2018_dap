package fr.ynov.dap.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.microsoft.service.MicrosoftContactService;
import fr.ynov.dap.service.PeopleGoogleService;

/**
 * @author Mon_PC
 */
@Service
public class ContactGlobalService {

    /**.
     * microsoftService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**.
     * contactService is managed by Spring on the loadConfig()
     */
    @Autowired
    private PeopleGoogleService contactService;

    /**
     * @param userKey user passé en param
     * @return globalMailUnread of microsoft and google
     * @throws Exception exeception si problème rencontré
     */
    public int totalMailGoogleMicrosoft(final String userKey) throws Exception {
        int totalContactGoogle = 0;
        int totalContactMicrosoft = 0;
        int globalContacts = 0;

        totalContactGoogle = contactService.getNbContact(userKey);
        totalContactMicrosoft = microsoftContactService.getNbContactAllAccountMicrosoft(userKey);

        globalContacts = totalContactGoogle + totalContactMicrosoft;

        return globalContacts;
    }
}
