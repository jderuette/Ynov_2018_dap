package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.service.GooglePeopleService;
@Controller
/**
 * Gere les url relatives aux contacts google.
 * @author abaracas
 *
 */
public class GooglePeopleController {
    @Autowired GooglePeopleService peopleService;
    @RequestMapping("/contacts/{gUser}")
    /**
     * Récupère les contacts, leur nombre, nom, tel
     * @param user GoogleUser
     * @return les valeurs voulus
     * @throws IOException exception
     */
    public String getContacts(Model model, @RequestParam("userKey") String userKey, @PathVariable final String gUser) throws IOException, GeneralSecurityException {
	String message = peopleService.getContacts(userKey, gUser);
	model.addAttribute(message);
	return "contactsGoogle";
    }
}
