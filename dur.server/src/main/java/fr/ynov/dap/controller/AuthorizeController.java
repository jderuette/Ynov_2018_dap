package fr.ynov.dap.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.microsoft.MicrosoftService;
import fr.ynov.dap.microsoft.OutlookApiCalls;
import fr.ynov.dap.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.entity.IdToken;
import fr.ynov.dap.microsoft.entity.OutlookUser;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.MicrosoftAccountRepository;

@Controller
public class AuthorizeController {

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, 
            @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state,
            final HttpServletRequest request) {
      // Get the expected state value from the session
      HttpSession session = request.getSession();
      UUID expectedState = (UUID) session.getAttribute("expected_state");
      UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
  
      // Make sure that the state query parameter returned matches
      // the expected state
      if (state.equals(expectedState)) {
          IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
          if (idTokenObj != null) {
            TokenResponse tokenResponse = MicrosoftService.getTokenFromAuthCode(code, idTokenObj.getTenantId());
            session.setAttribute("tokens", tokenResponse);
            session.setAttribute("userConnected", true);
            session.setAttribute("userName", idTokenObj.getName());

                String accountName = (String) session.getAttribute("accountName");
                String userKey = (String) session.getAttribute("userKey");

                AppUser appuser = appUserRepository.findByUserKey(userKey);

                MicrosoftAccount mAccount = new MicrosoftAccount();
                mAccount.setEmail(accountName);
                mAccount.setToken(tokenResponse);
                mAccount.setOwner(appuser);
                mAccount.setTenantId(idTokenObj.getTenantId());

                appuser.addMicrosoftAccount(mAccount);
                appUserRepository.save(appuser);

          // Get user info
                OutlookApiCalls outlookService = OutlookServiceBuilder
                        .getOutlookService(tokenResponse.getAccessToken());
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
      }
      else {
        session.setAttribute("error", "Unexpected state returned from authority.");
      }
        return "login_success";
   }

    /**
     * Logout url for microsoft.
     * @param request User's Id
     * @return the index html page
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
      HttpSession session = request.getSession();
      session.invalidate();
        return "index";
    }
}