package fr.ynov.dap.controller.google;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.google.GoogleAccountRepository;
import fr.ynov.dap.service.Google.GoogleGmailService;



/**
 * 
 * @author Florent
 * Handle all the request of the Gmail service
 */
@RestController
@RequestMapping(value="/google/mail")
public class GoogleGmailController {

	@Autowired
	private GoogleGmailService mailService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	/**
	 * 
	 * TODO bof by Djer |JavaDoc| Configure PMD/checkstyle pour qu'il t'aide a voir que le paramètre ci-dessous n'a plsu le bon nom dans ta doc
	 * @param userID Id of the user to access data
	 * @return The request response formated in JSON
	 * @throws Exception
	 * Map the path /count to the associated service method
	 */
	@RequestMapping(value="/inbox")
	public MasterModel getInboxMail(@RequestParam final String userKey) throws IOException, Exception {
		int inboxSum = 0;
		if(null != appUserRepository.findByUserKey(userKey)) {
			for (GoogleAccountModel googleAccount : appUserRepository.findByUserKey(userKey).getGoogleAccounts()) {
				MailModel inbox = (MailModel) mailService.getInboxMail(googleAccount.getAccountName());
				inboxSum += inbox.getNbOfEmail();
			}
		}
		return new MailModel(inboxSum);
	}
	
}
