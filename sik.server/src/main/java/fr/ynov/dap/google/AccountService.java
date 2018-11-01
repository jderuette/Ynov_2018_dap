package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

/**
 * Class to manage Google's Account OAuths API.
 * @author Kévin Sibué
 *
 */
@Service
public class AccountService extends GoogleAPIService {

    /**
     * Create new OAuth service for user.
     * @param userId Current user
     * @return OAuth service
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public Oauth2 getService(final String userId) throws GeneralSecurityException, IOException {

        Credential cdt = getCredential(userId);

        if (cdt != null) {

            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            final String appName = getConfig().getApplicationName();

            return new Oauth2.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();

        }

        return null;

    }

    /**
     * Return UserInfo of current logged user.
     * @param userId User id use to store token
     * @return Information of current logged user
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     */
    public Userinfoplus getUserInfo(final String userId) throws GeneralSecurityException, IOException {

        Oauth2 srv = getService(userId);

        return srv.userinfo().get().execute();

    }

    @Override
    protected final String getClassName() {
        return AccountService.class.getName();
    }

}
