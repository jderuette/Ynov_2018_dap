package fr.ynov.dap.dap.google;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.dap.App;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class GoogleService {
	
    //TODO for by Djer Si pas de modifier : celui de la classe, ton LOG est donc public !
    //TODO for by Djer un Logger est généralement static final
	Logger LOG = LogManager.getLogger();

	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
	//TODO for by Djer Pourquoi static ?
  static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userKey) throws IOException {
    //TODO for by Djer Une GROSSE partie de ce code est déja dans le "getFlow".
      // Load client secrets.
    InputStream in = CalendarService.class.getResourceAsStream(App.config.GetCredentialsFilePath());
    //TODO for by Djer chargement d'une fichier Externe ?
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(App.config.GetJsonFactory(), new InputStreamReader(in));

        // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, App.config.GetJsonFactory(), clientSecrets, App.config.getScopes())
          .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(App.config.GetTokensDirectoryPath())))
          .setAccessType("offline")
          .build();
    //TODO for by Djer "AuthorizationCodeInstalledApp" pose probleme en mode "web". Utiliser flow.loadCredential(userKey)
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
  }

/**
 * Handle the Google response.
 * @param request The HTTP Request
 * @param code    The (encoded) code use by Google (token, expirationDate,...)
 * @param session the HTTP Session
 * @return the view to display
 * @throws ServletException When Google account could not be connected to DaP.
 * @throws GeneralSecurityException 
 */
@RequestMapping("/oAuth2Callback")
public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
        final HttpSession session) throws ServletException, GeneralSecurityException {
    final String decodedCode = extracCode(request);

    final String redirectUri = buildRedirectUri(request, App.config.getoAuth2CallbackUrl());

    final String userId = getUserid(session);
    try {
        final GoogleAuthorizationCodeFlow flow = getFlow();
        final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

        final Credential credential = flow.createAndStoreCredential(response, userId);
        if (null == credential || null == credential.getAccessToken()) {
            LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
        }

        if (LOG.isDebugEnabled()) {
            if (null != credential && null != credential.getAccessToken()) {
                LOG.debug("New user credential stored with userId : " + userId);
            }
        }
        // onSuccess(request, resp, credential);
    } catch (IOException e) {
        LOG.error("Exception while trying to store user Credential", e);
        throw new ServletException("Error while trying to connect Google Account");
    }

    return "redirect:/loginSuccessful";
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
        LOG.error("userId in Session is NULL in Callback");
        throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
    }
    return userId;
}

/**
 * Extract OAuth2 Google code (from URL) and decode it.
 * @param request the HTTP request to extract OAuth2 code
 * @return the decoded code
 * @throws ServletException if the code cannot be decoded
 */
private String extracCode(final HttpServletRequest request) throws ServletException {
    final StringBuffer buf = request.getRequestURL();
    if (null != request.getQueryString()) {
        buf.append('?').append(request.getQueryString());
    }
    final AuthorizationCodeResponseUrl responseUrl = new AuthorizationCodeResponseUrl(buf.toString());
    final String decodeCode = responseUrl.getCode();

    if (decodeCode == null) {
        throw new MissingServletRequestParameterException("code", "String");
    }

    if (null != responseUrl.getError()) {
        LOG.error("Error when trying to add Google acocunt : " + responseUrl.getError());
        throw new ServletException("Error when trying to add Google acocunt");
        // onError(request, resp, responseUrl);
    }

    return decodeCode;
}

/**
 * Build a current host (and port) absolute URL.
 * @param req         The current HTTP request to extract schema, host, port
 *                    informations
 * @param destination the "path" to the resource
 * @return an absolute URI
 */
protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
    final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
    url.setRawPath(destination);
    return url.build();
}


/**
 * Add a Google account (user will be prompt to connect and accept required
 * access).
 * @param userId  the user to store Data
 * @param request the HTTP request
 * @param session the HTTP session
 * @return the view to Display (on Error)
 * @throws GeneralSecurityException 
 */
@RequestMapping("/account/add/{userId}")
public String addAccount(@PathVariable final String userId, final HttpServletRequest request,
        final HttpSession session) throws GeneralSecurityException {
    String response = "errorOccurs";
    GoogleAuthorizationCodeFlow flow;
    Credential credential = null;
    try {
        flow = getFlow();
        credential = flow.loadCredential(userId);

        if (credential != null && credential.getAccessToken() != null) {
            response = "AccountAlreadyAdded";
        } else {
            // redirect to the authorization flow
            final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
            authorizationUrl.setRedirectUri(buildRedirectUri(request, App.config.getoAuth2CallbackUrl()));
            // store userId in session for CallBack Access
            session.setAttribute("userId", userId);
            response = "redirect:" + authorizationUrl.build();
        }
    } catch (IOException e) {
        LOG.error("Error while loading credential (or Google Flow)", e);
    }
    // only when error occurs, else redirected BEFORE
    return response;
}
/**
 * Obtient le Google Authorization Flow
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
	private GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        InputStream in = CalendarService.class.getResourceAsStream(App.config.GetCredentialsFilePath());
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(App.config.GetJsonFactory(), new InputStreamReader(in));
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	            HTTP_TRANSPORT, App.config.GetJsonFactory(), clientSecrets, App.config.getScopes())
	            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(App.config.GetTokensDirectoryPath())))
	            .setAccessType("offline")
	            .build();
		return flow;
}
}