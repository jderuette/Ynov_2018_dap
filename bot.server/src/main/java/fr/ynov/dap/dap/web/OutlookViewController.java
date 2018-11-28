package fr.ynov.dap.dap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.microsoft.OutlookMailService;

/**
 * The Class OutlookViewController.
 */
@Controller
@RequestMapping("/outlook")
public class OutlookViewController {
	
	/** The outlook mail service. */
	@Autowired
	OutlookMailService outlookMailService;

	/**
	 * Gets the mail for all accounts.
	 *
	 * @param userKey the user key
	 * @param model the model
	 * @return the mail for all accounts
	 */
	@RequestMapping("/mails")
	public String getMailForAllAccounts(@RequestParam("userKey") final String userKey,
			ModelMap model) {
		model.put("users",
				outlookMailService.getMailForAllAccounts(userKey)
				.get("users"));
		model.put("messages",
				outlookMailService.getMailForAllAccounts(userKey)
				.get("messages"));

		return "mail";
	}
}
