package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.GoogleMaven.CalendarService;


@RestController
public class CalendarController {

	/**
	 * 
	 * @param user
	 * @return calendar controller with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@Autowired CalendarService calendarService;
	
	@RequestMapping("/NextEvents/{userKey}")
	public String GetNextEvents (@PathVariable final String userkey,ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException{
		
		model.addAttribute("GoogleEvent", calendarService.getNextEvent(request, userkey));
		return "GoogleEvent" ;
	}
	
}
