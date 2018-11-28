
package fr.ynov.dap.google;


import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;


/**
 * Service gérant les comptes google
 * 
 * @author antod
 *
 */
@Controller
public class GoogleAccountService extends GoogleServices
{
  /**
   * Constructeur de la classe GoogleAccount
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  GoogleAccountService() throws GeneralSecurityException, IOException
  {
    super();
  }

  /**
   * Le logger
   */
  private final Logger LOGGER = LogManager.getLogger();
  /**
   * L'indice de début de sélection pour les données sensibles
   */
  private final Integer SENSIBLE_DATA_FIRST_CHAR = 5;
  /**
   * L'indice de fin de sélection pour les données sensibles
   */
  private final Integer SENSIBLE_DATA_LAST_CHAR = 10;

  /**
   * Variable appUserRepository
   */
  @Autowired
  AppUserRepository appUserRepository;

  /**
   * Handle the Google response.
   * 
   * @param request The HTTP Request
   * @param code    The (encoded) code use by Google (token, expirationDate,...)
   * @param session the HTTP Session
   * @return the view to display
   * @throws ServletException         When Google account could not be connected
   *                                  to DaP.
   * @throws GeneralSecurityException
   */
  @RequestMapping("/oAuth2Callback")
  public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
      final HttpSession session) throws ServletException, GeneralSecurityException
  {
    final String decodedCode = extracCode(request);

    final String redirectUri = buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl());

    final String accountName = getAttributeFromSession(session, "accountName");
    final String userKey = getAttributeFromSession(session, "userKey");

    try
    {
      final GoogleAuthorizationCodeFlow flow = super.getFlow();
      final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

      final Credential credential = flow.createAndStoreCredential(response, accountName).setExpiresInSeconds(null);
      if (null == credential || null == credential.getAccessToken())
      {
        LOGGER.warn("Trying to store a NULL AccessToken for user : " + accountName);
      } else
      {
        // Si on a pas de problèmes sur le credential alors on enregsitre le compte
        // On récupère le AppUser
        AppUser appUser = appUserRepository.findByName(userKey);
        
        GoogleAccount googleAccount = new GoogleAccount();
        googleAccount.setUserName(accountName);
                
        appUser.addGoogleAccount(googleAccount);
        appUserRepository.save(appUser);
      }

      if (LOGGER.isDebugEnabled())
      {
        if (null != credential && null != credential.getAccessToken())
        {
          LOGGER.debug("New user credential stored with userId : " + accountName + "partial AccessToken : "
              + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR, SENSIBLE_DATA_LAST_CHAR));
        }
      }
    } catch (IOException e)
    {
      LOGGER.error("Exception while trying to store user Credential", e);
      throw new ServletException("Error while trying to conenct Google Account");
    }

    return "redirect:/";
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

  /**
   * Extract OAuth2 Google code (from URL) and decode it.
   * 
   * @param request the HTTP request to extract OAuth2 code
   * @return the decoded code
   * @throws ServletException if the code cannot be decoded
   */
  private String extracCode(final HttpServletRequest request) throws ServletException
  {
    final StringBuffer buf = request.getRequestURL();
    if (null != request.getQueryString())
    {
      buf.append('?').append(request.getQueryString());
    }
    final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
    final String decodeCode = responseUrl.getCode();

    if (decodeCode == null)
    {
      throw new MissingServletRequestParameterException("code", "String");
    }

    if (null != responseUrl.getError())
    {
      LOGGER.error("Error when trying to add Google acocunt : " + responseUrl.getError());
      throw new ServletException("Error when trying to add Google acocunt");
      // onError(request, resp, responseUrl);
    }

    return decodeCode;
  }

  /**
   * Build a current host (and port) absolute URL.
   * 
   * @param req         The current HTTP request to extract schema, host, port
   *                    informations
   * @param destination the "path" to the resource
   * @return an absolute URI
   */
  protected String buildRedirectUri(final HttpServletRequest req, final String destination)
  {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath(destination);
    return url.build();
  }

  /**
   * Add a Google account (user will be prompt to connect and accept required
   * access).
   * 
   * @param userId  the user to store Data
   * @param request the HTTP request
   * @param session the HTTP session
   * @return the view to Display (on Error)
   * @throws GeneralSecurityException
   */
  @RequestMapping("/account/add/google/{accountName}")
  // Ajouter un @RequestParams dans les paramètres
  public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
      final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException
  {
    String response = "errorOccurs";
    GoogleAuthorizationCodeFlow flow;
    Credential credential = null;

    try
    {
      flow = super.getFlow();
      credential = flow.loadCredential(accountName);

      if (credential != null && credential.getAccessToken() != null)
      {
        response = "AccountAlreadyAdded";
      } else
      {
        // redirect to the authorization flow
        final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
        authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getoAuth2CallbackUrl()));
        // store userId in session for CallBack Access
        session.setAttribute("accountName", accountName);
        session.setAttribute("userKey", userKey);
        response = "redirect:" + authorizationUrl.build();
      }
    } catch (IOException e)
    {
      LOGGER.error("Error while loading credential (or Google Flow)", e);
    }

    // only when error occurs, else redirected BEFORE
    return response;
  }
}
