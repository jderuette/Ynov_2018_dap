package fr.ynov.dap.controller.microsoft;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.microsoft.MicrosoftCalendarService;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftEventsController {

	@Autowired
	MicrosoftCalendarService microsoftEventService;

	@Autowired
	AppUserRepository appUserRepository;

	@RequestMapping("/events/next")
	public EventModel nextEvent(@RequestParam final String userKey, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		List<EventModel> events = new ArrayList<>();
		EventModel nextEvent = null;

		AppUserModel appUser = appUserRepository.findByUserKey(userKey);
		EventModel response = null;
		if (appUser != null) {
			for (OutlookAccountModel accountModel : appUser.getMicrosoftAccounts()) {
				events.add(microsoftEventService.getNextEvent(accountModel));
			}

			if (events.size() > 0) {
				nextEvent = events.get(0);
				for (EventModel eventModel : events) {
					if (((EventModel) nextEvent).getStartDate().compareTo(eventModel.getStartDate()) >= 0) {
						nextEvent = eventModel;
					}
				}
			}
			response = nextEvent;
		} 
		return response;
	}
}
