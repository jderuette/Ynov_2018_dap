package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.model.AddAccountResponse;
import fr.ynov.dap.model.enumeration.CredentialEnum;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.GoogleAccountRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * The Class GoogleAccount.
 */
@Service
public class GoogleAccountService extends GoogleService {
	
	/** The sensible data first char. */
	private final int SENSIBLE_DATA_FIRST_CHAR = 0;

	/** The sensible data last char. */
	private final int SENSIBLE_DATA_LAST_CHAR = 1;

	@Autowired
	private GoogleAccountRepository googleAccountRepo;

	@Autowired
	private AppUserRepository appUserRepo;
	
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	@Override
    protected final String getClassName() {
        return GoogleAccountService.class.getName();
    }

	
	public String oAuthCallback(final String code,String decodedCode, String redirectUri, String accountName, String userKey)
			throws ServletException, GeneralSecurityException {
		try {
			final GoogleAuthorizationCodeFlow flow = super.getFlow();
			final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
			final Credential credential = flow.createAndStoreCredential(response, accountName);
			if (null == credential || null == credential.getAccessToken()) {
				LOG.warn("Trying to store a NULL AccessToken for user : " + accountName);
			}

			if (LOG.isDebugEnabled()) {
				if (null != credential && null != credential.getAccessToken()) {
					LOG.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
							+ credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
				}
			}

			GoogleAccount googleAccount = new GoogleAccount();
			AppUser appUser = appUserRepo.findByUserKey(userKey);
			googleAccount.setAccountName(accountName);
			appUser.addGoogleAccount(googleAccount);
			googleAccountRepo.save(googleAccount);

			// onSuccess(request, resp, credential);
		} catch (IOException e) {
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to connect Google Account");
		}

		return "Vous êtes connecté ";
	}

	/**
	 * Adds the account.
	 *
	 * @param userId  the user id
	 * @param request the request
	 * @param session the session
	 * @return the string
	 */
	/**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param accountName this user account name used to link new google account
     * @param userId  the user to store Data
     * @param redirectUri Redirection Url
     * @return the success or not of the action.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public AddAccountResponse addAccount(final String accountName, final String userId, final String redirectUri)
            throws GeneralSecurityException {

    	AddAccountResponse addAccountResult = new AddAccountResponse();
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(accountName);
            if (credential != null && credential.getAccessToken() != null) {
                addAccountResult.setIsSuccess(false);
                addAccountResult.setErrorDescription("Already Added");
                return addAccountResult;
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(redirectUri);
                addAccountResult.setIsSuccess(true);
                addAccountResult.setRedirectUrl(authorizationUrl.build());
                return addAccountResult;
            }

        } catch (IOException e) {

            addAccountResult.setIsSuccess(false);

            return addAccountResult;

        }

    }
	
	/**
     * Get every stored credential from Google credential file.
     * @return Google credentials
     * @throws NoConfigurationException No configuration available
     * @throws IOException Exception
     * @throws GeneralSecurityException Security exception
     */
    public final ArrayList<fr.ynov.dap.model.Credential> getStoredCredentials()
            throws IOException, GeneralSecurityException {

        DataStore<StoredCredential> flow = getFlow().getCredentialDataStore();
        ArrayList<fr.ynov.dap.model.Credential> credentials = new ArrayList<fr.ynov.dap.model.Credential>();

        flow.keySet().forEach(key -> {

            StoredCredential assocStoredCredential;

            try {

                assocStoredCredential = flow.get(key);

                fr.ynov.dap.model.Credential newCredential = new fr.ynov.dap.model.Credential();
                newCredential.setUserId(key);
                newCredential.setToken(assocStoredCredential.getAccessToken());
                newCredential.setRefreshToken(assocStoredCredential.getRefreshToken());
                newCredential.setExpirationTime(assocStoredCredential.getExpirationTimeMilliseconds());
                newCredential.setType(CredentialEnum.GOOGLE);

                credentials.add(newCredential);

            } catch (IOException e) {

                e.printStackTrace();

            }

        });

        return credentials;

    }
    
    @Override
    protected final Object getGoogleClient(final Credential credential, final NetHttpTransport httpTransport, final String appName) {
        return null;
    }

}
