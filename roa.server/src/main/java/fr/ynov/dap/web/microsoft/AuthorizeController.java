package fr.ynov.dap.web.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.services.microsoft.AuthHelper;
import fr.ynov.dap.services.microsoft.IdToken;
import fr.ynov.dap.services.microsoft.OutlookService;
import fr.ynov.dap.services.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.services.microsoft.OutlookUser;
import fr.ynov.dap.services.microsoft.TokenResponse;

/**
 * @author alexa
 *
 */
@Controller
public class AuthorizeController {
  /**
   * index.
   * @param model model
   * @param request request
   * @return index
   */
  @RequestMapping("/index")
  public String index(final Model model, final HttpServletRequest request) {
    UUID state = UUID.randomUUID();
    UUID nonce = UUID.randomUUID();

    // Save the state and nonce in the session so we can
    // verify after the auth process redirects back
    HttpSession session = request.getSession();
    session.setAttribute("expected_state", state);
    session.setAttribute("expected_nonce", nonce);

    String loginUrl = AuthHelper.getLoginUrl(state, nonce);
    model.addAttribute("loginUrl", loginUrl);
    // Name of a definition in WEB-INF/defs/pages.xml
    return "index";
  }
  /**
   * @param code code
   * @param idToken id token
   * @param state state
   * @param request request
   * @return template "mail"
   */
  @RequestMapping(value = "/authorize.html", method = RequestMethod.POST)
  public String authorize(final Model model,
      @RequestParam("code") final String code,
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
        TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
        session.setAttribute("tokens", tokenResponse);
        session.setAttribute("userConnected", true);
        session.setAttribute("userName", idTokenObj.getName());// Get user info
        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
        OutlookUser user;
        try {
          user = outlookService.getCurrentUser().execute().body();
          session.setAttribute("userEmail", user.getMail());
        } catch (IOException e) {
          //TODO roa by Djer |Log4J| Une petite log ?
          session.setAttribute("error", e.getMessage());
        }
        session.setAttribute("userTenantId", idTokenObj.getTenantId());
        model.addAttribute("authCode", code);
        model.addAttribute("idToken", idToken);
        model.addAttribute("accessToken", tokenResponse.getAccessToken());
      //FIXME roa by Djer |JPA| C'est par ici que tu devrait suavegarder les "tokens" Microsoft en BDD
      } else {
        //TODO roa by Djer |Log4J| Une petite log ?
        session.setAttribute("error", "ID token failed validation.");
      }
    } else {
      //TODO roa by Djer |Log4J| Une petite log ?
      session.setAttribute("error", "Unexpected state returned from authority.");
    //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
      System.out.println("error state: " + state.toString());
    }
    
    return "mail";
  }

  @RequestMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.invalidate();
    return "redirect:/index.html";
  }
}
