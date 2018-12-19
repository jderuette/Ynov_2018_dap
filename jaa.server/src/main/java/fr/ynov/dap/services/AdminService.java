package fr.ynov.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.models.AccountData;
import fr.ynov.dap.models.AccountData.Type;
import fr.ynov.dap.services.google.GoogleAuthorizationFlowService;

/**
 * Admin service, used by the AdminController.
 */
@Service
public class AdminService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();
    /**
     * One thousand.
     */
    private static final int ONE_THOUSAND = 1000;

    /**
     * AppUserRepository instantiate thanks to the injection of dependency.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * Instantiate GoogleAuthorizationFlow using injection of dependency.
     */
    @Autowired
    private GoogleAuthorizationFlowService googleDataStoreService;

    /**
     * Display accounts informations.
     * @return Several Account informations inside the AccountData model.
     * @throws ServiceException exception
     */
    public List<AccountData> getAccountsInformations() throws ServiceException {
        log.info("Accounts informations requested by the admin.");
        List<AccountData> accounts = new ArrayList<AccountData>();

        Iterable<AppUser> appUsers = repository.findAll();

        for (AppUser appUser : appUsers) {
            addGoogleAccountToAccounts(accounts, appUser);
            addMicrosoftAccountToAccounts(accounts, appUser);
        }

        return accounts;
    }

    /**
     * Parse Microsoft accounts and add them to the acounts list.
     * @param accounts accounts that will be displayed to the view.
     * @param appUser AppUser account.
     * @throws ServiceException exception.
     */
    private void addMicrosoftAccountToAccounts(final List<AccountData> accounts, final AppUser appUser)
            throws ServiceException {
        List<MicrosoftAccount> microsoftAccounts = appUser.getMicrosoftAccounts();
        for (MicrosoftAccount microsoftAccount : microsoftAccounts) {
            AccountData account = new AccountData();
            account.setUserKey(appUser.getUserKey());
            account.setAccountName(microsoftAccount.getAccountName());
            try {
                account.setAccessToken(microsoftAccount.getTokenResponse().getAccessToken());
                account.setRefreshToken(microsoftAccount.getTokenResponse().getRefreshToken());
                account.setExpirationTimeMilliseconds(convertSecondsToMilliseconds(
                        microsoftAccount.getIdToken().getExpirationTime()));
                account.setTenantId(microsoftAccount.getIdToken().getTenantId());
            } catch (IOException e) {
              //TODO jaa by Djer |Log4J| Contextualise tes messages
                log.error("Error occured during the microsoftAccount reading", e);
                throw new ServiceException("Faild to get some properties of Microsoft Account", e);
            }
            account.setAccountType(Type.Microsoft);
            accounts.add(account);
        }
    }

    /**
      * Parse Google accounts and add them to the acounts list.
     * @param accounts accounts that will be displayed to the view.
     * @param appUser AppUser account.
     * @throws ServiceException exception.
     */
    private void addGoogleAccountToAccounts(final List<AccountData> accounts, final AppUser appUser)
            throws ServiceException {
        List<String> googleAccountNames = appUser.getGoogleAccountNames();
        Map<String, StoredCredential> googleMap;
        try {
            googleMap = googleDataStoreService.getStoreCredentialMap();
        } catch (GeneralSecurityException | IOException e) {
            //TODO jaa by Djer |Log4J| Contextualise tes messages
            log.error("Fail to get StoreData", e);
            throw new ServiceException("An error occured while retrieve Google StoreDara", e);
        }
        for (String name : googleAccountNames) {
            StoredCredential googleAccount = googleMap.get(name);
            if (googleAccount != null) {
                AccountData account = new AccountData();
                account.setUserKey(appUser.getUserKey());
                account.setAccountName(name);
                account.setAccessToken(googleAccount.getAccessToken());
                account.setRefreshToken(googleAccount.getRefreshToken());
                account.setExpirationTimeMilliseconds(googleAccount.getExpirationTimeMilliseconds());
                account.setAccountType(Type.Google);
                accounts.add(account);
            }
        }
    }
    /**
     * Convert seconds to milliseconds.
     * @param seconds seconds
     * @return milliseconds.
     */
    private long convertSecondsToMilliseconds(final long seconds) {
        return seconds * ONE_THOUSAND;
    }
}
