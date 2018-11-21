package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.model.MailModel;
import fr.ynov.dap.dap.model.NbTotalEmailModel;
import fr.ynov.dap.dap.service.GmailService;

/**
 * The Class GmailController.
 */
@RestController
@RequestMapping("/gmail")
public class GmailController {
    
    /** The gmail service. */
    @Autowired
    private GmailService gmailService;

    @Autowired
    AppUserRepository appUserRepository;
    /**
     * Gets the mail inbox unread.
     *
     * @param userId the user id
     * @return the mail inbox unread
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/unread")
    public NbTotalEmailModel getMailInboxUnread(@RequestParam final String userKey, ModelMap model) throws IOException, GeneralSecurityException {
    	AppUser user = appUserRepository.findByUserkey(userKey);
    	 Map<String, MailModel> map= new HashMap<String, MailModel>();
    	 int nbTotalMailUnread = 0;
    	 for(GoogleAccount googleAccount: user.getGoogleAccounts()){
    		 String accountName = googleAccount.getName();
    		 if(!accountName.isEmpty()){
    			MailModel mailModel = gmailService.getMailInBoxUnread(accountName);
    			map.put(accountName, mailModel);
    			
    			nbTotalMailUnread += mailModel.getUnreadMessages();
    		 }
    	 }
		 return new NbTotalEmailModel(nbTotalMailUnread);
    }
}
