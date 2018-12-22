package fr.ynov.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.GmailService;
import fr.ynov.dap.models.NbMailResponse;

import org.springframework.ui.ModelMap;

/**
 * The Class WelcomeController.
 */
@Controller
public class WelcomeController {

	/** The gmail service. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired 
	GmailService gmailService;
	
	/** The app user repository. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	AppUserRepostory appUserRepository;
	
	/**
	 * Welcome.
	 *
	 * @param model the model
	 * @param accountName the account name
	 * @param userKey the user key
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/welcome")
	public String welcome(ModelMap model, @PathVariable final String accountName,
			@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		NbMailResponse output = new NbMailResponse(gmailService.getNbUnreadMailForAccount(currentUser));
		model.addAttribute("nbEmails",output.getNbUnreadMail());
		model.addAttribute("userKey", userKey);
		return "welcome";
	}
}
