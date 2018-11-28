package fr.ynov.dap.global.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.repository.MicrosoftAccountRepository;
import fr.ynov.dap.model.CredentialModel;
import fr.ynov.dap.service.GoogleService;

/**
 * @author Mon_PC
 */
@Service
public class AdminService extends GoogleService {

    /**.
     * repositoryMicrosoftAccountData is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

    /**.
     * constructeur class
     * @throws Exception exception levée
     * @throws IOException exception levée
     */
    public AdminService() throws Exception, IOException {
        super();
    }

    /**
     * @return map de dataStore credentials
     * @throws IOException exception levée
     * @throws GeneralSecurityException exception levée
     */
    public List<CredentialModel> getDataStoreGoogle() throws IOException, GeneralSecurityException {

        DataStore<StoredCredential> storedCredential = super.getFlow().getCredentialDataStore();
        List<CredentialModel> listModelGoogle = new ArrayList<CredentialModel>();
        CredentialModel modelGoogle = new CredentialModel();

        for (String key : storedCredential.keySet()) {
            modelGoogle.setToken(storedCredential.get(key).getAccessToken());
            modelGoogle.setType("Google");
            listModelGoogle.add(modelGoogle);
        }
        return listModelGoogle;
    }

    /**
     * @return all Microsoft account data
     */
    public List<CredentialModel> getDataStoreMicrosoft() {

        Iterable<MicrosoftAccountData> listMicrosoftData = microsoftAccountRepository.findAll();
        List<CredentialModel> listModelMicrosoft = new ArrayList<CredentialModel>();
        CredentialModel modelMicrosoft;

        for (MicrosoftAccountData m : listMicrosoftData) {
            modelMicrosoft = new CredentialModel();
            modelMicrosoft.setToken(m.getToken().getAccessToken());
            modelMicrosoft.setAccountName(m.getAccountName());
            modelMicrosoft.setOwnerName(m.getOwner().getName());
            modelMicrosoft.setTenantId(m.getUserTenantId());
            modelMicrosoft.setUserEmail(m.getUserEmail());
            modelMicrosoft.setType("Microsoft");
            listModelMicrosoft.add(modelMicrosoft);
        }
        return listModelMicrosoft;
    }
}
