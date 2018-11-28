package fr.ynov.dap.microsoft.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.IdToken;
import fr.ynov.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.OutlookUser;
import fr.ynov.dap.microsoft.repository.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Mon_PC
 */
@Controller
public class AuthorizeController {

    /**.
     * Log variable
     */
    private static final Logger LOG = LogManager.getLogger();

    /**.
     * repositoryUser récupère l'instance de AppUserRepository en cours
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**.
     * repositoryMicrosoft récupére l'instance de MicrosoftAccountRepository en cours
     */
    @Autowired
    private MicrosoftAccountRepository repositoryMicrosoft;

    /**
     * @param code .
     * @param idToken .
     * @param state .
     * @param request .
     * @param model .
     * @return authorize
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request, final ModelMap model) {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_none");
        String userId = (String) session.getAttribute("userId");
        String accountName = (String) session.getAttribute("accountName");

        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());

                AppUser newUser = repositoryUser.findByName(userId);

                if (null == newUser) {
                    LOG.error("Erreur", "Aucun utilisateur correspond à : " + userId);
                }

                MicrosoftAccountData microsoftAccount = new MicrosoftAccountData();
                microsoftAccount.setAccountName(accountName);
                microsoftAccount.setToken(tokenResponse);
                microsoftAccount.setUserTenantId(idTokenObj.getTenantId());

                session.setAttribute("tokens", tokenResponse);
                session.setAttribute("userConnected", true);
                session.setAttribute("userName", idTokenObj.getName());
                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(),
                        null);
                OutlookUser user;
                try {
                    user = outlookService.getCurrentUser().execute().body();
                    session.setAttribute("userEmail", user.getMail());
                    microsoftAccount.setUserEmail(user.getMail());
                    if (user.getMail() == null) {
                        microsoftAccount.setUserEmail(idTokenObj.getEmail());
                    }
                    if (null != microsoftAccount) {
                        newUser.addMicrosoftAccount(microsoftAccount);
                        repositoryUser.save(newUser);
                    }

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
        model.addAttribute("authCode", code);
        model.addAttribute("idToken", idToken);
        model.addAttribute("logout", "/logout");
        return "redirect:/admin";
    }

    /**
     * @param accountName Microsoft Account name
     * @param userKey User key
     * @param request Http request
     * @param session Http session
     * @param response Http response
     * @throws UserNotFoundException Exception
     * @throws IOException Exception
     */
    @RequestMapping(value = "/account/add/microsoft/{userKey}/{accountName}", method = RequestMethod.GET)
    public void addMicrosoftAccount(@PathVariable final String accountName, @PathVariable final String userKey,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response) {

        AppUser userAccount = repositoryUser.findByName(userKey);

        if (userAccount == null) {
            LOG.error("Erreur", "L'user renseigné n'existe pas");
        }

        if (null != repositoryMicrosoft.findByAccountName(accountName)) {
            LOG.error("Erreur", "L'accountName existe déjà");
        }

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        session.setAttribute("expected_state", state);
        session.setAttribute("expected_none", nonce);
        session.setAttribute("userId", userKey);
        session.setAttribute("accountName", accountName);

        String redirectUrl = AuthHelper.getLoginUrl(state, nonce);

        try {
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            LOG.error("Error", "Problème lors de la redirection", e.getMessage());
        }

    }

    /**
     * @param request servlet request
     * @param model modelMap
     * @param redirectAttributes redirectAttributes
     * @return vue index
     */
    @RequestMapping("/logout")
    public String logout(final HttpServletRequest request, final ModelMap model,
            final RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        session.invalidate();
        redirectAttributes.addFlashAttribute("logout", "Vous avez ete deconnecte");
        return "redirect:/";
    }
}
