package fr.ynov.dap.web.microsoft.controllers;

import fr.ynov.dap.data.microsoft.MicrosoftHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class HomeController {

    @RequestMapping("/")
    public final String login(ModelMap model, HttpServletRequest request) {

        model.addAttribute("current","");
        model.addAttribute("userConnected",false);

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = MicrosoftHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);
        return "home";
    }
}
