package fr.ynov.dap.microsoft.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.microsoft.auth.AuthHelper;

/**
 * @author Mon_PC
 */
@Controller
public class IndexController {
    /**.
     * Constructeur class
     */
    public IndexController() {
    }

    /**
     * @param model nous permettant d'envoyer des paramètres à notre vue
     * @param request pour gérer les requêtes http servlet
     * @return la vue index
     */
    @RequestMapping("/")
    public String index(final ModelMap model, final HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);

        return "index";
    }
}
