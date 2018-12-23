package fr.ynov.dap.dap.microsoft.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.microsoft.auth.AuthHelper;

/***
 * @author Florian
 */
@Controller
public class MicrosoftAccountController {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**
     * Déclaration de appUserRepository
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * @param model .
     * @param request .
     * @param accountName .
     * @param userKey .
     * @return la page html index
     */
    @RequestMapping("/add/microsoftAccount/{accountName}")
    public String index(@PathVariable final String accountName, @RequestParam("userKey") final String userKey,
            final Model model, final HttpServletRequest request) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        //TODO brf by Djer |Log4J| Contextualise tes messages et donne leur du sens
        LOG.debug(appUserRepository.findByName(userKey));
        if (appUserRepository.findByName(userKey) == null) {
            //TODO brf by Djer |MVC| Tu est dans un Controller (pas un **Rest**Controller) Spring va chercher unhe vue portant ce nom
            return "L'utilisateur " + userKey + " n'existe pas";
        } else {
            session.setAttribute("accountName", accountName);
            session.setAttribute("userKey", userKey);
            String loginUrl = AuthHelper.getLoginUrl(state, nonce);
            model.addAttribute("loginUrl", loginUrl);
            return "redirect:" + loginUrl;
        }
    }
}