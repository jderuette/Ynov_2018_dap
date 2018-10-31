package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.model.MailModel;
import fr.ynov.dap.dap.model.MasterModel;
import fr.ynov.dap.dap.service.GmailService;

/**
 * 
 * @author Florent
 * Handle all the request of the Gmail service
 */
@RestController
@RequestMapping(value="/mail")
public class GoogleGmailController {

	@Autowired
	private GmailService mailService;
	
	/**
	 * 
	 * @param userID Id of the user to access data
	 * @return The request response formated in JSON
	 * @throws Exception
	 * Map the path /count to the associated service method
	 */
	@RequestMapping(value="/inbox")
	public MasterModel getInboxMail(@RequestParam final String userID) throws IOException, Exception {
		return mailService.getInboxMail(userID);
	}
	
}
