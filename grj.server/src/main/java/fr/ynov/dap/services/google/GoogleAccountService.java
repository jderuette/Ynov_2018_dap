package fr.ynov.dap.services.google;

import com.google.api.client.auth.oauth2.*;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.models.common.User;
import fr.ynov.dap.models.google.GoogleAccount;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * GoogleAccountService
 */
@Service
public class GoogleAccountService extends GoogleHelper {

    /**
     * Autowired GoogleHelper
     */
    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger  LOG                      = LogManager.getLogger(GoogleAccountService.class);
    private static final String  OAUTH_CALLBACK_URL       = "/oAuth2Callback";
    private static final Integer SENSIBLE_DATA_FIRST_CHAR = 3;
    private static final Integer SENSIBLE_DATA_LAST_CHAR  = 8;

    /**
     * Add a google account to a user
     *
     * @param request Http Request
     * @param session Http Session
     * @return Response
     * @throws ServletException Exception
     */
    public String addGoogleAccountToUser(final HttpServletRequest request, final HttpSession session) throws ServletException {

        String       userName;
        String       googleAccountName;
        final String decodedCode = googleHelper.extractCode(request);
        final String redirectUri = googleHelper.buildRedirectUri(request, OAUTH_CALLBACK_URL);

        if (session != null && session.getAttribute("userName") != null && session.getAttribute("googleAccount") != null) {
            userName = (String) session.getAttribute("userName");
            googleAccountName = (String) session.getAttribute("googleAccount");
        } else {
            LOG.error("GoogleAccount in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google account : userKey is NULL is User Session");
        }

        try {
            final GoogleAuthorizationCodeFlow flow     = googleHelper.getFlow();
            final TokenResponse               response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            //TODO grj by Djer |API Google| Ne fait pas la sauvegahrde, l'utilsiateur peut refuser de "partager ces données" et tu auras un "compte fantome". Fait le dans le callback
            User user = userRepository.findByName(userName);

            GoogleAccount googleAccount = new GoogleAccount();
            googleAccount.setName(googleAccountName);
            user.addGoogleAccount(googleAccount);
            userRepository.save(user);

            final Credential credential = flow.createAndStoreCredential(response, googleAccountName);
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + googleAccountName);
            }

            if (LOG.isDebugEnabled() && (null != credential && null != credential.getAccessToken())) {
                LOG.debug("New user credential stored with userId : " + googleAccountName + "partial AccessToken : "
                        + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                        SENSIBLE_DATA_LAST_CHAR));
            }

        } catch (IOException e) {
            //TODO grj by Djer |log4J| Ajoute du contexte (" for Google account : " + googleAccountName)
            LOG.error("Exception while trying to store user AccountCredential", e);
        }

        LOG.info("Google account " + googleAccountName + " is successfully added to user " + userName);

        return "redirect:/user-success";

    }


}
