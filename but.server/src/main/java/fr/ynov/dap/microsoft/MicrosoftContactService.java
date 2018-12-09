package fr.ynov.dap.microsoft;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.authentication.AuthHelper;
import retrofit2.Response;

/**
 * Service to manage microsoft contact.
 * @author thibault
 *
 */
@Service
public class MicrosoftContactService {

    /**
     * Repository of MicrosoftAccount.
     */
    @Autowired
    private MicrosoftAccountRepository repositoryMicrosoftAccount;

    /**
     * Logger for the class.
     */
  //TODO but by Djer |Log4J| Devrait être final
    private static Logger logger = LogManager.getLogger();

    /**
     * Get number of microsoft contact of one user (all accounts).
     * @param user the app user.
     * @return count of contact.
     * @throws IOException if error http.
     */
    public int getContactCount(final AppUser user) throws IOException {
        logger.info("Get count contact Microsoft for user : '" + user.getUserKey() + "'");
        int result = 0;
        for (MicrosoftAccount mAccount : user.getMicrosoftAccounts()) {

            mAccount = AuthHelper.ensureTokens(mAccount);
            repositoryMicrosoftAccount.save(mAccount);

            OutlookService outlookService = OutlookServiceBuilder
                    .getOutlookService(mAccount.getAccessToken(), mAccount.getEmail());

            // Sort by given name in ascending order (A-Z)
            String sort = "GivenName ASC";
            // Only return the properties we care about
            String properties = "GivenName,Surname,CompanyName,EmailAddresses";
            // Return at most 1 contacts
            Integer maxResults = 1;

            Response<PagedResult<Contact>> response = outlookService.getContacts(
                    sort, properties, maxResults).execute();

            if (response.code() >= HttpStatus.SC_BAD_REQUEST) {
              //TODO but by Djer |Log4J| Une petite LOG ? (en Warning à priori)
                throw new HttpResponseException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Error with Microsoft API.");
            }

            PagedResult<Contact> contacts = response.body();

            result += contacts.getCount();
        }
        return result;
    }
}
