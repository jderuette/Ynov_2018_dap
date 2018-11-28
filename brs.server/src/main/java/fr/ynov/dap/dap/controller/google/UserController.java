package fr.ynov.dap.dap.controller.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.service.google.GoogleAccountService;
import fr.ynov.dap.dap.service.google.GoogleServices;

@Controller

public class UserController {
	
	@Autowired 
	public AppUserRepository repo;
	
	@Autowired
	private GoogleAccountService googleAccountServices;
	
	
	
	@RequestMapping("/user/add/{userKey}")
	public String addAccount(@PathVariable final String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		String response = "errorOccurs";
		GoogleAuthorizationCodeFlow flow;
		Credential credential = null;
		try {
			flow = googleAccountServices.getFlow();
			credential = flow.loadCredential(userKey);

			if (credential != null && credential.getAccessToken() != null) {
				response = "AccountAlreadyAdded";
			} else {
				// redirect to the authorization flow
				final AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl();
				authorizationUrl.setRedirectUri(googleAccountServices.buildRedirectUri(request, googleAccountServices.getConfiguration().getoAuth2CallbackUrl()));
				// store userId in session for CallBack Access
				session.setAttribute("userKey", userKey);
				AppUser user = new AppUser();
				user.setUserkey(userKey);
				repo.save(user);
				//response = "redirect:" + authorizationUrl.build();
			}
		} catch (IOException e) {
			//LOG.error("Error while loading credential (or Google Flow)", e);
		}
		// only when error occurs, else redirected BEFORE
		AppUser user0 = new AppUser();
		
		
		
		return repo.findByUserkey(userKey).getUserkey();
	}
	
	
	

	
}
