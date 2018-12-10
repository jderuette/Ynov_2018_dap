package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * Controller to manage google accounts.
 * @author Robin DUDEK
 *
 */
@Service
public class GoogleAccountService extends GoogleService {

    @Override
    protected final String getClassName() {
        return GoogleAccountService.class.getName();
    }

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository appUserRepository;


    /**
     * Handle the Google response.
     * @param code    The (encoded) code use by Google (token, expirationDate, ...)
     * @param userKey User Id
     * @param gAccountName Google Account Name
     * @param decodedCode Decoded Code
     * @param redirectUri Redirection Uri
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws MissingSessionParameterException Thrown when a session parameter missing.
     */
    public String oAuthCallback(final String code, final String userKey, final String accountName,
            final String decodedCode, final String redirectUri)
            throws ServletException, GeneralSecurityException {

        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);
            if (null == credential || null == credential.getAccessToken()) {
                LOGGER.warn("Trying to store a NULL AccessToken for user : " + accountName);
            }

            if (null != credential && null != credential.getAccessToken()) {

                AppUser user = appUserRepository.findByUserKey(userKey);

                GoogleAccount gAccount = new GoogleAccount();
                gAccount.setAccountName(userKey);

                user.addGoogleAccount(gAccount);

                appUserRepository.save(user);

                LOGGER.debug("New user credential stored with userId : " + userKey + "partial AccessToken : "
                        + credential.getAccessToken());
                return "redirect:/";
            }

        } catch (IOException e) {

            //TODO dur by Djer |Log4J| ajoute du contexte dans tes messages
            LOGGER.error("Exception while trying to store user Credential", e);

            throw new ServletException("Error while trying to conenct Google Account");

        }

        return "Unknow state";

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
    public String addAccount(final String accountName, final String userKey, String redirectUri,
            final HttpSession session) {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        Credential credential = null;
        try {
                flow = super.getFlow();
            credential = flow.loadCredential(accountName);
            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(redirectUri);
                session.setAttribute("accountName", accountName);
                session.setAttribute("userKey", userKey);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (Exception e) {
            LOGGER.error("Error while loading credential (or Google Flow)", e);
        }

        return response;
    }

    /**
     * Build a current host (and port) absolute URL.
     * @param req The current HTTP request to extract schema, host, port
     * @param destination the "path" to the resource
     * @return an absolute URI
     */
    public String buildRedirectUri(final HttpServletRequest req, final String destination) {
        final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
        url.setRawPath(destination);
        return url.build();
    }

}
