package fr.ynov.dap.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.http.GenericUrl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MissingServletRequestParameterException;

/**
 * A google service to store all users' tokens.
 * @author Antoine
 *
 */
@Service
public class GoogleAccountService extends GoogleService {
  /**
   * Logger is the object we use to log important informations or report bugs.
   */
  private static final Logger LOGGER = (Logger) LogManager
      .getLogger(GoogleService.class);

  /**
   * Constructor.
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public GoogleAccountService()
      throws InstantiationException, IllegalAccessException {
    super();
  }

  /**
   * retrieve the User ID in Session.
   * @param session the HTTP Session
   * @return the current User Id in Session
   * @throws ServletException if no User Id in session
   */
  public String getUserid(final HttpSession session) throws ServletException {
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
  public String extracCode(final HttpServletRequest request)
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
  public String buildRedirectUri(final HttpServletRequest req,
      final String destination) {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath(destination);
    return url.build();
  }

  /**
   * get the singleton logger.
   * @return the logger
   */
  public Logger getLogger() {
    return LOGGER;
  }

}
