package fr.ynov.dap.dap.web;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.AppUserService;

/**
 * The Class UserController.
 */
@RestController
public class UserController {
	
	/** The app service. */
	@Autowired
	AppUserService appService;

	/**
	 * Adds the user.
	 *
	 * @param userKey the user key
	 * @return the map
	 */
	@RequestMapping("/user/add/{userKey}")
	public Map<String, Object> addUser(@PathVariable final String userKey) {
		return appService.addUser(userKey);
	}
}
