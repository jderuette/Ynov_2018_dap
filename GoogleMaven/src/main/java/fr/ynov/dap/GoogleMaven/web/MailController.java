package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.ynov.dap.GoogleMaven.Config;
import fr.ynov.dap.GoogleMaven.GMailService;
import fr.ynov.dap.GoogleMaven.GoogleService;
import fr.ynov.dap.GoogleMaven.data.AppUser;

@Controller
public class MailController extends GoogleService {

	@Autowired GMailService gMailService;

	/**
	 * 
	 * @param user
	 * @return mail controller with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/unreadmailgoogle/{userkey}")
	public String mailGoogle(@PathVariable final String userkey,ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException, InstantiationException, IllegalAccessException{
		if(gMailService.getNbUnreadEmails(userkey).equals("-1"))
		  //TODO elj by Djer |Log4J| une petite Log ? 
			model.addAttribute("unread","L'utilisateur "+ userkey+"  est inexistant");	
		else {
			model.addAttribute("unread", "Vous avez "+ gMailService.getNbUnreadEmails(userkey)+" mails non lus");
			
		}
			
		return "GmailListe";
	}
	


}

