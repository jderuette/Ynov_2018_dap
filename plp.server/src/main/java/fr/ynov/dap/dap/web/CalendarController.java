package fr.ynov.dap.dap.web;

import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.dap.CalendarService;
import fr.ynov.dap.dap.microsoft.OutlookService;
import fr.ynov.dap.dap.microsoft.models.EventMicrosoft;
import fr.ynov.dap.dap.web.google.GCalendarController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/calendar")
public class CalendarController {
    /**
     * Instantiate instance of CalendarService.
     */
    @Autowired
    private CalendarService calendarService;
    /**
     * Instantiate instance of OutlookService.
     */
    @Autowired
    private OutlookService outlookService;
    /**
     * Instantiate Logger.
     */
    private static final Logger LOG = LogManager.getLogger(GCalendarController.class);

    /**
     * format response for google event
     * @param google : event calendat
     * @return Map : contante all same fields than formatEventOutlook
     */
    private Map<String, Object> formatEventGoogle(final Event google) {
        Map<String, Object> response = new HashMap<>();

        response.put("organizer", google.getStatus()); //TODO : change value if time
        response.put("subject", google.getSummary());
        if (google.getStart().getDate() == null) {
            response.put("start", google.getStart().getDateTime());
            response.put("finish", google.getEnd().getDateTime());
        } else {
            response.put("start", google.getStart().getDate());
            response.put("finish", google.getEnd().getDate());
        }

        return response;
    }

    /**
     * format response for microsoft event
     * @param outlook : event calendar microsoft
     * @return Map : contante all same fields than formatEventGoogle
     */
    private Map<String, Object> formatEventOutlook(final EventMicrosoft outlook) {
        Map<String, Object> response = new HashMap<>();
        response.put("organizer", outlook.getOrganizer().getEmailAddress().getName());
        response.put("subject", outlook.getSubject());
        response.put("start", outlook.getStart().getDateTime());
        response.put("finish", outlook.getEnd().getDateTime());
        return response;
    }

    /**
     * get the next event for a user when there is a request in /calendar/nextEvent.
     *
     * @param userKey : userkey param
     * @return Map : contains accessRole, subject, start (date or/and time), finish (date or/and time)
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    @RequestMapping("/nextEvent")
    public final Map<String, Object> getNextEvent(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Map<String, Object> response = new HashMap<>();
        Event google = calendarService.getNextEvent(userKey);
        EventMicrosoft outlook = outlookService.events(userKey);

        Boolean googleEmpty = false;
        if (google.isEmpty()) {
            googleEmpty = true;
        }

        Boolean outlookEmpty = false;
        if (outlook == null) {
            outlookEmpty = true;
        }

        if (outlookEmpty || googleEmpty) {
            if (google.isEmpty()) {
                if (outlook == null) {
                    response.put("subject", "no incoming");
                } else {
                    response = formatEventOutlook(outlook);
                }
            } else {
                response = formatEventGoogle(google);
            }
        } else {
            try {
                Date dateGoogle;
                if (google.getStart().getDate() == null) {
                    //TODO plp by Djer |POO| Les "DateTime" de Google ont une compsante "heure" je ne suis pas certains que ton parseur fonctionnera bien
                    dateGoogle = format.parse(google.getStart().getDateTime().toString());
                } else {
                    dateGoogle = format.parse(google.getStart().getDate().toString());
                }

                if (outlook.getStart().getDateTime().before(dateGoogle)) {
                    response = formatEventOutlook(outlook);
                } else {
                    response = formatEventGoogle(google);
                }
            } catch (Exception e) {
                LOG.error("Error to parse date", e);
            }
        }

        return response;
    }
}
