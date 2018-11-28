package fr.ynov.dap.microsoft.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.data.Contact;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Mon_PC
 */
@Service
public class MicrosoftContactService {

    /**.
     * repositoryUser is managed by Spring on the loadConfig()
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * @param accessToken token
     * @param email mail user
     * @return contacts
     * @throws IOException si problème lors de l'éxécution de la fonction
     */
    public PagedResult<Contact> getContacts(final String accessToken, final String email) throws IOException {
        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, email);

        // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        PagedResult<Contact> contacts;
        contacts = outlookService.getContacts(sort, properties).execute().body();

        return contacts;
    }

    /**
     * @param userKey correspondant à l'userId passé
     * @return nb unread mail for all accounts microsoft
     * @throws IOException problème lors de l'éxécution de la fonction
     */
    public int getNbContactAllAccountMicrosoft(final String userKey) throws IOException {

        AppUser user = repositoryUser.findByName(userKey);
        List<MicrosoftAccountData> accounts = user.getAccountsMicrosoft();

        int nbContactAllAccount = 0;
        for (MicrosoftAccountData account : accounts) {
            nbContactAllAccount += getContacts(account.getToken().getAccessToken(), account.getUserEmail())
                    .getValue().length;
        }
        return nbContactAllAccount;
    }
}
