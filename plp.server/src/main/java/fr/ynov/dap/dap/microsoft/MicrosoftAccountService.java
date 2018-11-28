package fr.ynov.dap.dap.microsoft;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MicrosoftAccountService {
    /**
     * route of tuto microsoft with session
     *
     * @param model
     * @param request
     * @return
     */
    public Map<String, String> index(Model model, HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);
        Map<String, String> response = new HashMap<>();
        response.put("loginUrl", loginUrl);
        return response;
    }

    /**
     * add an account microsoft
     *
     * @param accountName :name account
     * @param userKey:    user name
     * @param request     : request
     * @return return a redirect
     */
    public String addAccount(final String accountName, final String userKey, final HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        session.setAttribute("userKey", userKey);
        session.setAttribute("accountName", accountName);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);

        return "redirect:" + loginUrl;
    }
}
