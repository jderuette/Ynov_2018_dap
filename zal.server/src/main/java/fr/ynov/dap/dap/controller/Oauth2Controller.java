package fr.ynov.dap.dap.controller;

import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.service.GoogleAccountService;

/**
 * The Class Oauth2Controller.
 */
@Controller
public class Oauth2Controller {
	
	/** The google account. */
	@Autowired
	private GoogleAccountService googleAccount;
	
	/**
	 * O auth callback.
	 *
	 * @param code the code
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws Exception the exception
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping(value= "/oAuth2Callback")
    public String oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws Exception, GeneralSecurityException{
 		 return googleAccount.oAuthCallback(code, request, session);
 	}
}
