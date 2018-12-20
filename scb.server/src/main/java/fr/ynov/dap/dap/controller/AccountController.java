package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.ynov.dap.dap.services.google.GoogleAccountService;
import fr.ynov.dap.dap.services.microsoft.OutlookService;
import fr.ynov.dap.dap.services.microsoft.OutlookServiceFactory;

import java.util.UUID;
import org.springframework.ui.Model;
import fr.ynov.dap.dap.helpers.AuthHelper;
import fr.ynov.dap.dap.models.IdToken;
import fr.ynov.dap.dap.models.OutlookUser;
import fr.ynov.dap.dap.models.TokenResponse;

@Controller
public class AccountController{
	
	@Autowired
	GoogleAccountService googleAccount;
	
	/**
	 * Add a Google account (user will be prompt to connect and accept required
	 * access).
	 * @param accountName Name that will refer to the google account
	 * @param userKey Application Account related to the google account
	 * @param request
	 * @param session
	 * @return
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/account/add/{accountName}")
	public String addAccount(@PathVariable final String accountName, 
							 @RequestParam final String userKey , 
							 final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		
		String response = googleAccount.addAccount( userKey,accountName, request, session);
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
	
	/**
	 * @param userKey Application account name to add
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/user/add/{userKey}")
	@ResponseBody
	public String AddUser(@PathVariable final String userKey,final HttpServletRequest request, final HttpSession session) {
		String response = googleAccount.AddUser(userKey);
		return response;
	}
}