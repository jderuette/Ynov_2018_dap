package fr.ynov.dap.GmailPOO.metier;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mortbay.jetty.security.Credential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;


import fr.ynov.dap.GmailPOO.data.AppUser;
import javassist.NotFoundException;

@Controller
public class GoogleAccount extends GoogleService {
@Autowired
private Data maDataBase;
	 public GoogleAccount() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		super();
		// TODO Auto-generated constructor stub
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
  //  @RequestMapping("/Callback")
    public String oAuthCallback(@RequestParam(value="code")  String code, final HttpServletRequest request,
            final HttpSession session) throws ServletException, GeneralSecurityException {
    	
      final String decodedCode = extracCode(request);

        final String redirectUri = buildRedirectUri(request, "/Callback");
         
        final String userId = getUserid(session);
        final String userKey = getUserKey(session);
 
        
        try {
            final GoogleAuthorizationCodeFlow flow = super.getFlow();
            final TokenResponse response = flow.newTokenRequest(decodedCode).setRedirectUri(redirectUri).execute();
            session.setAttribute("TokenGoogle",response);             

            final com.google.api.client.auth.oauth2.Credential credential = flow.createAndStoreCredential(response, userId);
            maDataBase.ajouterAccountGoogle(userKey, "", userId, response);
            if (null == credential || null == credential.getAccessToken()) {
                LOG.warn("Trying to store a NULL AccessToken for user : " + userId);
            }

            if (LOG.isDebugEnabled()) {
                if (null != credential && null != credential.getAccessToken()) {
                 //   LOG.debug("New user credential stored with userId : " + userId + "partial AccessToken : "
                   //         + credential.getAccessToken().substring(SENSIBLE_DATA_FIRST_CHAR,
                        //            SENSIBLE_DATA_LAST_CHAR));
                }
            }
            // onSuccess(request, resp, credential);
        } catch (IOException | NotFoundException e) {
            LOG.error("Exception while trying to store user Credential", e);
            throw new ServletException("Error while trying to conenct Google Account");
        }

        return "redirect:/ajouter?userEmail=";
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
 //  @RequestMapping("/add/account/{accountName}?userKey={userKey}")
    //TODO bes by Djer |POO| (ancine ?) code mort, a supprimer !
    public String addAccount( String userKey, final HttpServletRequest request,
            final HttpSession session) throws GeneralSecurityException {
	 
        String response = "errorOccurs";
        GoogleAuthorizationCodeFlow flow;
        
        //TODO bes by Djer |IDE| Ton IDE t'indique que cette variable n'est pas utilisée. Bug ? A supprimer ? 
        com.google.api.client.auth.oauth2.Credential credential = null;
       
        try {
            flow = super.getFlow();
            credential = flow.loadCredential(userKey);
        
            	  // redirect to the authorization flow
                final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
                authorizationUrl.setRedirectUri( getConfiguration().getoAuth2CallbackUrl());
                // store userId in session for CallBack Access
                session.setAttribute("userId", userKey);
                response = authorizationUrl.build();
            	
       
        } catch (IOException e) {
            LOG.error("Error while loading credential (or Google Flow)", e);
        }
        // only when error occurs, else redirected BEFORE
        return response;
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
   
   //TODO bes by Djer |Spring| pour le requestParam "userKey", "required=true" est la valeur par defaut, il n'est pas utile de le préciser et si tu met juste une "chanioe de texte" cela sera la value. Tu peux donc simplifier le contenu de ton association. Et si tu ne met aucune valeur, Spring prendra le nom du paramètre comme nom de clef (cette dernière option est pratique, mais risquée)
   public String addAccount1(@PathVariable("accountName") final String userId, @RequestParam(name = "userKey", required=true) String userKey, final HttpServletRequest request,
           final HttpSession session, Model model) throws GeneralSecurityException {
       String response = "errorOccurs";
       GoogleAuthorizationCodeFlow flow;
       com.google.api.client.auth.oauth2.Credential credential = null;
       try {
           flow = super.getFlow();
           credential = flow.loadCredential(userId);

           if (credential != null && credential.getAccessToken() != null) {
        	  
           	model.addAttribute("NotAdd", userId + "  AccountAlreadyAdded ");
   			LOG.info(userId + "  AccountAlreadyAdded ");
        	   response = "addUser";
   
           } else {
               // redirect to the authorization flow
               final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
               authorizationUrl.setRedirectUri(buildRedirectUri(request, getConfiguration().getMapping()));
               // store userId in session for CallBack Access
               session.setAttribute("userId", userId);
               session.setAttribute("userKey", userKey);
               response = "redirect:" + authorizationUrl.build();
           }
       } catch (IOException e) {
           LOG.error("Error while loading credential (or Google Flow)", e);
       }
       // only when error occurs, else redirected BEFORE
       return response;
   }
}
