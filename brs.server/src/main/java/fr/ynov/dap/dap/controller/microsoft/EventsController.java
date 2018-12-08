package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.auth.TokenResponse;
import fr.ynov.dap.dap.service.microsoft.MicrosoftEventService;
import fr.ynov.dap.dap.service.microsoft.IOutlookService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftOutlookService;
import fr.ynov.dap.dap.service.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Event;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.PagedResult;


@RequestMapping("/microsoft")
@Controller
public class EventsController {
	
	@Autowired
	MicrosoftEventService eventService;

	//TODO brs by Djer |Spring| Si tu n'as pas besoin de la requete ou des redirectAttributtes, ne les mets pas dans la signature de la méthode.
	  //TODO brs by Djer |Audit Code| Tes paramètres devraient être static
  @RequestMapping("/events")
  public String events(@RequestParam(value = "userKey", required = true) String userKey,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    
    Event events = eventService.getNextEvent(userKey);
    if(events == null) {
      //TODO brs by Djer |API Microsoft| En quoi est-ce une "erreur" de n'avoir aucun évènnement sur aucun de ces comtpes Micrsooft ? (si je ne souhaite utiliser QUE les mails ?)
    	return "error";
    }
    model.addAttribute("start",events.getStart().getDateTime());
    model.addAttribute("end",events.getEnd().getDateTime());
    model.addAttribute("subject",events.getSubject());
    model.addAttribute("organiser",events.getOrganizer().getEmailAddress().getName());
    
    model.addAttribute("content", "event"); 
	return "index";
   
    
    

    
  }
}