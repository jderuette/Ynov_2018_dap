package fr.ynov.dap.web.microsoft;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.microsoft.IdToken;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.data.microsoft.TokenResponseRepository;
import fr.ynov.dap.microsoft.AuthHelper;

@Controller
public class MicrosoftAccountController {
    /**
     * Start of substring for token.
     */
    private static final int SENSIBLE_DATA_FIRST_CHAR = 5;
    /**
     * End of substring for token.
     */
    private static final int SENSIBLE_DATA_LAST_CHAR = 10;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * User repository.
     */
    @Autowired
    private AppUserRepository userRepository;

    /**
     * Token repository.
     */
    @Autowired
    private TokenResponseRepository tokenRepository;

    /**
     * return helloWorld template.
     * @param accountName account.
     * @param session session
     * @param userKey userKey
     * @param request http request
     * @return helloWorld template
     */
    @RequestMapping("/account/add/microsoft/{accountName}")
    public String index(@PathVariable final String accountName, @RequestParam final String userKey,
            final HttpServletRequest request, final HttpSession session) {

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        session.setAttribute("accountName", accountName);
        session.setAttribute("userKey", userKey);
        String response = "redirect:" + loginUrl;

        return response;
    }

    /**
     * Redirection url after microsoft authent.
     * @param code authent code
     * @param idToken token
     * @param state authent state
     * @param model model
     * @param request http request
     * @return html page to send
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final ModelMap model, final HttpServletRequest request) {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
        String accountName = (String) session.getAttribute("accountName");
        String userKey = (String) session.getAttribute("userKey");
        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                tokenRepository.save(tokenResponse);

                AppUser user = userRepository.findByName(userKey);
                user.addMicrosoftAccount(new MicrosoftAccount(accountName, tokenResponse, idTokenObj.getTenantId()));
                userRepository.save(user);
                LOGGER.debug("Utilisateur crée en base", "Token : "
                        + tokenResponse.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));

            } else {
                LOGGER.error("Impossible de valider le token reçu", "ID token failed validation.");
            }
        } else {
            LOGGER.error("Erreur lors de la connexion", "Unexpected state returned from authority.");
        }
        return "redirect:/userCreated";
    }
}
