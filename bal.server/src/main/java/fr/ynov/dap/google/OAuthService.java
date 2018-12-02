package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;


/**
 * The Class OAuthService.
 */
//FIXME bal by Djer |IOC| N'est pas un bean vue par Spring, ne peu pas être injecté !! LE parent non plus n'est pas un Bean d'ou le mesage d'erreur au lancement du serveur "Error creating bean with name 'calendarService': Unsatisfied dependency expressed through field 'oAuthService'"
@Service
public class OAuthService extends GoogleService<Oauth2>{
	
	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getClassName()
	 */
	@Override
    protected final String getClassName() {
        return OAuthService.class.getName();
	}

	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getGoogleClient(com.google.api.client.auth.oauth2.Credential, com.google.api.client.http.javanet.NetHttpTransport, java.lang.String)
	 */
	@Override
	protected final Oauth2 getGoogleClient(final Credential credential, final NetHttpTransport httpTransport, 
			final String appName) {
		return new Oauth2.Builder(httpTransport, getJSON_FACTORY(), credential).setApplicationName(appName).build();
	}
   
    /**
     * Gets the user info.
     *
     * @param userId the user id
     * @return the user info
     * @throws GeneralSecurityException the general security exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Userinfoplus getUserInfo(final String userId) throws GeneralSecurityException, IOException {

        Oauth2 srv = getService(userId);
        return srv.userinfo().get().execute();

    }

}
