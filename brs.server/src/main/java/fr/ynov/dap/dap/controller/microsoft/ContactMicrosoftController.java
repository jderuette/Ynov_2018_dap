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

  @RequestMapping("/contact")
  public String contact(@RequestParam(value = "userKey", required = true) String userKey,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Integer nbContact = contactService.getNbTotalContact(userKey);
	LOG.info("nbContact : " + nbContact);
	if (nbContact == null) {
		return "error";
	}
	
	model.addAttribute("nbContact",nbContact);
	model.addAttribute("content","contact");

    return "contact";
  }

}
