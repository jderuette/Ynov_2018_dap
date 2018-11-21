package fr.ynov.dap.dap.controller;

import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.data.GoogleAccountRepository;
import fr.ynov.dap.dap.service.GoogleAccountService;

/**
 * The Class AccountController.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	/** The google account. */
	@Autowired
	private GoogleAccountService googleAccountService;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	GoogleAccountRepository googleRepository;
	 /**
 	 * Adds the acount.
 	 *
 	 * @param userId the user id
 	 * @param request the request
 	 * @param session the session
 	 * @return the string
 	 * @throws GeneralSecurityException the general security exception
 	 */
 	@RequestMapping("/add/{accountName}")
	 public String addAcount(@PathVariable final String accountName, @RequestParam final String userKey,final HttpServletRequest request, final HttpSession session) throws GeneralSecurityException {
		AppUser user = appUserRepository.findByUserkey(userKey);
 		String response = googleAccountService.addAccount(accountName, request, session);
 		if(!response.isEmpty()){
 			GoogleAccount googleAccount = new GoogleAccount();
 			googleAccount.setName(accountName);
 			googleAccount.setOwner(user);
 			googleRepository.save(googleAccount);
 		}
 		return response;
	}
 	
}
