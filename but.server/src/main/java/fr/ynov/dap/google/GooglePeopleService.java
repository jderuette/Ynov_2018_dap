package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ContactGroup;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;

/**
 * Manage Google Calendar Service.
 * @author thibault
 *
 */
//TODO but by Djer |POO| La majorité des remarques de GMAILService sont vrais pour cette classe.
//C'est d'ailleur "frappant" comme toutes ces classes "Google Service" ce ressembles. Ce qui les différentie : "getService" renvoie un type différent (utiliser un classe avec un paramètre "Generic", mais attentions à l'IOC). Chaque méthode métier est différentes mais qui fonctionne par "pair (par "Account, et "forAll").
@Service
public class GooglePeopleService extends GoogleService {
    /**
     * Logger for the class.
     */
  //TODO but by Djer |Log4J| Devrait être final
    private static Logger logger = LogManager.getLogger();

    /**
     * Connect to Google People Service.
     * @param accountName Account of user (associate token)
     * @param owner Owner of google account
     * @return Google People service
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public PeopleService getService(final String accountName, final AppUser owner)
            throws IOException, GeneralSecurityException {
        logger.info("Generate service People for account '" + accountName + "'");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential cred = getCredentials(accountName, owner);
        PeopleService service = new PeopleService.Builder(httpTransport, this.getJsonFactory(), cred)
                .setApplicationName(this.getConfig().getApplicationName()).build();

        return service;
    }

    /**
     * Get number of contacts.
     * @param accountName ID of user (associate token)
     * @param owner Owner of google account
     * @return int count of contacts
     * @throws GeneralSecurityException problem security with google server
     * @throws IOException error server response google
     */
    public int countContacts(final String accountName, final AppUser owner)
            throws IOException, GeneralSecurityException {
        logger.info("Get number of contacts for account '" + accountName + "'");
        int membersCount = 0;

        PeopleService service = this.getService(accountName, owner);
        ContactGroup allContacts = service.contactGroups().get("contactGroups/all").execute();

        if (allContacts.getMemberCount() != null) {
            membersCount = allContacts.getMemberCount();
        }
        return membersCount;
    }

    /**
     * Get number of contacts of all accounts google.
     * @param user Owner of accounts
     * @return int count of contacts
     * @throws GeneralSecurityException problem security with google server
     * @throws IOException error server response google
     */
    public int countContactsOfAllAccounts(final AppUser user) throws IOException, GeneralSecurityException {
        int membersCount = 0;

        for (GoogleAccount gAccount : user.getGoogleAccounts()) {
            membersCount += countContacts(gAccount.getAccountName(), user);
        }

        return membersCount;
    }
}
