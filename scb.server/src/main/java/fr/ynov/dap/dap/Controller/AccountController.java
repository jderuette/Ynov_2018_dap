package fr.ynov.dap.dap.Controller;
//TODO scb by Djer évite les Majusucle dans les nom de package (au pire camelCase)

import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.Services.google.GoogleAccount;

@Controller
public class AccountController{
	
	@Autowired
	GoogleAccount googleAccount;
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
	@RequestMapping("/account/add/{userId}")
	public String addAccount(@PathVariable final String userId, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		
		String response = googleAccount.addAccount(userId, request, session);
		return response;
	}
	
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
	@RequestMapping("/oAuth2CallBack")
	public String oAuth2CallBack(@RequestParam final String code, final HttpServletRequest request, final HttpSession session) throws ServletException, GeneralSecurityException {
			googleAccount.oAuthCallback(code, request, session);
			return "redirect:/";
	}
}