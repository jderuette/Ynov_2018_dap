package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/microsoft")
public class AuthorizeController {

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public Map<String, String> authorize(
            @RequestParam("code") String code,
            @RequestParam("id_token") String idToken,
            @RequestParam("state") UUID state,
            HttpServletRequest request) {
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
                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
                OutlookUser user;
                try {
                    user = outlookService.getCurrentUser().execute().body();
                    session.setAttribute("userEmail", user.getMail());
                } catch (IOException e) {
                    session.setAttribute("error", e.getMessage());
                }
                session.setAttribute("userTenantId", idTokenObj.getTenantId());
            } else {
                session.setAttribute("error", "ID token failed validation.");
            }
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }
        Map<String, String> response = new HashMap<>();
        response.put("authCode", code);
        response.put("idToken", idToken);
        return response;
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "logout";
    }
}
