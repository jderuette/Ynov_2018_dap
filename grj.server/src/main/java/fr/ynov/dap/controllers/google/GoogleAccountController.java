package fr.ynov.dap.controllers.google;

import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.google.GoogleAccountService;
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

    /**
     * Autowired GoogleAccountService
     */
    @Autowired
    private GoogleAccountService googleAccountService;

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Autowired GoogleHelper
     */
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
            //TODO grj by Djer |POO| Ce n'est aps top de passer des objet "Web" (Request/session) à test services. essaye d'extraire les informations dans le controller et de passer des objets "non Web" a tes services
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
            //TODO grj by Djer |POO| Tu pourais extraire les données des objets "Web" en plus ca te ferait du contexte pour la LOG d'erreur ! 
            response = googleAccountService.addGoogleAccountToUser(request, session);
        } catch (ServletException e) {
            LOGGER.error("Error when trying to store google account", e);
        }

        return response;
    }
}
