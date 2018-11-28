package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.GoogleMailService;
import fr.ynov.dap.dap.microsoft.OutlookMailService;

/**
 * The Class GmailController.
 */
@RestController
@RequestMapping("/mail")
public class MailController {

	/** The gmail service. */
	@Autowired
	private GoogleMailService gmailService;
	
	/** The outlook mail service. */
	@Autowired
	OutlookMailService outlookMailService;

	/**
	 * Gets the mail inbox un read.
	 *
	 * @param userKey the user key
	 * @return the mail inbox un read
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping(value = "/inbox")
	public Map<String, Integer> getMailInboxUnRead(@RequestParam("userKey") final String userKey)
			throws IOException, GeneralSecurityException {
		Map<String,Integer> response = new HashMap<String, Integer>();
		response.put("outlook", outlookMailService.getNbMailInboxForAllAccount(userKey));
		response.put("google", gmailService.getNbMailInboxForAllAccount(userKey));
		return response;
	}

}
