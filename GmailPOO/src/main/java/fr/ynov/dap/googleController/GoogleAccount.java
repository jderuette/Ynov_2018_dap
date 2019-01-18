package fr.ynov.dap.googleController;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;

import fr.ynov.dap.googleService.GoogleService;
import fr.ynov.dap.metier.Data;
import javassist.NotFoundException;

@Controller
public class GoogleAccount extends GoogleService {
@Autowired
private Data maDataBase;
private static final  Logger LOG = LogManager.getLogger(GoogleAccount.class);
	 public GoogleAccount() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		super();
		
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
     @RequestMapping("/Callback")
    public String oAuthCallback(@RequestParam(value="code")  String code, final HttpServletRequest request,
            final HttpSession session,Model model) throws ServletException, GeneralSecurityException {
    	
      final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, "/Callback");
         
        final String userId = getUserid(session);
        final String userKey = getUserKey(session);
        model.addAttribute("add","Add Google Account");
        
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
            session.setAttribute("TokenGoogle",response);             

            final com.google.api.client.auth.oauth2.Credential credential = flow.createAndStoreCredential(response, userId);
            maDataBase.ajouterAccountGoogle(userKey, "", userId, response);
            model.addAttribute("onSuccess",userId+" ajouté avec succes");
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
                model.addAttribute("NotAdd", "Trying to store a NULL AccessToken for user : " + userId);
            }

            if (LOG.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                 //   LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                   //         + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                        //            SENSIBLE_DATA_LAST_CHAR));
                }
            }
           
        } catch (IOException | NotFoundException e) {
            LOG.error("Exception while trying to store user Credential", e);
            model.addAttribute("NotAdd", "Exception while trying to store user Credential "+e.getMessage());
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "Info";
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
     * retrieve the User Key in Session.
     * @param session the HTTP Session
     * @return the current User Id in Session
     * @throws ServletException if no UserKey in session
     */
  
    private String getUserKey(final HttpSession session) throws ServletException {
        String userKey = null;
        if (null != session && null != session.getAttribute("userKey")) {
            userKey = (String) session.getAttribute("userKey");
           
        }

        if (null == userKey) {
            LOG.error("userId in Session is NULL in Callback");
            throw new ServletException("Error when trying to add Google acocunt : userId is NULL is User Session");
        }
        return userKey;
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
   @RequestMapping("/add/google/account/{accountName}")
   
   public String addAccount1(@PathVariable("accountName") final String userId, @RequestParam(name = "userKey") String userKey, final HttpServletRequest request,
           final HttpSession session, Model model) throws GeneralSecurityException {
       String response = "errorOccurs";
       GoogleAuthorizationCodeFlow flow;
       com.google.api.client.auth.oauth2.Credential credential = null;
       model.addAttribute("add","Add Google Account");
       try {
           flow = super.getFlow(); 
           credential = flow.loadCredential(userId);

           if (credential != null && credential.getAccessToken() != null) {
        	  
           	model.addAttribute("NotAdd", userId + "  Account Already Added ");
         	
   			LOG.info(userId + "  AccountAlreadyAdded ");
        	response = "Info";
   
           } else {
              
               final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
               authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getMapping()));
             
               session.setAttribute("userId", userId);
               session.setAttribute("userKey", userKey);
               response = "redirect:" + authorizationUrl.build();
           }
       } catch (IOException e) {
           LOG.error("Error while loading credential (or Google Flow)", e);
       }
 
       return response;
   }
}
