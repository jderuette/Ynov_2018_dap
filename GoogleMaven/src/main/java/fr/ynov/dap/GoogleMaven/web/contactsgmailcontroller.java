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

import fr.ynov.dap.GoogleMaven.ContactService;


@Controller
//TODO elj by Djer |POO| Utilise le CamelCase pour le nom de tes classes ! Configure Pmd/CheckStyle, ils auraient au moins repréré la majuscule manquante au début
public class contactsgmailcontroller {


	@Autowired ContactService contactService;
	/**
	 * 
	 * @param user
	 * @return contacts controller with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/NombreDeContactgmail/{userkey}")
	public String GetContact(@PathVariable final String userkey,ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException {
		model.addAttribute("contactsgmail", "vous avez : "+contactService.GetContact(userkey)+" contacts");
		return "numbercontactsgmail";

	}
}
