package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.servlet.ServletException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

/**
 * The Class GoogleAccount.
 */
@Service
public class GoogleAccountService extends GoogleService {
	
	/** The sensible data first char. */
	private final int SENSIBLE_DATA_FIRST_CHAR = 0;

	/** The sensible data last char. */
	private final int SENSIBLE_DATA_LAST_CHAR = 1;
	
	/** The google account repo. */
	@Autowired
	private GoogleAccountRepository googleAccountRepo;
	
	/** The app user repo. */
	@Autowired
	private AppUserRepository appUserRepo;
	
	/** The log. */
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	/* (non-Javadoc)
	 * @see fr.ynov.dap.google.GoogleService#getClassName()
	 */
	@Override
    protected final String getClassName() {
        return GoogleAccountService.class.getName();
    }
	
	/**
	 * O auth callback.
	 *
	 * @param code the code
	 * @param decodedCode the decoded code
	 * @param redirectUri the redirect uri
	 * @param accountName the account name
	 * @param userKey the user key
	 * @return the string
	 * @throws ServletException the servlet exception
	 * @throws GeneralSecurityException the general security exception
	 */
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
			//FIXME bal by Djer |JPA| Attention tu ne sauvegarde pas l'entité qui est "maître" de la relation, c'est risqué. Tu devrais faire un save du "appUser", après l'avoir mis à jours, avec le UserRepository (JPA s'ocucpera de créer/mettre à jours les entités filles, notament l'ajout de ton "GoogleAccount")
			googleAccountRepo.save(googleAccount);

		} catch (IOException e) {
		    //TODO bal by Djer |Log4J| Contextualise tes logs ! (" for user : " + userKey + " and accountName : " + accountName))
			LOG.error("Exception while trying to store user Credential", e);
			throw new ServletException("Error while trying to connect Google Account");
		}

		return "Connecté ";
	}

    /**
     * Adds the account.
     *
     * @param accountName the account name
     * @param userId the user id
     * @param redirectUri the redirect uri
     * @return the adds the account response
     * @throws GeneralSecurityException the general security exception
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
     * Gets the stored credentials.
     *
     * @return the stored credentials
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
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

                //TODO bal by Djer |Gestion Exception| e.printStackTrace() affiche dans la console, pas très utile sur un serveur. Utiliser une LOG.
                e.printStackTrace();

            }

        });
        return credentials;
    }
    
    /* (non-Javadoc)
     * @see fr.ynov.dap.google.GoogleService#getGoogleClient(com.google.api.client.auth.oauth2.Credential, com.google.api.client.http.javanet.NetHttpTransport, java.lang.String)
     */
    @Override
    protected final Object getGoogleClient(final Credential credential, final NetHttpTransport httpTransport, final String appName) {
        return null;
    }

}
