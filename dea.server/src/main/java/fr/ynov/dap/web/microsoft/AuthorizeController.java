
package fr.ynov.dap.web.microsoft;


import java.security.GeneralSecurityException;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.web.microsoft.auth.AuthHelper;
import fr.ynov.dap.web.microsoft.auth.IdToken;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;


/**
 * Classe de controlleur pour l'authentification généré par microsoft
 * 
 * @author antod
 *
 */
@Controller
public class AuthorizeController
{

  /**
   * Le logger
   */
  private final Logger LOGGER = LogManager.getLogger();

  /**
   * Variable appUserRepository
   */
  @Autowired
  private AppUserRepository appUserRepository;

  @RequestMapping(value = "/authorize", method = RequestMethod.POST)
  public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
      @RequestParam("state") UUID state, HttpServletRequest request)
  {
    {
      // Get the expected state value from the session
      HttpSession session = request.getSession();
      UUID expectedState = (UUID) session.getAttribute("expected_state");
      UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

      // Make sure that the state query parameter returned matches
      // the expected state
      if (state.equals(expectedState))
      {
        IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());

        if (idTokenObj != null)
        {
          String tenantId = idTokenObj.getTenantId();
          TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());

          try
          {
            // Enregistrement de l'utilisateur connecté
            final String userKey = getAttributeFromSession(session, "userKey");
            final String accountName = getAttributeFromSession(session, "accountName");
            AppUser appUser = appUserRepository.findByName(userKey);

            MicrosoftAccount microsoftAccount = new MicrosoftAccount();
            microsoftAccount.setUserName(accountName);
            microsoftAccount.setTenantId(tenantId);
            microsoftAccount.setTokenResponse(tokenResponse);

            appUser.addMicrosoftAccounts(microsoftAccount);
            appUserRepository.save(appUser);
          } catch (Exception e)
          {
            LOGGER.error("error in AuthorizeController", e.getMessage());
          }
        } else
        {
          LOGGER.error("error", "ID token failed validation.");
        }
      } else
      {
        LOGGER.error("error", "Unexpected state returned from authority.");
      }

      return "redirect:/index";
    }
  }

  @RequestMapping("/logout")
  public String logout(HttpServletRequest request)
  {
//    HttpSession session = request.getSession();
//    session.invalidate();
    return "redirect:/index";
  }

  @RequestMapping("/account/add/microsoft/{accountName}")
  // Ajouter un @RequestParams dans les paramètres
  public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
      final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException
  {
    UUID state = UUID.randomUUID();
    UUID nonce = UUID.randomUUID();

    String loginUrl = AuthHelper.getLoginUrl(state, nonce);

    session.setAttribute("expected_state", state);
    session.setAttribute("expected_nonce", nonce);
    session.setAttribute("userKey", userKey);
    session.setAttribute("accountName", accountName);

    return "redirect:" + loginUrl;
  }

  /**
   * retrieve the User ID in Session.
   * 
   * @param session the HTTP Session
   * @return the current User Id in Session
   * @throws ServletException if no User Id in session
   */
  private String getAttributeFromSession(final HttpSession session, String attributeName) throws ServletException
  {
    String attribute = null;

    if (null != session && null != session.getAttribute(attributeName))
    {
      attribute = (String) session.getAttribute(attributeName);
    }

    if (null == attribute)
    {
      LOGGER.error(attributeName + " in Session is NULL in Callback");
      throw new ServletException(
          "Error when trying to add Google acocunt : " + attributeName + " is NULL is User Session");
    }
    return attribute;
  }
}
