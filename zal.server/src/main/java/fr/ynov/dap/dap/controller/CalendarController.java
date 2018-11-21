package fr.ynov.dap.dap.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.model.CalendarModel;
import fr.ynov.dap.dap.model.MailModel;
import fr.ynov.dap.dap.service.CalendarService;

/**
 * The Class CalendarController.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {

	/** The calendar service. */
	@Autowired
	CalendarService calendarService;
	
	@Autowired
    AppUserRepository appUserRepository;
	
	
	/**
	 * Gets the mail inbox unread.
	 *
	 * @param userId the user id
	 * @return the mail inbox unread
	 * @throws Exception the exception
	 */
	@RequestMapping("/next")
	public CalendarModel getCalendarLastEvents(@RequestParam final String userKey, ModelMap model) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);
		ArrayList<CalendarModel> events = new ArrayList<CalendarModel>();
		Map<String, CalendarModel> map= new HashMap<String, CalendarModel>();
		 for(GoogleAccount googleAccount: user.getGoogleAccounts()){
    		 String accountName = googleAccount.getName();
    		 if(!accountName.isEmpty()){
    			 events.add(calendarService.getCalendarEvents(accountName));
    		 }
		 }
		 CalendarModel temp = null;
		 for(CalendarModel calendar: events){
			 if(temp != null){
				 if(calendar.getDateOfStart().before(temp.getDateOfStart())){
					 temp = calendar;
				 }
			 }else {
				 temp = calendar;
			 }
		 }
		 return temp;
	}

}
