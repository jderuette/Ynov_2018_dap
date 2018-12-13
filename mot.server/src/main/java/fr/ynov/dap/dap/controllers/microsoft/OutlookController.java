package fr.ynov.dap.dap.controllers.microsoft;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.services.microsoft.OutlookService;
import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;

/**
 * The Class OutlookController.
 */
@Controller
public class OutlookController {

	@Autowired
	OutlookService outlookService;

	@Autowired
	AppUserRepository appUserRepository;

	/**
	 * Nb mail.
	 *
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return Thymeleaf Template nbUnreadMail
	 */
	//TODO mot by Djer |Spring| Le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	//TODO mot by Djer |Sping| Si tu n'as pas besoin de la "HttpServletRequest" et/ou de la "RedirectAttributes" ne les met pas en paramètre de ta méthode
	@RequestMapping("/nbMailByAccountMicrosoft")
	public String nbMail(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		Integer nbUnreadEmails = 0;

		if (outlookService.getUnreadEmails(userKey) != null) {
			nbUnreadEmails = outlookService.getUnreadEmails(userKey);
		}

		model.addAttribute("nbUnreadEmails", nbUnreadEmails);

		return "nbUnreadMail";
	}

	/**
	 * Mail.
	 *
	 * @param name               the name
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return ThymeLeaf template ListMail OR Error Template
	 */
	//TODO mot by Djer |Sping| Si tu n'as pas besoin de la "HttpServletRequest" et/ou de la "RedirectAttributes" ne les met pas en paramètre de ta méthode
	@RequestMapping("/listMailByAccountMicrosoft/{accountName}")
	public String mail(@PathVariable(value = "accountName") final String accountName,
			@RequestParam(value = "userKey", required = true) String userKey, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
	    
	    //TODO mot by Djer |JPA| A partir d'un "accountName" tu peux récupérer un "userKey". Tu pourrais te passer du "userKey" sauf si tu souhaites l'utiliser pour vérifier que c'est bien le "bon" utilisateur. Dans ce cas assure-toi que le userKey est bien associé à CE accountName

		if (outlookService.getListMessage(userKey, accountName) != null) {
			Message[] messageList = outlookService.getListMessage(userKey, accountName);

			model.addAttribute("messages", messageList);
			model.addAttribute("name", accountName);
			model.addAttribute("userKey", userKey);
		} else {
		    //TODO mot by Djer |API Microsoft| Ne pas avoir d'email n'est pas vraiment une "erreur", je pourrais utiliser QUE les calendar Microsoft
			model.addAttribute("errorMessage", "No email found for this account");
			return "error";
		}

		return "listMail";
	}

}
