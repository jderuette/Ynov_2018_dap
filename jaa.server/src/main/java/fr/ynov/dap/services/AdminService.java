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
 * TODO Extract methods.
 */
@Service
public class AdminService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

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
     * @throws GeneralSecurityException exception.
     * @throws IOException exception.
     */
    public List<AccountData> getAccountsInformations() throws ServiceException {
        log.info("Accounts informations requested by the admin.");
        List<AccountData> accounts = new ArrayList<AccountData>();

        Iterable<AppUser> appUsers = repository.findAll();

        for (AppUser appUser : appUsers) {
            // Google
            List<String> googleAccountNames = appUser.getGoogleAccountNames();
            Map<String, StoredCredential> googleMap;
            try {
                googleMap = googleDataStoreService.getStoreCredentialMap();
            } catch (GeneralSecurityException | IOException e) {
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

            // Microsoft
            List<MicrosoftAccount> microsoftAccounts = appUser.getMicrosoftAccounts();
            for (MicrosoftAccount microsoftAccount : microsoftAccounts) {
                AccountData account = new AccountData();
                account.setUserKey(appUser.getUserKey());
                account.setAccountName(microsoftAccount.getAccountName());
                try {
                    account.setAccessToken(microsoftAccount.getTokenResponse().getAccessToken());
                    account.setRefreshToken(microsoftAccount.getTokenResponse().getRefreshToken());
                    account.setExpirationTimeMilliseconds(microsoftAccount.getIdToken().getExpirationTime() * 1000);
                    account.setTenantId(microsoftAccount.getIdToken().getTenantId());
                } catch (IOException e) {
                    log.error("Error occured during the microsoftAccount reading", e);
                    throw new ServiceException("Faild to get some properties of Microsoft Account", e);
                }
                account.setAccountType(Type.Microsoft);
                accounts.add(account);
            }
        }

        return accounts;
    }
}
