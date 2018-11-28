package fr.ynov.dap.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/calendar")
public class GoogleCalendarController {

	/*
	 * public MasterModel getNextEvent(@RequestParam final String userKey) throws
	 * Exception {
	 * 
	 * List<EventModel> events = new ArrayList<>();
	 * 
	 * if(null != appUserRepository.findByUserKey(userKey)) { for
	 * (GoogleAccountModel googleAccount :
	 * appUserRepository.findByUserKey(userKey).getGoogleAccounts()) { try {
	 * events.add((EventModel)
	 * calendarService.getUpcomingEvent(googleAccount.getAccountName())); }catch
	 * (ClassCastException clCastException) {
	 * 
	 * } } } MasterModel nextEvent = new EmptyData("No Event found");
	 * if(events.size() > 0) { nextEvent = events.get(0); for (EventModel eventModel
	 * : events) { if(((EventModel)
	 * nextEvent).getStartDate().compareTo(eventModel.getStartDate()) >= 0){
	 * nextEvent = eventModel; } } }
	 * 
	 * return nextEvent; }
	 */
}
