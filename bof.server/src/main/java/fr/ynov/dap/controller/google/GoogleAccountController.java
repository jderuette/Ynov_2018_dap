package fr.ynov.dap.controller.google;

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

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.google.GoogleAccountRepository;
import fr.ynov.dap.service.Google.GoogleAccountService;

@Controller

public class GoogleAccountController {

	@Autowired
	private GoogleAccountService googleAccountService;
	
	

	@RequestMapping("/oAuth2Callback")
	public String oAuth2Callback(@RequestParam final String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException, GeneralSecurityException {
		return googleAccountService.oAuthCallback(code, request, session);
	}
	
	@RequestMapping(value="/google/account/add/{accountName}")
	public String addAccount(@PathVariable final String accountName,@RequestParam String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
			return googleAccountService.addAccount(accountName,userKey, request, session);
	}

}
