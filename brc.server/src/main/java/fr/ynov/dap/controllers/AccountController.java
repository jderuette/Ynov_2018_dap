package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.data.GoogleAccount;
import fr.ynov.dap.google.data.GoogleAccountRepository;
import fr.ynov.dap.google.service.GoogleAccountService;
import fr.ynov.dap.microsoft.AuthHelper;


/**
 * The Class AccountController.
 */
@Controller
public class AccountController {

	/** The google account service. */
	@Autowired 
	GoogleAccountService googleAccountService;
	
	/** The app user repository. */
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The google account repository. */
	@Autowired
	GoogleAccountRepository googleAccountRepository;
	
	/** The logger. */
	final static Logger logger = LogManager.getLogger(AccountController.class);

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
	 * Adds the google account.
	 *
	 * @param accountName the account name
	 * @param userKey the user key
	 * @param request the request
	 * @param session the session
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/account/add/{accountName}")
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
	
	/**
	 * Adds the microsoft account.
	 *
	 * @param accountName the account name
	 * @param userKey the user key
	 * @param response the response
	 * @param session the session
	 */
	@RequestMapping("/microsoft/account/add/{accountName}")
	public void addMicrosoftAccount (@PathVariable final String accountName, 
			@RequestParam final String userKey, final HttpServletResponse response, final HttpSession session) {
		
		UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();
		
		session.setAttribute("state", state);
        session.setAttribute("nonce", nonce);
        session.setAttribute("userKey", userKey);
        session.setAttribute("msAccount", accountName);
        
		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		try {
			response.sendRedirect(loginUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
