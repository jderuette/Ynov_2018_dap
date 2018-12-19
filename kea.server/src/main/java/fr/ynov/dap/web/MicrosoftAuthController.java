package fr.ynov.dap.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.MicrosoftAuthService;

/**
 * this Controller manages the oAuthService of Microsoft.
 * @author Antoine
 *
 */
@Controller
public class MicrosoftAuthController {

  /**
   * the microsoftAuthService to get mails information.
   */
  @Autowired
  private MicrosoftAuthService microsoftAuthService;
  /**
   * the appUserRepository manages all database accesses for The AppUser.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
   * the route where microsoft redirects.
   * @param model model used in template
   * @param code the extracted code
   * @param idToken the token given by microsoft
   * @param state the status
   * @param request the request after auth
   * @return the template registered.html
   * @throws IOException nothing special
   */
  @RequestMapping("/authorize")
  public String openAuthCallBack(final Model model,
      @RequestParam("code") final String code,
      @RequestParam("id_token") final String idToken,
      @RequestParam("state") final UUID state, final HttpServletRequest request)
      throws IOException {
    HttpSession session = request.getSession();
    //TODO kea by Djer |IDE| Ton IDE t'indique que ca n'est pas utilisé. Bug ? A supprimer ?
    UUID expectedState = (UUID) session.getAttribute("expected_state");
    UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
    //the expectedState and expectedNonce are null but the token and authCode aren't
    //if (state.equals(expectedState)) {
    model.addAttribute("authCode", code);
    model.addAttribute("idToken", idToken);
    /*} else {
      model.addAttribute("error", "Unexpected state returned from authority.");
    }*/
    return "registered";
  }

  /**
   * the method that leads you to a template where you have a auth button.
   * @param model the model linked to the template
   * @param userKey the ynov appUser
   * @param accountName the name used to identify the microsoft account
   * @return a template
   * @throws IOException nothing special
   */
  @RequestMapping("/add/maccount/{accountName}")
  public String login(final Model model, @PathVariable final String accountName,
      @RequestParam(value = "userKey", required = true) final String userKey)
      throws IOException {
    String response = null;
    UUID state = UUID.randomUUID();
    UUID nonce = UUID.randomUUID();
    response = microsoftAuthService.getLoginUrl(state, nonce);
    
    //TODO kea by Djer |API Microsoft| Fait le "save" dans le "/authorize", ton utilsiateur pourrait refuser de te laisser acces à ces informations et tu auras un comtpe "fantome"
    if (appUserRepo.findByUserKey(userKey) == null) {
      AppUser user = new AppUser(userKey);
      appUserRepo.save(user);
      MicrosoftAccount mAccount = new MicrosoftAccount();
      mAccount.setMicrosoftAccountName(accountName);
      user = appUserRepo.findByUserKey(userKey);
      user.addMicrosoftAccount(mAccount);
      appUserRepo.save(user);
    } else {
      AppUser user = appUserRepo.findByUserKey(userKey);
      MicrosoftAccount mAccount = new MicrosoftAccount();
      mAccount.setMicrosoftAccountName(accountName);
      user.addMicrosoftAccount(mAccount);
      appUserRepo.save(user);
    }
    model.addAttribute("loginUrl", response);
    return "microsoftRegister";
  }
}
