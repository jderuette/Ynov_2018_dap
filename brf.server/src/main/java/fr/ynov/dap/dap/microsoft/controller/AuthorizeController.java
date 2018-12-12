package fr.ynov.dap.dap.microsoft.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.IdToken;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.dap.microsoft.service.OutlookService;
import fr.ynov.dap.dap.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.microsoft.service.OutlookUser;

/**
 * @author Florian
 */
@Controller
public class AuthorizeController {

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
     * @param code .
     * @param idToken .
     * @param state .
     * @param request .
     * @param model .
     * @return la page html de mail
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request, final Model model) {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
        final String accountName = (String) session.getAttribute("accountName");
        final String userKey = (String) session.getAttribute("userKey");
        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                LOG.debug("Le TokenResponse : " + tokenResponse);
                session.setAttribute("tokens", tokenResponse);
                session.setAttribute("userConnected", true);
                session.setAttribute("userName", idTokenObj.getName());
                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken());
                OutlookUser user;
                try {
                    user = outlookService.getCurrentUser().execute().body();
                    session.setAttribute("userEmail", user.getMail());
                } catch (IOException e) {
                    session.setAttribute("error", e.getMessage());
                }
                session.setAttribute("userTenantId", idTokenObj.getTenantId());

                MicrosoftAccountData account = new MicrosoftAccountData();
                account.setAccountName(accountName);
                account.setTokenResponse(tokenResponse);
                account.setTenantId(idTokenObj.getTenantId());
                AppUser appuser = appUserRepository.findByName(userKey);
                appuser.getAccounts().stream().anyMatch(u -> u.getAccountName() == accountName);
                appuser.addMicrosoftAccount(account);
                appUserRepository.save(appuser);

                model.addAttribute("authCode", code);
                model.addAttribute("idToken", idToken);
            } else {
                session.setAttribute("error", "ID token failed validation.");
            }
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }

        return "redirect:/mail?userKey=" + userKey;
    }

    /**
     * @param request .
     * @return sur la page html index
     */
    @RequestMapping("/logout")
    public String logout(final HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
