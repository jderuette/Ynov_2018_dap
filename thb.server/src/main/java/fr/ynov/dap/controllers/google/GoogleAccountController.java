package fr.ynov.dap.controllers.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.services.google.GoogleService;
import fr.ynov.dap.utils.JSONResponse;


@Controller
public class GoogleAccountController extends GoogleService {
	@Autowired
	AppUserRepository appUserRepo;
	
	private static final Integer SENSIBLE_DATA_FIRST_CHAR =  0; //"Aucune indication ???";
	private static final Integer SENSIBLE_DATA_LAST_CHAR =  0; //"Aucune indication ???";
	
    @RequestMapping("/account/add/google/{accountName}")
    public @ResponseBody String addAccount(@PathVariable(value="accountName") final String accountName, 
    		@RequestParam(value="userKey", required=true) String userKey, 
    		final HttpServletRequest request,
    		final HttpSession session) {
    	
      String response = "errorOccurs";
      GoogleAuthorizationCodeFlow flow = null;
      Credential credential = null;
      
      try {
          try {
				flow = super.getFlow();
			} catch (GeneralSecurityException e) {
				LOG.error(e.getLocalizedMessage());
			}
          
          credential = flow.loadCredential(accountName);

          if (credential != null && credential.getAccessToken() != null) {
          	  LOG.warn("User already added and saved in store");
              response = JSONResponse.responseString("error", "User already added and saved in store");
          } else {
              // redirect to the authorization flow
              final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
              authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfig().getAuth2CallbackUrl()));
              // store userId in session for CallBack Access
              session.setAttribute("userId", userKey);
              session.setAttribute("accountName", accountName);
              
              //response = "redirect:" + authorizationUrl.build();
              
              response = JSONResponse.responseString("redirect",  authorizationUrl.build());
          }
      } catch (IOException e) {
          LOG.error("Error while loading credential (or Google Flow)", e);
      }
      // only when error occurs, else redirected BEFORE
      return response;
    }
	
	/**
     * Handle the Google response.
     * @param request The HTTP Request
     * @param code    The (encoded) code use by Google (token, expirationDate,...)
     * @param session the HTTP Session
     * @return the view to display
     * @throws ServletException When Google account could not be connected to DaP.
     */
    @RequestMapping("/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request, final HttpSession session) 
    		throws ServletException {
    	
        final String decodedCode = extracCode(request);
        final String redirectUri = buildRedirectUri(request, getConfig().getAuth2CallbackUrl());
        final String userId = getUserid(session);
        final String accountName = getAccountName(session);
        
        try {
            GoogleAuthorizationCodeFlow flow = null;
			try {
				flow = super.getFlow();
			} catch (GeneralSecurityException e) {
				LOG.error(e.getLocalizedMessage());
			}

            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();

            final Credential credential = flow.createAndStoreCredential(response, accountName);
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + accountName);
                return JSONResponse.responseString("error", "Trying to store a NULL AccessToken for user : " + accountName);
            } else {
            	AppUser user = appUserRepo.findByUserKey(userId);
            	if (user == null) {
            		user = new AppUser();
            		user.setUserKey(userId);
            	}
            	
            	GoogleAccount newGoogleAccount = new GoogleAccount();
            	newGoogleAccount.setName(accountName);
            	
            	user.addGoogleAccount(newGoogleAccount);
            	
                appUserRepo.save(user);
                
            	LOG.info("Create new user "+ accountName +" and add new google account");
            }

            if (LOG.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                    LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                            + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                                    SENSIBLE_DATA_LAST_CHAR));
                }
            }
            // onSuccess(request, resp, credential);
        } catch (IOException e) {
            LOG.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to connect Google Account");
        }

        return "redirect:/?userKey=" + userId;
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
     * retrieve the accountName in Session.
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
            LOG.error("accountName in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : accountName is NULL is User Session");
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

}
