package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.GoogleAccountService;
import fr.ynov.dap.dap.model.AccountResponse;

/**
 * used to connect or retrieve token for an userID.
 *
 * @author David_tepoche
 *
 */
@RestController
public class AccountController extends BaseController {

    /**
     * link googleAccount service.
     */
    @Autowired
    private GoogleAccountService googleAccount;

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     *
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     * @throws GeneralSecurityException throw if the addCount fail
     * @throws IOException              throw if the call from accountService fail
     */
    @GetMapping("/account/add/{userId}")
    public AccountResponse addAccount(@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) throws GeneralSecurityException, IOException {
        return googleAccount.addAccount(userId, request, session);
    }

    /**
     * retrieve the tojken send by google and store it.
     *
     * @param code    code return from google service
     * @param request the resquet given
     * @param session session open
     * @return redirection to google.
     * @throws ServletException throw if the call from googleAccount fail
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException {
        return googleAccount.oAuthCallback(code, request, session);
    }

    @Override
    public final String getClassName() {
        return AccountController.class.getName();
    }

}
