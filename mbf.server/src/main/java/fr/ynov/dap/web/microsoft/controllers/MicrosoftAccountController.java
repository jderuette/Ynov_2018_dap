package fr.ynov.dap.web.microsoft.controllers;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

public class MicrosoftAccountController {

    /**
     * Add a google account (user will be prompt to connect and accept required
     * access).
     * @param userKey  the login of the user.
     * @param request the HTTP request
     * @param session the HTTP session
     * @return the view to Display (on Error)
     */
    @RequestMapping("/account/add/microsoft/{accountName}")
    public final String addAccount(@PathVariable final String accountName, @RequestParam("userKey") final String userKey, final HttpServletRequest request,
                                   final HttpSession session) {
        //FIXME mbf by Djer |API Microsoft| C'est ennuyeux :/
        return "API route ot implemented yet";
    }
}
