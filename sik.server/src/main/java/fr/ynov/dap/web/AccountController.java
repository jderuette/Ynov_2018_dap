package fr.ynov.dap.web;

import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dto.out.LoginResponseOutDto;
import fr.ynov.dap.google.GoogleAccountService;

/**
 * Controller to manage every call to Google Account API.
 * @author Kévin Sibué
 *
 */
@RestController
public class AccountController extends BaseController {

    /**
     * Instance of GoogleAccount service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Return calendar service instance.
     * @return CalendarService instance
     */
    private GoogleAccountService getGoogleAccountService() {
        return googleAccountService;
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    @RequestMapping("/account/add/{userId}")
    public LoginResponseOutDto addAccount(@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) throws GeneralSecurityException {

        LoginResponseOutDto outDto = getGoogleAccountService().addAccount(userId, request);

        return outDto;

    }

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException, GeneralSecurityException {

        return getGoogleAccountService().oAuthCallback(code, request);

    }

    @Override
    protected final String getClassName() {
        return AccountController.class.getName();
    }

}
