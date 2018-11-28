package fr.ynov.dap.dap.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;

/**
 * The Class UserController.
 */
@RestController
public class UserController {

	/** The app user repository. */
	@Autowired
	AppUserRepository appUserRepository;

	/**
	 * Adds the user.
	 *
	 * @param userKey
	 *            the user key
	 * @return the string
	 */
	@RequestMapping("/user/add")
	public String addUser(@RequestParam final String userKey) {
		AppUser user = appUserRepository.findByUserkey(userKey);
		if (user == null) {
			user = new AppUser();
			user.setUserKey(userKey);
			appUserRepository.save(user);

			return "the user is now added";
		}
		return "the user is already added";
	}
}
