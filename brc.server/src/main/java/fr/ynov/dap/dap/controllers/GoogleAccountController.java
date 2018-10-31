package fr.ynov.dap.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.ynov.dap.dap.GoogleAccountService;

/**
 * The Class GoogleAccountController.
 */
@Controller
public class GoogleAccountController {

	/** The google account service. */
	@Autowired 
	GoogleAccountService googleAccountService;
	
	/**
	 * O auth callback.
	 *
	 * @param code the code
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/oAuth2Callback")
	public String oAuthCallback (@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session) throws IOException, Exception {
		
		return googleAccountService.oAuthCallback(code, request, session);
	}
	
	/**
	 * Adds the account.
	 *
	 * @param userId the user id
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/account/add/{userId}")
	public String addAccount (@PathVariable final String userId, final HttpServletRequest request,
            final HttpSession session) throws IOException, Exception {
		
		return googleAccountService.addAccount(userId, request, session);
	}
}
