package fr.ynov.dap.dap.controller.google;




import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.service.google.GoogleAccountService;
//TODO brs by Djer |IDE| Configure les "save action" de ton IDE pour éviter de laisser trainer des imports inutiles
import fr.ynov.dap.dap.service.google.GoogleServices;



@Controller
public class AccountController {
	
	@Autowired 
	public AppUserRepository repo;
	
	@Autowired
	private GoogleAccountService googleServices;
	
	
	@RequestMapping("/add/account/{accountName}")
	public String addAccount(@RequestParam("userKey") final String userKey, @PathVariable final String accountName, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow;
		Credential credential = null;
		try {
			flow = googleServices.getFlow();
			credential = flow.loadCredential(accountName);

			if (credential != null && credential.getAccessToken() != null) {
				response = "AccountAlreadyAdded";
			} else {
				// redirect to the authorization flow
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(googleServices.buildRedirectUri(request, googleServices.getConfiguration().getoAuth2CallbackUrl()));
				// store userId in session for CallBack Access
				session.setAttribute("userKey", userKey);
				session.setAttribute("accountName", accountName);
				
				
				response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
		    //TODO brs by Djer |Log4J| Pourquoi enlever cette LOG ? Tu "étouffe" cette exception c'est quand même bien d'avoir un minimum d'infos !
			//LOG.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		return response;
	}
	
	
	

}
