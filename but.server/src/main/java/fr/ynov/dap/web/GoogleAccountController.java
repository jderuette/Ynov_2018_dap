package fr.ynov.dap.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.tomcat.util.codec.binary.Base64;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.google.GoogleAccountService;

/**
 * Controller to manage google user.
 * @author thibault
 *
 */
@Controller
public class GoogleAccountController extends HandlerErrorController {

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * GoogleAccount service.
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param state state of request (data userKey & accountName).
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws HttpResponseException when bad request.
     * @throws UnsupportedEncodingException if UTF-8 is not supported.
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, @RequestParam final String state,
            final HttpServletRequest request)
                    throws ServletException, HttpResponseException, UnsupportedEncodingException {
        JSONObject stateJson = new JSONObject(new String(Base64.decodeBase64(state), "UTF-8"));

        String userKey = stateJson.getString("userKey");
        if (userKey == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "userKey in state is null.");
        }

        String accountName = stateJson.getString("accountName");
        if (accountName == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "accountName in state is null.");
        }
        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        Boolean success = googleAccountService.oAuthCallback(code, request, accountName);

        if (success) {
            GoogleAccount gAccount = new GoogleAccount();
            gAccount.setOwner(user);
            gAccount.setAccountName(accountName);
            user.addGoogleAccount(gAccount);
        }

        return "redirect:/";
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param accountName  the user to store Data
     * @param userKey the owner (user key) of this google account.
     * @param request the HTTP request
     * @return the view to Display (on Error)
     * @throws IOException If the credentials.json file cannot be found or bad request.
     * @throws GeneralSecurityException Security on Google API
     */
    @RequestMapping("/add/account/{accountName}")
    public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
            final HttpServletRequest request) throws IOException, GeneralSecurityException {
        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        String redirectUri = googleAccountService.getAddAccountGoogleRedirectURI(accountName, userKey, request);

        return "redirect:" + redirectUri;
    }
}
