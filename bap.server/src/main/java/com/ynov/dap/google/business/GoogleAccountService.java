package com.ynov.dap.google.business;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.ynov.dap.data.AppUser;
import com.ynov.dap.google.data.GoogleAccount;
import com.ynov.dap.google.repositories.GoogleAccountRepository;
import com.ynov.dap.repositories.AppUserRepository;

/**
 * Service for account.
 * @author POL
 */
@Service
public class GoogleAccountService extends GoogleService {

    /**
     * FIRST CHAR.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 0;

    /**
     * LAST CHAR.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 1;
    
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private GoogleAccountRepository googleAccountRepository;


    public String oAuthCallback(final String code, String decodedCode, String redirectUri, String userId) throws ServletException {
        try {
            GoogleAuthorizationCodeFlow flow = null;
            try {
                flow = super.getFlow();
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                getLogger().error("Error while loading credential (or Google Flow)", e);
            }
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, userId);
            if (null == credential || null == credential.getAccessToken()) {
            	getLogger().error("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (null != credential && null != credential.getAccessToken()) {
            	getLogger().error("New user credential stored with userId : " + userId + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                                    SENSIBLE_DATA_LAST_CHAR));
            }
        } catch (IOException e) {
        	getLogger().error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "redirect:/";
    }

    public String addAccount(final String userId, final String userKey, String redirectUri, HttpSession session) {
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow = null;
        Credential credential = null;

        AppUser user = appUserRepository.findByName(userKey);
        System.out.println(user.getName());

        GoogleAccount googleAccount = new GoogleAccount();
        googleAccount.setOwner(user);
        googleAccount.setName(userId);
        user.addGoogleAccount(googleAccount);
        googleAccountRepository.save(googleAccount);

        try {
            try {
                flow = super.getFlow();
            } catch (GeneralSecurityException e) {
                getLogger().error("Error while loading credential (or Google Flow)", e);
            }
            credential = flow.loadCredential(userId);

            if (credential != null && credential.getAccessToken() != null) {
                response = "AccountAlreadyAdded";
                getLogger().error("Error AccountAlreadyAdded");
            } else {
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri(redirectUri);
                session.setAttribute("userId", userId);
                response = "redirect:" + authorizationUrl.build();
            }
        } catch (IOException e) {
        	getLogger().error("Error while loading credential (or Google Flow)", e);
        }
        return response;
    }

    @Override
    public String getClassName() {
        return GoogleAccountService.class.getName();
    }

}
