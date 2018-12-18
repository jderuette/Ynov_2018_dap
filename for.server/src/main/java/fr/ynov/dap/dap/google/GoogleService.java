package fr.ynov.dap.dap.google;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.function.Consumer;

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

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.dap.App;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repository.AppUserRepository;
import fr.ynov.dap.dap.repository.GoogleUserRepository;

@Controller
public class GoogleService {
	
	public static final Logger LOG = LogManager.getLogger();
	
	//TODO for by Djer |POO| Attention, devrait être privé. Si tu ne pécises pas l'attribut à la même porté que la classe (donc "public" ici) !
	@Autowired
	AppUserRepository userRepo;
	//TODO for by Djer |POO| Attention, devrait être privé. Si tu ne pécises pas l'attribut à la même porté que la classe (donc "public" ici) ! De plus tu n'utilises pas cetet attribut, mais ton IDE ne peu pas te l'indiqué car il est public
	@Autowired
	GoogleUserRepository googleRepo;

	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
  static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userKey) throws IOException {
    //TODO for by Djer |POO| (ancien TO-DO) Une GROSSE partie de ce code est déja dans le "getFlow".
      // Load client secrets.
      InputStreamReader inputStrReader = new InputStreamReader(new FileInputStream(App.config.getCredentialsFilePath()), Charset.forName("UTF-8"));
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(App.config.GetJsonFactory(), inputStrReader);

        // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, App.config.GetJsonFactory(), clientSecrets, App.config.getScopes())
          .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(App.config.GetTokensDirectoryPath())))
          .setAccessType("offline")
          .build();
    return flow.loadCredential(userKey);
    
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
    final String accountName = getAccountName(session);
    final String userKey = getUserKey(session);
	
    try {
        final GoogleAuthorizationCodeFlow flow = getFlow();
        final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

        final Credential credential = flow.createAndStoreCredential(response, accountName);
        if (null == credential || null == credential.getAccessToken()) {
            LOG.warn("Trying to store a NULL AccessToken for user : " + userKey);
        }
        if (null != credential && null != credential.getAccessToken()) {

            AppUser appUser = userRepo.findByUserKey(userKey);
            GoogleAccount googleAccount = new GoogleAccount();
            
            googleAccount.setAccountName(accountName);
            googleAccount.setToken(credential.getAccessToken());

            appUser.addGoogleAccount(googleAccount);
            userRepo.save(appUser);
        }

        if (LOG.isDebugEnabled()) {
            if (null != credential && null != credential.getAccessToken()) {
                LOG.debug("New user credential stored with userKey : " + userKey);
            }
        }
        // onSuccess(request, resp, credential);
    } catch (IOException e) {
        //TODO for by Djer |Log4J| Contextualise tes messages (" for userKey : " + userKey + " and accountName : " + accountName)
        LOG.error("Exception while trying to store user Credential", e);
        throw new ServletException("Error while trying to connect Google Account");
    }

    return "redirect:/";
}

/**
 * retrieve the User ID in Session.
 * @param session the HTTP Session
 * @return the current User Id in Session
 * @throws ServletException if no User Id in session
 */
public String getUserKey(final HttpSession session) throws ServletException {
    String userId = null;
    if (null != session && null != session.getAttribute("userKey")) {
        userId = (String) session.getAttribute("userKey");
    }

    if (null == userId) {
        LOG.error("userKey in Session is NULL in Callback");
        throw new ServletException("Error when trying to add Google acocunt : userKey is NULL in User Session");
    }
    return userId;
}

/**
 * retrieve the User ID in Session.
 * @param session the HTTP Session
 * @return the current User Id in Session
 * @throws ServletException if no User Id in session
 */
private String getAccountName(final HttpSession session) throws ServletException {
    String accountName = null;
    if (null != session && null != session.getAttribute("accountName")) {
    	accountName = (String) session.getAttribute("accountName");
    }

    if (null == accountName) {
        //TODO for by Djer |POO| Attention, tes message sont faux, c'est le "accountName", pas le "userKey". Au lieu de copier/coller tu aurais pu factoriser
        LOG.error("userKey in Session is NULL in Callback");
        throw new ServletException("Error when trying to add Google acocunt : userKey is NULL in User Session");
    }
    return accountName;
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
        LOG.error("Error when trying to add Google account : " + responseUrl.getError());
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
public String buildRedirectUri(final HttpServletRequest req, final String destination) {
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
@RequestMapping("/add/account/{accountName}")
public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey, final HttpServletRequest request,
        final HttpSession session) throws GeneralSecurityException {
    String response = "errorOccurs";
    GoogleAuthorizationCodeFlow flow;
    Credential credential = null;
	AppUser appUser = userRepo.findByUserKey(userKey);
	
    try {
        flow = getFlow();
        credential = flow.loadCredential(accountName);

        if (credential != null && credential.getAccessToken() != null) {
            response = "AccountAlreadyAdded";
        } else {
            // redirect to the authorization flow
            final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
            authorizationUrl.setRedirectUri(buildRedirectUri(request, App.config.getoAuth2CallbackUrl()));
            // store userId in session for CallBack Access
            session.setAttribute("userKey", userKey);
            if(appUser != null)
    		{
                session.setAttribute("accountName", accountName);
    		}
            response = "redirect:" + authorizationUrl.build();
        }
    } catch (IOException e) {
    	//TODO for by Djer |Log4J| Une petite log ?
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
	public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        InputStreamReader inputStrReader = new InputStreamReader(new FileInputStream(App.config.getCredentialsFilePath()), Charset.forName("UTF-8"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(App.config.GetJsonFactory(), inputStrReader);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	            HTTP_TRANSPORT, App.config.GetJsonFactory(), clientSecrets, App.config.getScopes())
	            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(App.config.GetTokensDirectoryPath())))
	            .setAccessType("offline")
	            .build();
		return flow;
}
}