package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Utils.LoggerUtils;
import fr.ynov.dap.dap.auth.TokenResponse;
import fr.ynov.dap.dap.controller.google.CalendarController;
import fr.ynov.dap.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftOutlookService;
import fr.ynov.dap.dap.service.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Message;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.PagedResult;


@RequestMapping("/microsoft")
@Controller
public class MailController extends LoggerUtils {
	
	@Autowired
	MicrosoftOutlookService outlookService;

	//TODO brs by Djer |Spring| Si tu n'as pas besoin de la requete ou des redirectAttributtes, ne les mets pas dans la signature de la méthode.
	  //TODO brs by Djer |Audit Code| Tes parmaètres devraient être static
  @RequestMapping("/mail")
  public String mail(@RequestParam(value = "userKey", required = true) String userKey,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Integer nbUnreadEmails = outlookService.getNbMailUnread(userKey);
  //TODO brs by Djer |Log4J| Contextualise tes messages ("for userKey : " + userKey)
	LOG.info("mails : " + nbUnreadEmails);
	if (nbUnreadEmails == null) {
		model.addAttribute("content", "error"); 
		return "index";
		
	}
	
	model.addAttribute("nbUnreadEmail",nbUnreadEmails);

	model.addAttribute("content", "mail"); 
	return "index";
  }
  
  //TODO brs by Djer |Spring| Si tu n'as pas besoin de la requete ou des redirectAttributtes, ne les mets pas dans la signature de la méthode.
  //TODO brs by Djer |Audit Code| Tes parmaètres devraient être static
  @RequestMapping("/mail/{name}")
  public String listMail(Model model,@PathVariable final String name,@RequestParam(value = "userKey", required = true) String userKey, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Message [] messages = outlookService.getListEmail(userKey,name);
    //TODO brs by Djer |Log4J| Contextualise tes messages ("for userKey : " + userKey)
	LOG.info("mails : " + messages);
	if (messages == null) {
		model.addAttribute("content", "error"); 
		return "index";
	}
	
	model.addAttribute("messages", messages);

	model.addAttribute("content", "mail"); 
	return "index";
  }
}
