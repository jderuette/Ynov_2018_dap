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

  @RequestMapping("/mail")
  public String mail(@RequestParam(value = "userKey", required = true) String userKey,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Integer nbUnreadEmails = outlookService.getNbMailUnread(userKey);
	LOG.info("mails : " + nbUnreadEmails);
	if (nbUnreadEmails == null) {
		model.addAttribute("content", "error"); 
		return "index";
		
	}
	
	model.addAttribute("nbUnreadEmail",nbUnreadEmails);

	model.addAttribute("content", "mail"); 
	return "index";
  }
  
  @RequestMapping("/mail/{name}")
  public String listMail(Model model,@PathVariable final String name,@RequestParam(value = "userKey", required = true) String userKey, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Message [] messages = outlookService.getListEmail(userKey,name);
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
