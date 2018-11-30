package fr.ynov.dap.dap.google.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.DataStore;
import com.google.api.services.oauth2.Oauth2;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class UserInfoService extends GoogleBaseService {

    /**
     * get the auth2 with the matched credential.
     *
     * @param userId user key
     * @return the Oauth2 service
     * @throws GeneralSecurityException throws by getCredential from BaseService
     * @throws IOException              throws by getCredential from BaseService
     */
    private Oauth2 getService(final String userId) throws GeneralSecurityException, IOException {
        return new Oauth2.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY, getCredential(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

    @Override
    protected final String getClassName() {
        return UserInfoService.class.getName();
    }

    /**
     * get the email of the user.
     *
     * @param userId token identifier
     * @return email
     * @throws IOException              when retrieve the token fail.
     * @throws GeneralSecurityException error while you try to authenticated
     */
    //TODO duv by Djer |Audit Code| Ce "userId" est un AccountName je supose ? 
    public String getEmail(final String userId) throws IOException, GeneralSecurityException {
        return getService(userId).userinfo().get().execute().getEmail().toString();
    }

    /**
     * Get the credential stored from the application.
     *
     * @return the dataStoreCRedential
     * @throws IOException              throw if the get flow fail
     * @throws GeneralSecurityException throw if the get flow fail
     */
    public DataStore<StoredCredential> getCredentialDataStore() throws IOException, GeneralSecurityException {
        return getFlow().getCredentialDataStore();
    }

}
