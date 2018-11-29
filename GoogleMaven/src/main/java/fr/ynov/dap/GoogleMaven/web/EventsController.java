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

import fr.ynov.dap.GoogleMaven.OutlookEvents;
import fr.ynov.dap.GoogleMaven.auth.TokenResponse;
import fr.ynov.dap.GoogleMaven.service.Event;
import fr.ynov.dap.GoogleMaven.service.OutlookService;
import fr.ynov.dap.GoogleMaven.service.OutlookServiceBuilder;
import fr.ynov.dap.GoogleMaven.service.PagedResult;

@Controller
public class EventsController {

	@Autowired OutlookEvents outlookEvents;
	@Autowired IndexController indexController;
	
	/**
	 * 
	 * @param user
	 * @return events controller with outlook api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/eventsoutlook")
	public String eventsoutlook(ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException, InstantiationException, IllegalAccessException{
		String responce;
		model.addAttribute("UrlOutlookService",indexController.index(model, request));
		try {
			model.addAttribute("events", outlookEvents.events(model, request));
			responce="Events";
		} catch (Exception e) {
			// TODO: handle exception
			responce="ConnexionOutlook";
		}
		return responce;
	}
	 

}
