package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;


public class OAuthService extends GoogleService<Oauth2>{
	
	@Override
    protected final String getClassName() {
        return OAuthService.class.getName();
	}

	@Override
	protected final Oauth2 getGoogleClient(final Credential credential, final NetHttpTransport httpTransport, 
			final String appName) {
		return new Oauth2.Builder(httpTransport, getJSON_FACTORY(), credential).setApplicationName(appName).build();
	}
   
    public Userinfoplus getUserInfo(final String userId) throws GeneralSecurityException, IOException {

        Oauth2 srv = getService(userId);

        return srv.userinfo().get().execute();

    }

}
