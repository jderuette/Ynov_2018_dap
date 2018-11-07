package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.javanet.NetHttpTransport;

import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.exception.MissingSessionParameterException;
import fr.ynov.dap.model.AddAccountResult;

/**
 * Controller to manage google accounts.
 * @author Kévin Sibué
 *
 */
@Service
public class GoogleAccountService extends GoogleAPIService {

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Handle the Google response.
     * @param code    The (encoded) code use by Google (token, expirationDate, ...)
     * @param userId User Id
     * @param gAccountName Google Account Name
     * @param decodedCode Decoded Code
     * @param redirectUri Redirection Uri
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws MissingSessionParameterException Thrown when a session parameter missing.
     */
    public String oAuthCallback(final String code, final String userId, final String gAccountName,
            final String decodedCode, final String redirectUri)
            throws ServletException, GeneralSecurityException, MissingSessionParameterException {

        if (userId == null || gAccountName == null || decodedCode == null || redirectUri == null) {
            throw new MissingSessionParameterException();
        }

        try {

            final GoogleAuthorizationCodeFlow flow = super.getFlows();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, gAccountName);
            if (null == credential || null == credential.getAccessToken()) {
                getLogger().warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (null != credential && null != credential.getAccessToken()) {

                AppUser user = appUserRepository.findByUserKey(userId);

                GoogleAccount gAccount = new GoogleAccount();
                gAccount.setAccountName(gAccountName);

                user.addGoogleAccount(gAccount);

                appUserRepository.save(user);

                getLogger().debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                        + credential.getAccessToken());
                //.substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));

                return "Vous êtes connecté au service DaP";

            }

        } catch (IOException e) {

            getLogger().error("Exception while trying to store user Credential", e);

            throw new ServletException("Error while trying to conenct Google Account");

        }

        return "Unknow state";

    }

    @Override
    protected final String getClassName() {
        return GoogleAccountService.class.getName();
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param accountName this user account name used to link new google account
     * @param userId  the user to store Data
     * @param redirectUri Redirection Url
     * @return the success or not of the action.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public AddAccountResult addAccount(final String accountName, final String userId, final String redirectUri)
            throws GeneralSecurityException {

        AddAccountResult addAccountResult = new AddAccountResult();

        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;

        try {

            flow = super.getFlows();

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

            String error = "Error while loading credential (or Google Flow)";

            getLogger().error(error, e);

            addAccountResult.setIsSuccess(false);
            addAccountResult.setErrorDescription(error);

            return addAccountResult;

        }

    }

    @Override
    protected final Object getGoogleClient(final NetHttpTransport httpTransport, final Credential cdt,
            final String appName) {
        return null;
    }

}
