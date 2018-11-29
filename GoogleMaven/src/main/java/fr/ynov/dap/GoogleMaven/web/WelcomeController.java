package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.GoogleMaven.ContactService;
import fr.ynov.dap.GoogleMaven.GMailService;


@Controller
public class WelcomeController {
@Autowired MailControllerOutlook mailControllerOutlook;
@Autowired IndexController indexController;
@Autowired AccountManager accountManager;
@Autowired GMailService gmailservice;
@Autowired EventsController eventsController;
@Autowired ContactsController contactsController;




	@RequestMapping("/")
	public String welcome(ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException, InstantiationException, IllegalAccessException{
		

		//if (userKey != null)
		//model.addAttribute("UrlConnection",accountManager.ManageAccount(userKey, request, session));
		//${UrlConnection}
		return "Welcome";
	}





}
