package fr.ynov.dap.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.GoogleAccount;

/**
 * Controller to manage user.
 * @author thibault
 *
 */
@RestController
public class AppUserController extends HandlerErrorController {

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * GoogleAccount service.
     */
    @Autowired
    private GoogleAccount googleAccount;

    /**
     * Register a app user.
     * @param userKey  the userKey to create
     * @return the view to Display (on Error)
     */
    @RequestMapping("/user/add/{userKey}")
    public AppUser registerUser(@PathVariable("userKey") final String userKey) {
        AppUser user = new AppUser();
        user.setUserKey(userKey);

        return repositoryUser.save(user);
    }

    /**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException {

        googleAccount.oAuthCallback(code, request, session);

        return "redirect:/";
    }

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     * @param userId  the user to store Data
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     */
    @RequestMapping("/account/add/{userId}")
    public String addAccount(@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) {
        return googleAccount.addAccount(userId, request, session);
    }
}
