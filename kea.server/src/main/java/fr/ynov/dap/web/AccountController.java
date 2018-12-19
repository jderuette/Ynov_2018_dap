package fr.ynov.dap.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.GoogleAccountService;

/**
 * this controller manages the AccountService in order to add user in the app and also Google and Microsoft accounts.
 * @author Antoine
 *
 */
@Controller
public class AccountController {

  /**
   * the AccountService to add and manage user's information.
   */
  @Autowired
  private GoogleAccountService accountService;
  /**
   * the appUserRepository manages all database accesses for The AppUser.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
   * This method is called when the user needs to be authenticated and stores the token.
   * @param code encoded code with google Datas
   * @param request the http request
   * @param session the session used by the current user
   * @return string with the redirect URL
   * @throws ServletException if server doesn't respond
   */
  @RequestMapping("/oAuth2Callback")
  public String openAuthCallback(@RequestParam final String code,
      final HttpServletRequest request, final HttpSession session)
      throws ServletException {
    final String decodedCode = accountService.extracCode(request);
    final String redirectUri = accountService.buildRedirectUri(request,
        accountService.getCustomConfig().getoAuth2CallbackUrl());

    final String googleAccountName = accountService.getUserid(session);
    try {
      GoogleAuthorizationCodeFlow flow = accountService.getFlow();
      final TokenResponse response = flow.newTokenRequest(decodedCode)
          .setRedirectUri(redirectUri).execute();
      final Credential credential = flow.createAndStoreCredential(response,
          googleAccountName);
      if (null == credential || null == credential.getAccessToken()) {
          //TODO kea by Djer |Log4J| Pourquoi utiliser le logger de accountService ? Le controlelr devrait avoir son propre logger (avec SA catégorie)
        accountService.getLogger().warn("Trying to store a NULL"
            + " AccessToken for user : " + googleAccountName);
      }
      if (accountService.getLogger().isDebugEnabled()) {
        if (null != credential && null != credential.getAccessToken()) {
          accountService.getLogger().debug("New user credential" + " stored "
              + "with userId : " + googleAccountName);
        }
      }
    } catch (IOException e) {
        //TODO kea by Djer |Log4J| Contextualise ausi ce message (" for google AccountName :" + googleAccountName)
      accountService.getLogger()
          .error("Exception while trying" + " to store user Credential", e);
      throw new ServletException(
          "Error while trying" + " to connect Google Account");
    }

    return "index";
  }

  /**
   * Add a Google account
   * (user will be prompt to connect and accept required).
   * access).
   * @param userKey the user to store Data
   * @param request the HTTP request
   * @param session the HTTP session
   * @param accountName the real googleAccount
   * @return the view to Display (on Error)
   */
  @RequestMapping("/add/gaccount/{accountName}")
  //TODO kea by Djer |Spring| dans l'annotation @RequestParam "required" est à true par defaut, ca n'est pas utile de le préciser
  public String addAccount(
      @RequestParam(value = "userKey", required = true) final String userKey,
      @PathVariable final String accountName, final HttpServletRequest request,
      final HttpSession session) {
    String response = "errorOccurs";
    GoogleAuthorizationCodeFlow flow;
    Credential credential = null;
    try {
      flow = accountService.getFlow();
      credential = flow.loadCredential(accountName);

      if (credential != null && credential.getAccessToken() != null) {
        response = "accountAlreadyAdded";
      } else {
        // redirect to the authorization flow
        final AuthorizationCodeRequestUrl authorizationUrl = flow
            .newAuthorizationUrl();
        authorizationUrl.setRedirectUri(accountService.buildRedirectUri(request,
            accountService.getCustomConfig().getoAuth2CallbackUrl()));
        // store userId in session for CallBack Access
        session.setAttribute("userId", accountName);
        response = "redirect:" + authorizationUrl.build();
        if (appUserRepo.findByUserKey(userKey) == null) {
          AppUser user = new AppUser(userKey);
          appUserRepo.save(user);
          GoogleAccount gAccount = new GoogleAccount();
          gAccount.setGoogleAccountName(accountName);
          //TODO kea by Djer |JPA| il n'est pas utile de fair ton save ne 2 fois.  appUserRepo.save(user); te renvoie une instance de appuser, pas la peine de re-faire un find. Tu devrait initialiser ton AppUser, puis lui ajouter le compte Google, et faire un unique save à la fin, JPA va s'occuper de créer/modifier les différntes entités
          user = appUserRepo.findByUserKey(userKey);
          user.addGoogleAccount(gAccount);
          appUserRepo.save(user);
        } else {
            //TODO kea by Djer |POO| Ce code est très simillaire à celui de la fin du "if". Dans le if créé le user si besoin, ensuite (sans "else) ajoute et sauvegarde le compte, avec une varaible "user" en dehors du if
          AppUser user = appUserRepo.findByUserKey(userKey);
          GoogleAccount gAccount = new GoogleAccount();
          gAccount.setGoogleAccountName(accountName);
          user.addGoogleAccount(gAccount);
          appUserRepo.save(user);
        }
      }
    } catch (IOException e) {
        //TODO kea by Djer |Log4J| Message faux. Contextualise tes messages (" for userKey : " + userKey + " and accountName : " + accountName)
      accountService.getLogger()
          .error("Error while loading credential (or Google Flow)", e);
    }
    // only when error occurs, else redirected BEFORE
    return response;
  }

}
