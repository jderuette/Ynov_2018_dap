package fr.ynov.dap.microsoft.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.microsoft.AuthHelper;

/**
 *
 * @author Dom
 *
 */
@Controller
public class IndexController {
    /**
     *
     * @param model .
     * @param request .
     * @return .
     */
    @RequestMapping("/index")
    public String index(final Model model, final HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);
        // Name of a definition in WEB-INF/defs/pages.xml
        return "index";
    }
}
