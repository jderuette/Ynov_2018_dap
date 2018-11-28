package fr.ynov.dap.dap;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.data.microsoft.OutlookAccount;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@RestController
public class DataStoreService {
    private GoogleService googleService = new GoogleService();

    @Autowired
    AppUserRepository userRepository;

    public DataStore<StoredCredential> getDataStore() throws IOException, GeneralSecurityException {
        return googleService.getFlow().getCredentialDataStore();
    }

    public List<Map> getInterfaceAdmin() {
        List<Map> response = new ArrayList<>();
        Iterable<AppUser> users = userRepository.findAll();

        for (AppUser currentUser : users) {
            Map<String, Object> user = new HashMap<>();

            List<Map> listAccount = new ArrayList<>();
            for (GoogleAccount googleAccount: currentUser.getGoogleAccount()) {
                Map<String, String> account = new HashMap<>();
                account.put("name", googleAccount.getName());
                account.put("type", "google");
                account.put("tenantId","");
                listAccount.add(account);
            }
            for (OutlookAccount outlookAccount: currentUser.getOutlookAccount()) {
                Map<String, String> account = new HashMap<>();
                account.put("name", outlookAccount.getName());
                account.put("type", "microsoft");
                account.put("tenantId", outlookAccount.getTenantId());
                listAccount.add(account);
            }

            user.put("userKey", currentUser.getName());
            user.put("accounts",listAccount);
            response.add(user);
        }

        return response;
    }
}
