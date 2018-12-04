package fr.ynov.dap.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.Google.GoogleCalendarService;
import fr.ynov.dap.service.microsoft.MicrosoftCalendarService;

@Controller
@RequestMapping("/calendar")
public class GlobalEventController {

	@Autowired
	GoogleCalendarService googleCalendarService;

	@Autowired
	MicrosoftCalendarService microsoftCalendarService;

	@Autowired
	AppUserRepository appUserRepository;

	@RequestMapping("/upcoming")
	public String getNextEvent(@RequestParam final String userKey, Model model) throws Exception {

		AppUserModel appUser = appUserRepository.findByUserKey(userKey);

		if (appUser != null) {
		    //TODO bof by Djer |IDE| Ton IDE t'indique que ce n'est oas utilisé, Bug ? A supprimer ? 
			int sumOfContacts = 0;

			List<EventModel> events = new ArrayList<>();
			EventModel nextEvent = null;

			 //TODO bof by Djer |IDE| Ton IDE t'indique que ce n'est oas utilisé, Bug ? A supprimer ? 
			EventModel response = null;
			for (OutlookAccountModel accountModel : appUser.getMicrosoftAccounts()) {
				events.add(microsoftCalendarService.getNextEvent(accountModel));
			}

			for (GoogleAccountModel googleAccount : appUser.getGoogleAccounts()) {
				events.add(googleCalendarService.getUpcomingEvent(googleAccount.getAccountName()));
			}

			if (events.size() > 0) {
				if(events.get(0).getStartDate().compareTo(new Date()) >= 0) {
					nextEvent = events.get(0);
				}
				for (EventModel eventModel : events) {
					if(nextEvent == null) {
						if(eventModel.getStartDate().compareTo(new Date()) >= 0) {
							nextEvent = eventModel;
						}
					}else {
						if(eventModel.getStartDate().compareTo(nextEvent.getStartDate()) >= 0) {
							nextEvent = eventModel;
						}
					}
				}
			}

			model.addAttribute("nextEvent", nextEvent);
			//TODO bof by Djer |POO| Evite les multiples return dans une même méthode
			return "GlobalNextEvent";
		} else {
			model.addAttribute("errorMessage", "No user found for given useKey");
			return "Error";
		}
	}
}
