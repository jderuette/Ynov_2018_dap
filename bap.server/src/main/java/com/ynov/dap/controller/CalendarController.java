package com.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

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

/**
 * The Class CalendarController.
 */
@RestController
@RequestMapping("calendar")
public class CalendarController extends BaseController {

    /** The google calendar service. */
    @Autowired
    private GoogleCalendarService googleCalendarService;

    /** The microsoft calendar service. */
    @Autowired
    private MicrosoftCalendarService microsoftCalendarService;

    /**
     * Gets the google next event.
     *
     * @param appUser the app user
     * @return the google next event
     * @throws GeneralSecurityException the general security exception
     * @throws IOException              Signals that an I/O exception has occurred.
     */
    @GetMapping(value = "/google/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarModel> getGoogleNextEvent(@PathVariable final String appUser)
            throws GeneralSecurityException, IOException {

        return new ResponseEntity<CalendarModel>(googleCalendarService.getNextEvent(appUser), HttpStatus.ACCEPTED);
    }

    /**
     * Gets the microsoft next event.
     *
     * @param appUser the app user
     * @return the microsoft next event
     * @throws Exception the exception
     */
    @GetMapping(value = "/microsoft/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarModel> getMicrosoftNextEvent(@PathVariable final String appUser) throws Exception {

        //TODO bap by Djer |POO| Pourquoi appeler une fois le service ici sans récupérer la réponse ?
        microsoftCalendarService.getNextEvent(appUser);

        return new ResponseEntity<CalendarModel>(microsoftCalendarService.getNextEvent(appUser),
                //TODO bap by Djer |Spring| Pourquoi un "BAD_REQUEST" ?
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets the next event.
     *
     * @param appUser the app user
     * @return the next event
     * @throws Exception the exception
     */
    @GetMapping(value = "/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarModel> getNextEvent(@PathVariable final String appUser) throws Exception {

        CalendarModel googleCalendar = googleCalendarService.getNextEvent(appUser);
        CalendarModel microsoftCalendar = microsoftCalendarService.getNextEvent(appUser);

        CalendarModel finalCalendar = new CalendarModel();

        if (googleCalendar.getStartDate() != null && microsoftCalendar.getStartDate() == null) {
            finalCalendar = new CalendarModel(googleCalendar.getSubject(), googleCalendar.getStartDate(),
                    googleCalendar.getEndDate(), googleCalendar.getState());
          //TODO bap by Djer |POO| Evite les multiples return dans une même méthode
            return new ResponseEntity<CalendarModel>(finalCalendar, HttpStatus.ACCEPTED);
        } else if (googleCalendar.getStartDate() == null && microsoftCalendar.getStartDate() != null) {
            //TODO bap by Djer |POO| googleCalendar et microsoftCalendar sont déja des CalendarModel, pourquoi les re-créer un nouveau CalendarModel ?
            finalCalendar = new CalendarModel(microsoftCalendar.getSubject(), microsoftCalendar.getStartDate(),
                    microsoftCalendar.getEndDate(), "UNKOWN");
          //TODO bap by Djer |POO| Evite les multiples return dans une même méthode
            return new ResponseEntity<CalendarModel>(finalCalendar, HttpStatus.ACCEPTED);
        }

        if (googleCalendar.getStartDate() != null && microsoftCalendar.getStartDate() != null) {
            //FIXME bap by Djer |POO| Si "google" AVANT "microsoft" => finalEvent = Microsoft ? (tu as inversé je pense)
            if (googleCalendar.getStartDate().before(microsoftCalendar.getStartDate())) {
                finalCalendar = new CalendarModel(microsoftCalendar.getSubject(), microsoftCalendar.getStartDate(),
                        microsoftCalendar.getEndDate(), "UNKOWN");
            } else {
                finalCalendar = new CalendarModel(googleCalendar.getSubject(), googleCalendar.getStartDate(),
                        googleCalendar.getEndDate(), googleCalendar.getState());
            }
        }

        return new ResponseEntity<CalendarModel>(finalCalendar, HttpStatus.ACCEPTED);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ynov.dap.controller.BaseController#getClassName()
     */
    @Override
    public String getClassName() {
        return CalendarController.class.getName();
    }
}
