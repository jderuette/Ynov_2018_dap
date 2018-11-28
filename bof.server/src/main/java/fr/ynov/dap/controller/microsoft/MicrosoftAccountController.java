package fr.ynov.dap.controller.microsoft;

import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.service.microsoft.MicrosoftAccountService;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftAccountController {
	
	@Autowired
	MicrosoftAccountService microsoftAccountService;
	
	@RequestMapping(value="/account/add/{accountName}")
	public String AddAccount(@PathVariable final String accountName,@RequestParam String userKey, Model model, HttpServletRequest request, final HttpSession session) throws GeneralSecurityException {
		return microsoftAccountService.addAccount(accountName, userKey, request, session, model);
	}
	
	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	public String authorize(
			@RequestParam("code") String code, 
			@RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state,
			HttpServletRequest request) throws ServletException {
		
		return microsoftAccountService.authorize(code, idToken, state, request);
		
	}
}
