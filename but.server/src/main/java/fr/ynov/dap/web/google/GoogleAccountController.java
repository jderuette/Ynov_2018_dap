package fr.ynov.dap.web.google;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.web.HandlerErrorController;

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
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws HttpResponseException when bad request.
     * @throws UnsupportedEncodingException if UTF-8 is not supported.
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session)
                    throws ServletException, HttpResponseException, UnsupportedEncodingException {

        String userKey = googleAccountService.getInSession(GoogleAccountService.USER_KEY, session);
        String accountName = googleAccountService.getInSession(GoogleAccountService.ACCOUNT_NAME_KEY, session);

        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        googleAccountService.oAuthCallback(code, request, accountName, user);

        /** if (success) {
            GoogleAccount gAccount = new GoogleAccount();
            gAccount.setOwner(user);
            gAccount.setAccountName(accountName);
            user.addGoogleAccount(gAccount);
            repositoryUser.save(user);
        } */

        session.setAttribute("sucess", "Google account added.");
        return "redirect:/";
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param accountName  the user to store Data
     * @param userKey the owner (user key) of this google account.
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws IOException If the credentials.json file cannot be found or bad request.
     * @throws GeneralSecurityException Security on Google API
     */
    @RequestMapping("/add/account/google/{accountName}")
    public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
            final HttpServletRequest request, final HttpSession session)
                    throws IOException, GeneralSecurityException {
        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        String redirectUri = googleAccountService.getAddAccountGoogleRedirectURI(
                accountName, userKey, request, session
            );

        return "redirect:" + redirectUri;
    }
}
