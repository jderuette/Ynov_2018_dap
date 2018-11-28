package fr.ynov.dap.controllers.microsoft;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.data.Message;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.services.microsoft.MicrosoftAccountService;
import fr.ynov.dap.services.microsoft.OutlookService;
import fr.ynov.dap.utils.ExtendsUtils;

/**
 * The Class MicrosoftOutlookController.
 */
@RequestMapping("/microsoft")
@Controller
public class MicrosoftOutlookController extends ExtendsUtils {

	/** The microsoft account service. */
	@Autowired
	MicrosoftAccountService microsoftAccountService;

	/** The outlook service. */
	@Autowired
	OutlookService outlookService;

	/** The app user repo. */
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Mail.
	 *
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@RequestMapping("/mail")
	public String mail(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Integer nbUnreadEmails = outlookService.getUnreadEmails(userKey);
		LOG.info("mails : " + nbUnreadEmails);

		if (nbUnreadEmails == null) {
			LOG.error("email's empty");
			return "error";
		}

		model.addAttribute("successMsg", "Emails : " + nbUnreadEmails);
		return "success";
	}

	/**
	 * Mail by account.
	 *
	 * @param username           the username
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@RequestMapping("/mail/{username}")
	public String mailByAccount(@PathVariable(value = "username") final String username,
			@RequestParam(value = "userKey", required = true) String userKey, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		Message[] messages = outlookService.getMessages(userKey, username);

		if (messages == null) {
			String error = "messages list's empty";

			LOG.error(error);
			model.addAttribute("errorMsg", error);
			return "error";
		}

		model.addAttribute("user", "?userKey=" + userKey);
		model.addAttribute("messages", messages);
		model.addAttribute("currentName", username.toUpperCase());

		return "messages";
	}
}