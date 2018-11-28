package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;

/**
 * The Class OAuthService.
 */
public class OAuthService extends GoogleService<Oauth2>{
	
	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	@Override
    protected final String getClassName() {
        return OAuthService.class.getName();
	}

	/**
	 * Gets the google client.
	 *
	 * @param credential the credential
	 * @param httpTransport the http transport
	 * @param appName the app name
	 * @return the google client
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
