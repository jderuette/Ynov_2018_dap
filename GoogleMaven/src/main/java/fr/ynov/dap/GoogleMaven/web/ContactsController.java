package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.GoogleMaven.ContactsOutlook;
import fr.ynov.dap.GoogleMaven.auth.TokenResponse;
import fr.ynov.dap.GoogleMaven.service.Contact;
import fr.ynov.dap.GoogleMaven.service.OutlookService;
import fr.ynov.dap.GoogleMaven.service.OutlookServiceBuilder;
import fr.ynov.dap.GoogleMaven.service.PagedResult;

@Controller
public class ContactsController {

	
	/**
	 * 
	 * @param user
	 * @return contacts controller with outlook api 
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
    //TODO elj by Djer |POO| place tes attributs AVANT tes méthodes (ordre : cosntantes, attributs, initialisateurs static, Constructeurs, méthodes métiers, méthdoe Générique (toString, hashCode,...) getter/setter)
	@Autowired ContactsOutlook contactsOutlook;
	@Autowired IndexController indexController;
	@RequestMapping("/contactsoutlook")
	public String contactsoutlook(ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException, InstantiationException, IllegalAccessException{
			String responce;
			model.addAttribute("UrlOutlookService",indexController.index(model, request));
		try {
			model.addAttribute("contacts", contactsOutlook.contacts(model, request));
			responce="ContactsOutlook";
		} catch (Exception e) {
		    //TODO elj by DJer |Log4J| Une petite log ? 
			// TODO: handle exception
			responce="ConnexionOutlook";
		}
		return responce;

	}
}
