package fr.ynov.dap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;

/**
 * The Class UserController.
 */
@Controller
public class UserController {

	/** The app user repostory. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	public AppUserRepostory appUserRepostory;

	/**
	 * Adds the userkey.
	 *
	 * @param userKey the user key
	 * @return the string
	 */
	@RequestMapping("/user/add")
	public String addUserkey(@RequestParam final String userKey) {
		AppUser userToSave = new AppUser();
		userToSave.setUserKey(userKey);
	
		appUserRepostory.save(userToSave);
		
		return "index";
	}
}
