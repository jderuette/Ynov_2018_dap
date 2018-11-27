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

@RequestMapping("/microsoft")
@Controller
public class MicrosoftEventsController extends ExtendsUtils {

	@Autowired
	EventService eventService;

	@RequestMapping("/events")
	public String microsoftEvents(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		Event events = eventService.getNextEvent(userKey);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		model.addAttribute("userKey", "?userKey=" + userKey);
		model.addAttribute("start", dateFormat.format(events.getStart().getDateTime()));
		model.addAttribute("subject", events.getSubject());
		model.addAttribute("organizer", "organised by " + events.getOrganizer().getEmailAddress().getName() + " - "
				+ events.getOrganizer().getEmailAddress().getAddress());

		return "events";
	}
}
