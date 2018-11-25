package com.ynov.dap.google.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.google.business.CalendarService;
import com.ynov.dap.google.models.CalendarModel;

/**
 * Controller for calendar
 * @author POL
 */
@RestController
public class GoogleCalendarController extends BaseController {

    /** The calendar service. */
    @Autowired
    private CalendarService calendarService;

    /**
     * Gets the calendar.
     *
     * @param gUser the g user
     * @return the calendar
     */
    @RequestMapping(value = "/calendar/{gUser}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarModel> getCalendar(@PathVariable final String gUser) {
        if (gUser == null || gUser.length() <= 0) {
            return new ResponseEntity<CalendarModel>(new CalendarModel("", null, null, ""), HttpStatus.BAD_REQUEST);
        }

        try {
        	System.out.println(calendarService.finalReturn(gUser));
        	
            return new ResponseEntity<CalendarModel>(calendarService.finalReturn(gUser), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            getLogger().error(e.getMessage());
        }
        
        return new ResponseEntity<CalendarModel>(new CalendarModel("", null, null, ""), HttpStatus.BAD_REQUEST);
    }

    @Override
    public String getClassName() {
        return GoogleCalendarController.class.getName();
    }
}
