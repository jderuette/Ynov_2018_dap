package fr.ynov.dap.controllers.microsoft;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.data.Event;
import fr.ynov.dap.services.microsoft.EventService;
import fr.ynov.dap.utils.ExtendsUtils;

/**
 * The Class MicrosoftEventsController.
 */
@RequestMapping("/microsoft")
@Controller
public class MicrosoftEventsController extends ExtendsUtils {

	/** The event service. */
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	EventService eventService;

	/**
	 * Microsoft events.
	 *
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	//TODO thb by Djer |Spring| Si tu n'as pas besoin de request et redirectAttributes, ne les met pas dans la signature de ta méthode
	@RequestMapping("/events")
	public String microsoftEvents(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		Event events = eventService.getNextEvent(userKey);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		//TODO thb by Djer |Thymleaf| Ne formate pas tes messsages dans le controller, passe une isntance d'objet ("events") à la vue et fait le formatage dans la vue
		model.addAttribute("userKey", "?userKey=" + userKey);
		model.addAttribute("start", dateFormat.format(events.getStart().getDateTime()));
		model.addAttribute("subject", events.getSubject());
		model.addAttribute("organizer", "organised by " + events.getOrganizer().getEmailAddress().getName() + " - "
				+ events.getOrganizer().getEmailAddress().getAddress());

		return "events";
	}
}
