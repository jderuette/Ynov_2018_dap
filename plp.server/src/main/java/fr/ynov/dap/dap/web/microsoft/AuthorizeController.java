package fr.ynov.dap.dap.web.microsoft;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
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
            session.setAttribute("authCode", code);
            session.setAttribute("idToken", idToken);
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }
        Map<String, String> response = new HashMap<>();
        response.put("authCode", code);
        response.put("idToken", idToken);
        return response;
    }
}
