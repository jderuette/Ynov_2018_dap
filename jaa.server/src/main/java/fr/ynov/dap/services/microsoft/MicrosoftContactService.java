package fr.ynov.dap.services.microsoft;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * Microsoft Contact Service.
 */
@Service
public class MicrosoftContactService extends MicrosoftService {

    /**
     * Get Contacts of a Microsoft Account.
     * @param account Microsoft account.
     * @return ResultPages that contains the contacts of the specified Microsoft Account.
     * @throws ServiceException exception of this service.
     */
    public PagedResult<Contact> getContacts(final MicrosoftAccount account) throws ServiceException {
        getLog().info("Try to get microsoft contacts with accountName=" + account.getAccountName());

        TokenResponse tokens = getTokens(account);
        String email = getEmail(account);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        String sortGivenNameInAscendingOrder = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        Integer maxResults = MAX_RESULTS;
        Boolean showCount = true;

        try {
          PagedResult<Contact> contacts = outlookService.getContacts(
              sortGivenNameInAscendingOrder, properties, maxResults, showCount)
              .execute().body();
          return contacts;
        } catch (IOException ioe) {
            getLog().error("An occured while trying to get microsoft contacts from the API.", ioe);
            throw new ServiceException("Get Microsoft contacts failed.", ioe);
        }
    }

    /**
     * Get the number of contacts.
     * @param account Microsoft account.
     * @return the number of contacts.
     * @throws ServiceException exception of the service.
     */
    public Integer getNumberOfContacts(final MicrosoftAccount account) throws ServiceException {
        Integer numberOfContacts = getContacts(account).getCount();
        return numberOfContacts;
    }

    /**
     * Get the number of all Microsoft contacts.
    * @param userKey userKey of the AppUser account.
     * @return total number of Microsoft contacts for an AppUser.
     * @throws ServiceException exception
     */
    public Integer getNumberOfAllContacts(final String userKey) throws ServiceException {
        AppUser appUser = getRepository().findByUserKey(userKey);
        List<MicrosoftAccount> accounts = appUser.getMicrosoftAccounts();
        Integer totalNumberofContacts = 0;

        for (MicrosoftAccount account : accounts) {
            totalNumberofContacts += getNumberOfContacts(account);
        }

        return totalNumberofContacts;
    }
}
