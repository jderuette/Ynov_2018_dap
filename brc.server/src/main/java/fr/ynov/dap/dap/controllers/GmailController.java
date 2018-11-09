package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.GmailService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;
import fr.ynov.dap.dap.models.GmailResponse;

/**
 * The Class GmailController.
 */
@RestController
public class GmailController {

	/** The gmail service. */
	@Autowired 
	GmailService gmailService;
	
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The logger. */
	private final static Logger logger = LogManager.getLogger(GmailController.class);	
	
	/**
	 * Gets the unread mail.
	 *
	 * @param userId the user id
	 * @return the gmail response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/gmail")
	public int GetUnreadMail (ModelMap model, @RequestParam final String userKey) throws IOException, Exception {
		
		logger.info("-- start -- get unread mails for : " + userKey);
		
		AppUser appUser = appUserRepository.findByUserkey(userKey);
		int totalUnreadMail = 0;
		logger.info("appuser : " + appUser.getUserKey());
		if(appUser != null) {
			for(int i=0; i < appUser.getGoogleAccounts().size(); i++) {
				String accountName = appUser.getGoogleAccounts().get(i).getName();
				logger.info("accountName : " + accountName);

				if(accountName != null) {
					GmailResponse response = gmailService.resultMailInbox(accountName);
					totalUnreadMail += response.getNbUnreadMail();
				}
			}
		}
		return totalUnreadMail;
		
	}
}
