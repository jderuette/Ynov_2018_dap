package fr.ynov.dap.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping(value="/mail")
public class GoogleGmailController {
	/*
	 * @Autowired private GmailService mailService;
	 * 
	 * 
	 * @Autowired private AppUserRepository appUserRepository;
	 * 
	 * /**
	 * 
	 * @param userID Id of the user to access data
	 * 
	 * @return The request response formated in JSON
	 * 
	 * @throws Exception Map the path /count to the associated service method
	 *
	 * @RequestMapping(value="/inbox") public MasterModel getInboxMail(@RequestParam
	 * final String userKey) throws IOException, Exception { int inboxSum = 0;
	 * if(null != appUserRepository.findByUserKey(userKey)) { for
	 * (GoogleAccountModel googleAccount :
	 * appUserRepository.findByUserKey(userKey).getGoogleAccounts()) { MailModel
	 * inbox = (MailModel) mailService.getInboxMail(googleAccount.getAccountName());
	 * inboxSum += inbox.getNbOfEmail(); } } return new MailModel(inboxSum); }
	 */
}
