package fr.ynov.dap.controllers.microsoft;

import fr.ynov.dap.helpers.MicrosoftAuthHelper;
import fr.ynov.dap.models.common.User;
import fr.ynov.dap.models.microsoft.*;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.microsoft.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

/**
 * IndexController
 */

@Controller
public class MicrosoftAccountController {

    /**
     * Autowired UserRepository
     */
    @Autowired
    //TODO grj by Djer |POO| par defaut ton attribut aura la porté de la classe (donc public).
    UserRepository userRepository;


    /**
     * Add a MicrosoftAccount to a user
     *
     * @param model                model
     * @param request              HTTP request
     * @param userName             userName
     * @param microsoftAccountName microsoftAccountName
     * @return success or error template
     */
    @RequestMapping("/user/{userName}/add/microsoft-account/{microsoftAccountName}")
    public String index(Model model, HttpServletRequest request, @PathVariable final String userName, @PathVariable final String microsoftAccountName) {
        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        // Save the state and nonce in the session so we can
        // verify after the auth process redirects back
        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        session.setAttribute("userName", userName);
        session.setAttribute("microsoftAccountName", microsoftAccountName);

        String loginUrl = MicrosoftAuthHelper.getLoginUrl(state, nonce);
        //TODO grj by Djer |MVC| pas très utile, ton modèle ne sera pas "sauvegardé" après le "redirect. A la limite tu peux mettre cette URL dans la session utilisateur mais je ne suis pas certains que tu en ai vraiment besoin
        model.addAttribute("loginUrl", loginUrl);

        return "redirect:" + loginUrl;
    }

    @RequestMapping(value = "/microsoft/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
                            @RequestParam("state") UUID state, HttpServletRequest request) {
        {
            // Get the expected state value from the session
            HttpSession      session          = request.getSession();
            UUID             expectedState    = (UUID) session.getAttribute("expected_state");
            UUID             expectedNonce    = (UUID) session.getAttribute("expected_nonce");
            MicrosoftAccount microsoftAccount = new MicrosoftAccount();

            String userName             = (String) session.getAttribute("userName");
            String microsoftAccountName = (String) session.getAttribute("microsoftAccountName");

            if (state.equals(expectedState)) {
                MicrosoftIdToken microsoftIdTokenObj = MicrosoftIdToken.parseEncodedToken(idToken, expectedNonce.toString());
                if (microsoftIdTokenObj != null) {

                    if (userName != null && microsoftAccountName != null) {
                        MicrosoftTokenResponse microsoftTokenResponse = MicrosoftAuthHelper.getTokenFromAuthCode(code, microsoftIdTokenObj.getTenantId());
                        User                   user                   = userRepository.findByName(userName);
                        microsoftAccount.setName(microsoftAccountName);
                        microsoftAccount.setToken(microsoftTokenResponse.getAccessToken());
                        microsoftAccount.setRefreshToken(microsoftTokenResponse.getRefreshToken());

                        // Get user info
                        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(microsoftTokenResponse.getAccessToken(), null);
                        OutlookUser    outlookUser;
                        try {
                            outlookUser = outlookService.getCurrentUser().execute().body();
                            microsoftAccount.setEmail(outlookUser.getMail());
                        } catch (IOException e) {
                            //TODO grj by Djer |Gestion Exception| Attention comme tu "catch" cette exception, tu l'étouffe et ton programe continue après. Tu va donc sauvegarder un "micrsooft acocunt" sans email. C'est peut-être ton objectif, mais pense à une LOG en complment dans ce cas. Les exeptiosn "étouffées" sans "avertissement" (une Log en error/warning) peuvent être très difficile à débugger
                            session.setAttribute("error", e.getMessage());
                        }

                        microsoftAccount.setTenantId(microsoftIdTokenObj.getTenantId());
                        microsoftAccount.setTokenExpirationTime(microsoftTokenResponse.getExpirationTime());

                        user.addMicrosoftAccount(microsoftAccount);
                        userRepository.save(user);
                    }

                }
            }
        }

        return "redirect:/user-success";

    }
}
