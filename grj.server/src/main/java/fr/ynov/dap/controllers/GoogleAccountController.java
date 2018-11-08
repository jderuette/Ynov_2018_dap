package fr.ynov.dap.controllers;

import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.GoogleAccountService;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * GoogleAccountController
 */
@Controller
public class GoogleAccountController extends GoogleHelper {

    @Autowired
    private GoogleAccountService googleAccountService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleHelper googleHelper;

    private static final Logger LOGGER             = LogManager.getLogger(GoogleAccountController.class);
    private static final String OAUTH_CALLBACK_URL = "/oAuth2Callback";

    /**
     * Add a Google account (user will be prompt to connect and accept required
     * access).
     *
     * @param googleAccountName the user to store Data
     * @param request           the HTTP request
     * @param session           the HTTP session
     * @return the view to Display (on Error)
     */
    @RequestMapping("/user/{userName}/add/google-account/{googleAccountName}")
    public String addAccount(@PathVariable final String userName, @PathVariable final String googleAccountName, final HttpServletRequest request,
                             final HttpSession session) {

        boolean isUserExists = userRepository.findByName(userName) != null;

        if (isUserExists) {
            return googleHelper.loadCredential(request, session, userName, googleAccountName);
        } else {
            return "redirect:/user-does-not-exist";
        }
    }

    /**
     * Handle the Google response.
     *
     * @param request The HTTP Request
     * @param session the HTTP Session
     * @return the view to display
     */
    @RequestMapping(OAUTH_CALLBACK_URL)
    public String oAuthCallback(final HttpServletRequest request, final HttpSession session) {

        String response = null;
        try {
            response = googleAccountService.addGoogleAccountToUser(request, session);
        } catch (ServletException e) {
            LOGGER.error("Error when trying to store google account", e);
        }

        return response;
    }
}
