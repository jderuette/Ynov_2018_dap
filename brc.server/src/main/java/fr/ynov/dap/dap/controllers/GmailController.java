package fr.ynov.dap.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.GmailService;
import fr.ynov.dap.dap.models.GmailResponse;

/**
 * The Class GmailController.
 */
@RestController
public class GmailController {

	/** The gmail service. */
	@Autowired 
	GmailService gmailService;
	
	/**
	 * Gets the unread mail.
	 *
	 * @param userId the user id
	 * @return the gmail response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/gmail")
	public GmailResponse GetUnreadMail (@RequestParam final String userId) throws IOException, Exception {
		
		return gmailService.resultMailInbox(userId);
	}
	
}
