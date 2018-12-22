package fr.ynov.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.GmailService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.models.NbMailResponse;

/**
 * The Class MailRestController.
 */
@RestController
public class MailRestController {


	/** The app user repository. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The outlook service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	OutlookService outlookService;
	
	/** The gmail service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired 
	GmailService gmailService;
	

	
	/**
	 * get number of microsoft unread mail.
	 *
	 * @param model the model
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the nb mail response
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/microsoft/nbUnreadMails")
	public NbMailResponse getNbMicrosoftUnreadMail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws IOException {
		NbMailResponse response = new NbMailResponse(0);

        AppUser currentUser = appUserRepository.findByUserkey(userKey);
        if(currentUser != null) {
        	Integer nbUnreadMails = outlookService.getNbUnreadEmailsForAccount(currentUser);
        	response.setNbUnreadMail(nbUnreadMails);
        }
		return response;
	}
	
	/**
	 * Gets the number of unread message.
	 *
	 * @param userKey the user key
	 * @return the number of unread message
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/mail/nbUnread")
    public final NbMailResponse getNumberOfUnreadMail(@RequestParam final String userKey) throws IOException, Exception{

		NbMailResponse response = new NbMailResponse(0);

		AppUser currentUser = appUserRepository.findByUserkey(userKey);
		if(currentUser != null) {
	        Integer googleNbUnreadMail = gmailService.getNbUnreadMailForAccount(currentUser);
	
	        Integer microsoftNbUnreadMail = outlookService.getNbUnreadEmailsForAccount(currentUser);
	        response.setNbUnreadMail(googleNbUnreadMail + microsoftNbUnreadMail);
		}
		//TODO brc by Djer |log4J| (Else) Une petite log ?

        return response;
    }
	
	/**
	 * Gets the number of google unread mail.
	 *
	 * @param model the model
	 * @param userKey the user key
	 * @return the gmail response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/nbUnreadMails")
	public NbMailResponse GetGoogleUnreadMail (ModelMap model, @RequestParam final String userKey) throws IOException, Exception {
		
		NbMailResponse response = new NbMailResponse(0);
		AppUser currentUser = appUserRepository.findByUserkey(userKey);
		if(currentUser != null) {
			Integer nbUnreadMail = gmailService.getNbUnreadMailForAccount(currentUser);
			response.setNbUnreadMail(nbUnreadMail);
		}
		//TODO brc by Djer |log4J| (Else) Une petite log ?
		return response;
	}
}