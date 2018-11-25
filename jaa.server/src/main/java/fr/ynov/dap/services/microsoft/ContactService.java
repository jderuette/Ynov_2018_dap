package fr.ynov.dap.services.microsoft;

import java.io.IOException;

import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * @author adrij
 *
 */
public class ContactService extends MicrosoftService {

    /**
     * Constructor.
     * @param key You have to provide the userKey of the AppUser to use.
     */
    public ContactService(final String key) {
        super(key);
    }

    /**
     * Get Contacts of a Microsoft Account.
     * @param account Microsoft account.
     * @return ResultPages that contains the contacts of the specified Microsoft Account.
     * @throws ServiceException exception of this service.
     */
    public PagedResult<Contact> getContacts(final MicrosoftAccount account) throws ServiceException{
        getLog().info("Try to get microsoft contacts with accountName=" + account.getAccountName());

        TokenResponse tokens = getTokens(account);
        String email = getEmail(account);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String sortGivenNameInAscendingOrder = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        Integer maxResults = 10;

        try {
          PagedResult<Contact> contacts = outlookService.getContacts(
              sortGivenNameInAscendingOrder, properties, maxResults)
              .execute().body();
          return contacts;
        } catch (IOException ioe) {
            getLog().error("An occured while trying to get microsoft contacts from the API.", ioe);
            throw new ServiceException("Get Microsoft contacts failed.", ioe);
        }
    }
}
