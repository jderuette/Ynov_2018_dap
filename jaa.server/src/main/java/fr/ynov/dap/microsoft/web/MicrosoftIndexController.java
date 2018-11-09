package fr.ynov.dap.microsoft.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.microsoft.auth.AuthHelper;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author adrij
 *
 */
@Controller
public class MicrosoftIndexController {

    /**
     * Default constructor.
     */
    public MicrosoftIndexController() {
    }

    @RequestMapping("/microsoftIndex")
    public String microsoftIndex(final ModelMap model, final HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);

        Integer nbUnreadEmail = 5;
        model.addAttribute("nbEmails", nbUnreadEmail);

        return "microsoftIndex";
    }
}
