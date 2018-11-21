package fr.ynov.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.ynov.dap.dap.GoogleAccountService;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepostory;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.GoogleAccountRepository;

/**
 * The Class GoogleAccountController.
 */
@Controller
public class GoogleAccountController {

	/** The google account service. */
	@Autowired 
	GoogleAccountService googleAccountService;
	
	@Autowired
	AppUserRepostory appUserRepository;
	
	@Autowired
	GoogleAccountRepository googleAccountRepository;
	
	/** The logger. */
	final static Logger logger = LogManager.getLogger(GoogleAccountController.class);

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
		
		final String decodedCode = googleAccountService.extracCode(request);
        final String redirectUri = googleAccountService.buildRedirectUri(request, "/oAuth2Callback");
        final String userId = googleAccountService.getUserid(session);
        
		return googleAccountService.oAuthCallback(decodedCode, redirectUri, userId);
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
	@RequestMapping("/account/add/{accountName}")
	public String addAccount (@PathVariable final String accountName, @RequestParam final String userKey,
			final HttpServletRequest request, final HttpSession session) throws IOException, Exception {
		
		
		AppUser appUser = appUserRepository.findByUserkey(userKey);
		String response = googleAccountService.addAccount(accountName, request, session);
		
		GoogleAccount account = new GoogleAccount();
		final String userId = googleAccountService.getUserid(session);
		
		account.setName(userId);
		account.setOwner(appUser);
		
		appUser.addGoogleAccount(account);
		googleAccountRepository.save(account);
		
		return response;
	}
}
