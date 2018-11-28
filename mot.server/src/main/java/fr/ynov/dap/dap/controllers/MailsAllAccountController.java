package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.MailsAllAccountService;

/**
 * The Class MailsAllAccountController.
 */
@RestController
public class MailsAllAccountController {

	@Autowired
	private MailsAllAccountService mailsOfAllAccountService;

	/**
	 * Mails account.
	 *
	 * @param model   the model
	 * @param userKey the user key
	 * @return the mailUnread of All Account
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/nbMailOfAllAccount")
	public Integer mailsAccount(ModelMap model, @RequestParam(value = "userKey", required = true) String userKey)
			throws IOException, GeneralSecurityException {
		Integer mailUnread = 0;

		mailUnread = mailsOfAllAccountService.getAllUnreadMail(userKey);

		return mailUnread;
	}

}
