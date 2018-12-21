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
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	MicrosoftAccountService microsoftAccountService;

	/** The outlook service. */
	//TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	OutlookService outlookService;

	/** The app user repo. */
	//TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
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
	//TODO thb by Djer |Spring| Si tu n'as pas besoin de request et redirectAttributes, ne les met pas dans la signature de ta méthode
	@RequestMapping("/mail")
	public String mail(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Integer nbUnreadEmails = outlookService.getUnreadEmails(userKey);
		LOG.info("mails : " + nbUnreadEmails);

		if (nbUnreadEmails == null) {
		    //TODO thb by Djer |Log4J| nbUnreadEmails == null ne veux pas forcément dire que l'email est vide.
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
	//TODO thb by Djer |Spring| Si tu n'as pas besoin de request et redirectAttributes, ne les met pas dans la signature de ta méthode
	@RequestMapping("/mail/{username}")
	public String mailByAccount(@PathVariable(value = "username") final String username,
			@RequestParam(value = "userKey", required = true) String userKey, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		Message[] messages = outlookService.getMessages(userKey, username);

		//TODO thb by Djer |POO| NE pas avoir de message n'est pas une erreur. Je pourais utilsier mon comtpe Microsoft que pour le calendrier. Un Level Warning serait suffisant pour la log. Et un simple message indiquant qu'il n'y a pas de message sur la page de "liste des mail" suffirai
		if (messages == null) {
			String error = "messages list's empty";

			//TODO thb by Djer |Log4J| Contextualise tes messages
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