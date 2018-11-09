package fr.ynov.dap.microsoft.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.IdToken;
import fr.ynov.dap.microsoft.auth.TokenResponse;

/**
 * @author adrij
 *
 */
@Controller
public class AuthorizeController {

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(final ModelMap model, @RequestParam("code") String code,
            @RequestParam("id_token") String idToken, @RequestParam("state") UUID state, HttpServletRequest request) {

        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                session.setAttribute("tokens", tokenResponse);
                session.setAttribute("userConnected", true);
                session.setAttribute("userName", idTokenObj.getName());
                session.setAttribute("userTenantId", idTokenObj.getTenantId());

                model.addAttribute("authCode", code);
                model.addAttribute("idToken", idToken);
                model.addAttribute("accessToken", tokenResponse);

            } else {
                session.setAttribute("error", "ID token failed validation.");
                model.addAttribute("error", "ID token failed validation.");
            }
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
            model.addAttribute("error", "Unexpected state returned from authority.");
        }

        return "microsoftMail";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/index.html";
    }
}
