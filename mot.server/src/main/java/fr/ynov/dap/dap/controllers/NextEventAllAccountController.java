package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.NextEventAllAccountService;

/**
 * The Class NextEventAllAccountController.
 */
@RestController
public class NextEventAllAccountController {

	@Autowired
	private NextEventAllAccountService nextEventOfAllAccountService;

	/**
	 * Next event of all account.
	 *
	 * @param model   the model
	 * @param userKey the user key
	 * @return the next Event of all accounts
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO mot by Djer |Spring| le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	@RequestMapping("/nextEventOfAllAccount")
	public String nextEventOfAllAccount(ModelMap model,
			@RequestParam(value = "userKey", required = true) String userKey)
			throws IOException, GeneralSecurityException {
		String nextEvent = "";

		nextEvent = nextEventOfAllAccountService.getNextEvent(userKey);

		return nextEvent;
	}

}
