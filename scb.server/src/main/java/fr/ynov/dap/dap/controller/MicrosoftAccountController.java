package fr.ynov.dap.dap.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.services.microsoft.OutlookAccountService;

/**
 * @author Brice
 *
 */
@Controller
public class MicrosoftAccountController {
    //TODO scb by Djer |POO| Attention précise la porté de chaque attributs ! (ici il est public comme la classe)
	@Autowired
	OutlookAccountService outlookAccount;
	
	
	/**
	 * Add a microsoft account to our app account
	 * @param accountName Name that will refer to the google account
	 * @param userKey Application Account
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/account/microsoft/add/{accountName}")
	public String addAccount(@PathVariable final String accountName, 
			 @RequestParam final String userKey , 
			 final HttpServletRequest request,
			 final HttpSession session) {
	    //TODO scb by Djer |MVC| Evite de passer des objets "web" à tes services (request).
		String response = outlookAccount.AddAccount(userKey, accountName, request);
		return response;
	}
	
	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	public String Authorize(@RequestParam("code") String code, 
		      @RequestParam("id_token") String idToken,
		      @RequestParam("state") UUID state,
		      HttpServletRequest request) {
		
		outlookAccount.authorize(request, state, code, idToken);
		return "redirect:/";
		
	}
}
