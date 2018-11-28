package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.data.Contact;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.PagedResult;

/**
 *
 * @author Dom
 *
 */
@Service
public class MicrosoftContactService {

    /**
    *
    */
    @Autowired
    private AppUserRepository userRepository;

    /**
    *
    * @param accessToken .
    * @param email .
    * @return .
    * @throws IOException .
    */
    public int getNbContact(final String accessToken, final String email) throws IOException {

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, email);

        // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        PagedResult<Contact> contacts = outlookService.getContacts(sort, properties).execute().body();

        return contacts.getValue().length;
    }

    /**
    *
    * @param userId .
    * @return .
    * @throws IOException .
    */
    public int getNbContactAllAccount(@RequestParam("userId") final String userId) throws IOException {
        AppUser user = userRepository.findByName(userId);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();
        int nbContactAllAccount = 0;
        for (MicrosoftAccountData accountData : accounts) {
            int nbContactAccount = getNbContact(accountData.getTokens().getAccessToken(), accountData.getUserEmail());
            nbContactAllAccount = nbContactAllAccount + nbContactAccount;

        }
        return nbContactAllAccount;
    }

}
