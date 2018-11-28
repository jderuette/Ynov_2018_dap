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
          user = appUserRepo.findByUserKey(userKey);
          user.addGoogleAccount(gAccount);
          appUserRepo.save(user);
        } else {
          AppUser user = appUserRepo.findByUserKey(userKey);
          GoogleAccount gAccount = new GoogleAccount();
          gAccount.setGoogleAccountName(accountName);
          user.addGoogleAccount(gAccount);
          appUserRepo.save(user);
        }
      }
    } catch (IOException e) {
      accountService.getLogger()
          .error("Error while loading credential (or Google Flow)", e);
    }
    // only when error occurs, else redirected BEFORE
    return response;
  }

}
