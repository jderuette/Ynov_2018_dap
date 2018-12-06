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
	    //TODO bot by Djer |POO| Tu ne devrais appeler ton service qu'une fois, puis extraire les deux infos du resultat. Petit risque sur les PERF, mais SURTOUT tu pourrais avoir une désynchro entre les deux réponses, et tu produis deux listes tu supose qu'elle sont "synchroniser (le 3ème de l'une corespond au 3ème de l'autre).
		model.put("users",
				outlookMailService.getMailForAllAccounts(userKey)
				.get("users"));
		model.put("messages",
				outlookMailService.getMailForAllAccounts(userKey)
				.get("messages"));

		return "mail";
	}
}
