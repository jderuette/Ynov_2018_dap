package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.GoogleAccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;

@Controller
public class GoogleAccountController {
    /**
     * Instantiate instance of PeopleGService.
     */
    private final GoogleAccountService googleAccountService;

    /**
     * instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger();

    @Autowired
    public GoogleAccountController(GoogleAccountService googleAccountService) {
        this.googleAccountService = googleAccountService;
    }

    /**
     * Handle the Google response.
     *
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException         When Google account could not be connected to DaP.
     * @throws GeneralSecurityException throw exception.
     */
    @RequestMapping("/oAuth2Callback")
    public final String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
                                      final HttpSession session) throws ServletException, GeneralSecurityException {
        return googleAccountService.oAuthCallback(code, request, session);
    }

    @RequestMapping("/account/add/{userId}")
    public final String addAccount(@PathVariable final String userId, final HttpServletRequest request,
                                   final HttpSession session) throws GeneralSecurityException {
        return googleAccountService.addAccount(userId, request, session);
    }

}
