package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.service.GoogleCalendarService;
@Controller
/**
 * Accède aux url relatives aux events de google (soit les prochains, soit LE prochain).
 * @author abaracas
 *
 */
public class GoogleCalendarController {
    @Autowired GoogleCalendarService calendarService;
    
    @RequestMapping("/calendar/nextEvents/{accountName}")
    /**
     * Récupère les 20 prochains évènements
     * @param accountName le nom de compte google lié 
     * @return la liste des évènements
     * @throws IOException exception
     * @throws GeneralSecurityException excepion
     */
    public String getNextEvents(Model model, @PathVariable String accountName) throws IOException, GeneralSecurityException {
	String message = calendarService.getNextEvents(accountName, 20);
	model.addAttribute("message", message);
	return "eventsGoogle";
    }
    
    @RequestMapping("/calendar/nextEvent/{accountName}")
    /**
     * Récupère le prochain événement à venir.
     * @param accountName le nom du compte google.
     * @return le prochain événement
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public String getNextEvent(Model model, @PathVariable String accountName) throws IOException, GeneralSecurityException {
	String message = calendarService.getNextEvents(accountName, 20);
	model.addAttribute("message", message);
	return "eventsGoogle";
    }
}
