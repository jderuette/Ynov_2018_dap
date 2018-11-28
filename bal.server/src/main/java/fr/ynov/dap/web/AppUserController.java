package fr.ynov.dap.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.service.AppUserService;

/**
 * The Class AppUserController.
 */
@RestController
public class AppUserController {

	/** The app user service. */
	@Autowired
	AppUserService appUserService;

	/**
	 * Adds the user key.
	 *
	 * @param userId the user id
	 * @return the map
	 */
	@RequestMapping(value="/user/add/{userKey}")
	public Map<String, Object> addUserKey(@RequestParam("userKey") final String userId) {
		return appUserService.addAppUser(userId);
	}
}
