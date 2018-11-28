package fr.ynov.dap.dap.controllers.microsoft;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.microsoft.EventMicrosoft;
import fr.ynov.dap.dap.services.microsoft.EventMicrosoftService;

/**
 * The Class EventMicrosoftController.
 */
@Controller
public class EventMicrosoftController {

	@Autowired
	EventMicrosoftService eventMicrosoftService;

	protected Logger LOG = LogManager.getLogger(EventMicrosoftController.class);

	/**
	 * Events.
	 *
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return Thymeleaf template event with nextEvent
	 */
	@RequestMapping("/microsoftNextEvent")
	public String microsoftNextEvent(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		if (eventMicrosoftService.getNextEvents(userKey) != null) {
			EventMicrosoft event = eventMicrosoftService.getNextEvents(userKey);
			model.addAttribute("subject", event.getSubject());
			model.addAttribute("organizer", event.getOrganizer().getEmailAddress().getName());
			model.addAttribute("start", event.getStart().getDateTime());
			model.addAttribute("end", event.getEnd().getDateTime());

		} else {
			model.addAttribute("noEvent", "No events found");
		}

		return "event";
	}
}
