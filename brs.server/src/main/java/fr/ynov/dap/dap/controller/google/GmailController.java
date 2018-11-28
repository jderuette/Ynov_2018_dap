package fr.ynov.dap.dap.controller.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import Utils.LoggerUtils;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.service.google.GmailService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftOutlookService;

/**
 * The Class GmailController.
 */
@Controller
@RequestMapping("/email")
public class GmailController extends LoggerUtils {
	
	@Autowired
	private GmailService gmailService;
	@Autowired
	private MicrosoftOutlookService outlookService;
	
	@Autowired 
	public AppUserRepository repo;
	/**
	 * Gets the label.
	 *
	 * @param user the user
	 * @return the label
	 * @throws GeneralSecurityException 
	 * @throws IOException 
	 */
	@RequestMapping("/nbUnread")
	public String getUnreadEmail(@RequestParam("userKey") String userKey, Model model) throws IOException, GeneralSecurityException {
		Integer nbMailUnread = 0;
		nbMailUnread = gmailService.getNbMailUnread(userKey);
		nbMailUnread += outlookService.getNbMailUnread(userKey);
		model.addAttribute("nbMail", nbMailUnread);
		model.addAttribute("content", "maxMail");
		
		
		return "index";

	}
}
