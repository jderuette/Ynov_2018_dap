package fr.ynov.dap;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * A google service to store all users' tokens.
 * @author Antoine
 *
 */
@Controller
public class AccountService extends GoogleService {
  /**
   * Logger is the object we use to log important informations or report bugs.
   */
  private static final Logger LOGGER = (Logger) LogManager
      .getLogger(GoogleService.class);

  /**
   * The repository that makes calls to the BDD.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
   * Constructor.
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public AccountService()
      throws InstantiationException, IllegalAccessException {
    super();
  }

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
    final String decodedCode = extracCode(request);

    final String redirectUri = buildRedirectUri(request,
        getCustomConfig().getoAuth2CallbackUrl());

    final String googleAccountName = getUserid(session);
    try {
      GoogleAuthorizationCodeFlow flow = super.getFlow();
      final TokenResponse response = flow.newTokenRequest(decodedCode)
          .setRedirectUri(redirectUri).execute();
      final Credential credential = flow.createAndStoreCredential(response,
          googleAccountName);
      if (null == credential || null == credential.getAccessToken()) {
        LOGGER.warn("Trying to store a NULL" + " AccessToken for user : "
            + googleAccountName);
      }
        if (LOGGER.isDebugEnabled()) {
          if (null != credential && null != credential.getAccessToken()) {
            LOGGER.debug("New user credential" + " stored " + "with userId : "
                + googleAccountName);
          }
        }
    } catch (IOException e) {
      LOGGER.error("Exception while trying" + " to store user Credential", e);
      throw new ServletException(
          "Error while trying" + " to connect Google Account");
    }

    return "index";
  }

  /**
   * retrieve the User ID in Session.
   * @param session the HTTP Session
   * @return the current User Id in Session
   * @throws ServletException if no User Id in session
   */
  private String getUserid(final HttpSession session) throws ServletException {
    String userId = null;
    if (null != session && null != session.getAttribute("userId")) {
      userId = (String) session.getAttribute("userId");
    }

    if (null == userId) {
      LOGGER.error("userId in Session is NULL in Callback");
      throw new ServletException("Error when trying to add "
          + "Google account : userId is NULL" + " is User Session");
    }
    return userId;
  }

  /**
   * Extract OAuth2 Google code (from URL) and decode it.
   * @param request the HTTP request to extract OAuth2 code
   * @return the decoded code
   * @throws ServletException if the code cannot be decoded
   */
  private String extracCode(final HttpServletRequest request)
      throws ServletException {
    final StringBuffer buf = request.getRequestURL();
    if (null != request.getQueryString()) {
      buf.append('?').append(request.getQueryString());
    }
    final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(
        buf.toString());
    final String decodeCode = responseUrl.getCode();

    if (decodeCode == null) {
      throw new MissingServletRequestParameterException("code", "String");
    }

    if (null != responseUrl.getError()) {
      LOGGER.error("Error when" + " trying to add Google acocunt : "
          + responseUrl.getError());
      throw new ServletException(
          "Error when trying to add" + " Google account");
      // onError(request, resp, responseUrl);
    }
    return decodeCode;
  }

  /**
   * Build a current host (and port) absolute URL.
   * @param req The current HTTP request to extract schema, host, port
   * @param destination the "path" to the resource
   * @return an absolute URI
   */
  protected String buildRedirectUri(final HttpServletRequest req,
      final String destination) {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath(destination);
    return url.build();
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
  @RequestMapping("/add/account/{accountName}")
  public String addAccount(
      @RequestParam(value = "userKey", required = true) final String userKey,
      @PathVariable final String accountName, final HttpServletRequest request,
      final HttpSession session) {
    String response = "errorOccurs";
    GoogleAuthorizationCodeFlow flow;
    Credential credential = null;
    try {
      flow = super.getFlow();
      credential = flow.loadCredential(accountName);

      if (credential != null && credential.getAccessToken() != null) {
        response = "AccountAlreadyAdded";
      } else {
        // redirect to the authorization flow
        final AuthorizationCodeRequestUrl authorizationUrl = flow
            .newAuthorizationUrl();
        authorizationUrl.setRedirectUri(buildRedirectUri(request,
            getCustomConfig().getoAuth2CallbackUrl()));
        // store userId in session for CallBack Access
        session.setAttribute("userId", accountName);
        response = "redirect:" + authorizationUrl.build();
        if (appUserRepo.findByUserKey(userKey) == null) {
            AppUser user = new AppUser(userKey);
            GoogleAccount gAccount = new GoogleAccount();
            gAccount.setGoogleAccountName(accountName);
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
      LOGGER.error("Error while loading credential (or Google Flow)", e);
    }
    // only when error occurs, else redirected BEFORE
    return response;
  }

}
