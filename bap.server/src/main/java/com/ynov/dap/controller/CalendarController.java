package com.ynov.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.model.CalendarModel;
import com.ynov.dap.service.google.GoogleCalendarService;
import com.ynov.dap.service.microsoft.MicrosoftCalendarService;

@RestController
@RequestMapping("calendar")
public class CalendarController extends BaseController {

	@Autowired
	private GoogleCalendarService googleCalendarService;

	@Autowired
	private MicrosoftCalendarService microsoftCalendarService;

	@GetMapping(value = "/google/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarModel> getGoogleNextEvent(@PathVariable final String appUser) {

		try {
			System.out.println(googleCalendarService.getNextEvent(appUser));

			return new ResponseEntity<CalendarModel>(googleCalendarService.getNextEvent(appUser), HttpStatus.ACCEPTED);
		} catch (Exception e) {
			getLogger().error(e.getMessage());
		}

		return new ResponseEntity<CalendarModel>(new CalendarModel("", null, null, ""), HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/microsoft/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarModel> getMicrosoftNextEvent(@PathVariable final String appUser) {

		microsoftCalendarService.getNextEvent(appUser);

		return new ResponseEntity<CalendarModel>(microsoftCalendarService.getNextEvent(appUser),
				HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CalendarModel> getNextEvent(@PathVariable final String appUser) throws Exception {

		CalendarModel googleCalendar = googleCalendarService.getNextEvent(appUser);
		CalendarModel microsoftCalendar = microsoftCalendarService.getNextEvent(appUser);

		CalendarModel finalCalendar = null;

		if (googleCalendar != null && microsoftCalendar != null) {
			if (googleCalendar.getStartDate().before(microsoftCalendar.getStartDate())) {
				finalCalendar = new CalendarModel(microsoftCalendar.getSubject(), microsoftCalendar.getStartDate(),
						microsoftCalendar.getEndDate(), "UNKOWN");
			} else {
				finalCalendar = new CalendarModel(googleCalendar.getSubject(), googleCalendar.getStartDate(),
						googleCalendar.getEndDate(), googleCalendar.getState());
			}
		}

		return new ResponseEntity<CalendarModel>(finalCalendar, HttpStatus.BAD_REQUEST);
	}

	@Override
	public String getClassName() {
		return CalendarController.class.getName();
	}
}
