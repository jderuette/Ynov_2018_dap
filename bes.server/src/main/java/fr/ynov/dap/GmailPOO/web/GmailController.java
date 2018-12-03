package fr.ynov.dap.GmailPOO.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.ynov.dap.GmailPOO.metier.GMailService;
@RestController
public class GmailController {
	@Autowired
	GMailService gmailservice;
	
	@RequestMapping("/list/google/email/{userkey}")
	public  List<Message> getListEmails( @PathVariable("userkey") String userkey) throws IOException, GeneralSecurityException {
        
         System.out.println("veuillez patienter traitement en cours ...."+userkey);
 
		return gmailservice.getListEmails(userkey);

	}
}
