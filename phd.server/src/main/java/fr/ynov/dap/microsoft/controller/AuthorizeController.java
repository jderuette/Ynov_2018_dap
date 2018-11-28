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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.IdToken;
import fr.ynov.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.OutlookUser;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;

/**
 *
 * @author Dom
 *
 */
@Controller
public class AuthorizeController {

    /**.
     * repositoryUser récupère l'instance de AppUserRepository en cours
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     *
     */
    private static final Logger LOG = LogManager.getLogger();

    /**
     *
     * @param microsoftAccountName .
     * @param userId .
     * @param request .
     * @param session .
     * @param response .
     * @throws IOException .
     */
    @RequestMapping(value = "/account/microsoft/add/{microsoftAccountName}/{userId}")
    public void addMicrosoftAccount(@PathVariable final String microsoftAccountName, @PathVariable final String userId,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
            throws IOException {

        AppUser userAccount = appUserRepository.findByName(userId);

        if (userAccount == null) {
            LOG.error("Error", "This user does not exist");
        }

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        session.setAttribute("userId", userId);
        session.setAttribute("accountName", microsoftAccountName);

        String redirectUrl = AuthHelper.getLoginUrl(state, nonce);

        response.sendRedirect(redirectUrl);

    }

    /**
     *
     * @param code .
     * @param idToken .
     * @param state .
     * @param request .
     * @param model .
     * @return .
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request, final Model model) {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
        // Make sure that the state query parameter returned matches
        // the expected state
        String userId = (String) session.getAttribute("userId");
        String accountName = (String) session.getAttribute("accountName");

        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                session.setAttribute("tokens", tokenResponse);
                session.setAttribute("userConnected", true);
                session.setAttribute("userName", idTokenObj.getName());

                AppUser currentUser = appUserRepository.findByName(userId);

                if (currentUser == null) {
                    LOG.error("Error", "The currentUser does not exits");
                }

                MicrosoftAccountData msAccount = new MicrosoftAccountData();
                msAccount.setUserTenantId(idTokenObj.getTenantId());
                msAccount.setTokens(tokenResponse);

                msAccount.setAccountName(accountName);

                currentUser.adMicrosoftAccount(msAccount);

                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(),
                        null);
                OutlookUser user;
                try {
                    user = outlookService.getCurrentUser().execute().body();
                    session.setAttribute("userEmail", user.getMail());

                    msAccount.setUserEmail(user.getMail());

                    if (null != msAccount) {
                        currentUser.adMicrosoftAccount(msAccount);
                        appUserRepository.save(currentUser);
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
        model.addAttribute("logoutUrl", "/logout");
        return "redirect:/admin";
    }

    /**
     * .
     * @param request .
     * @param model .
     * @return .
     */
    @RequestMapping("/logout")
    public String logout(final HttpServletRequest request, final Model model) {
        HttpSession session = request.getSession();
        session.invalidate();
        model.addAttribute("messageLogout", "You have been disconnected");
        return "redirect:/";
    }
}
