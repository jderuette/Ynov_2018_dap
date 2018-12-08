package fr.ynov.dap.dap.controller.microsoft;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Utils.LoggerUtils;
import fr.ynov.dap.dap.controller.google.CalendarController;
import fr.ynov.dap.dap.service.microsoft.MicrosoftContactService;


@Controller
public class ContactMicrosoftController extends LoggerUtils {
	
	@Autowired
	MicrosoftContactService contactService;

  //TODO brs by Djer |Spring| Si tu n'as pas besoin de la requetes ou des redirectAttributtes, ne les mets pas dans la signature de la méthode.
  //TODO brs by Djer |Audit Code| Tes attributs devraient être static
  @RequestMapping("/contact")
  public String contact(@RequestParam(value = "userKey", required = true) String userKey,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Integer nbContact = contactService.getNbTotalContact(userKey);
  //TODO brs by Djer |Log4J| Contextualise tes messages ("for userKey : " + userKey)
	LOG.info("nbContact : " + nbContact);
	if (nbContact == null) {
	    //TODO brs by Djer |API Microsoft| En quoi est-ce une "erreur" de n'avoir aucun contact sur aucun de ces comtpes Micrsooft ? (si je ne souhaite utiliser QUE les calendrier ?)
		return "error";
	}
	
	model.addAttribute("nbContact",nbContact);
	model.addAttribute("content","contact");

    return "contact";
  }

}
