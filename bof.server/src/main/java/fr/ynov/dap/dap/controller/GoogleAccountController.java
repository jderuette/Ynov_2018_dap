package fr.ynov.dap.dap.controller;

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

import fr.ynov.dap.dap.model.AppUserModel;
import fr.ynov.dap.dap.model.GoogleAccountModel;
import fr.ynov.dap.dap.repository.AppUserRepository;
import fr.ynov.dap.dap.repository.GoogleAccountRepository;
import fr.ynov.dap.dap.service.GoogleAccountService;

@Controller
public class GoogleAccountController {

	@Autowired
	private GoogleAccountService googleAccountService;
	
	
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private GoogleAccountRepository googleAccountRepository;

	@RequestMapping("/oAuth2Callback")
	public String oAuth2Callback(@RequestParam final String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException, GeneralSecurityException {
		return googleAccountService.oAuthCallback(code, request, session);
	}
	
	@RequestMapping("/oAuthSuccess")
	public String oAuthSuccess(final HttpServletRequest request, final HttpSession session) {
		
		String userKey = (String) session.getAttribute("userKey");
		String accountName = (String) session.getAttribute("accountName");
		
		AppUserModel appUserModel = appUserRepository.findByUserKey(userKey);
		GoogleAccountModel googleAccountModel = new GoogleAccountModel();

		googleAccountModel.setOwner(appUserModel);
		
		googleAccountModel.setAccountName(accountName);
		appUserModel.getGoogleAccounts().add(googleAccountModel);
		
		appUserRepository.save(appUserModel);
		return "redirect:/";
	}

/*	@RequestMapping(value="/account/add/{accountName}", method = RequestMethod.GET)
	public String addAccount(@PathVariable final String accountName,@RequestParam String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		return googleAccountService.addAccount(accountName, request, session);
	}*/
	
	@RequestMapping(value="/account/add/{accountName}")
	public String addAccount(@PathVariable final String accountName,@RequestParam String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException {
		if(appUserRepository.findByUserKey(userKey) != null) {
			return googleAccountService.addAccount(accountName,userKey, request, session);
		}
		return "redirect:/";
	}

}
