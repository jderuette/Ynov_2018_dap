package fr.ynov.dap.web.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;

import fr.ynov.dap.services.google.GCalendarService;
import fr.ynov.dap.services.microsoft.MicrosoftCalendarService;

/**
 * CalendarController of the application that uses the Google and the Microsoft API.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController extends DapController {
    /**
     * Get the google calendar service thanks Spring.
     */
    @Autowired
    private GCalendarService googleCalendarService;

    /**
     * Get the Microsoft calendar thanks to Spring.
     */
    @Autowired
    private MicrosoftCalendarService microsoftCalendarService;

    /**
     * Get the next Event from now.
     * @param userKey user key needed for authentication
     * @return near event from now.
     * It's an object because we can't now if we will get a Google or a Microsoft event.
     * @throws Exception exception
     */
    @RequestMapping(value = "/event/next", method = RequestMethod.GET)
    public Event getNextEvent(@RequestParam("userKey") final String userKey) throws Exception {
        Event googleEvent = googleCalendarService.getNextEventForAllAccount(userKey);
        microsoftCalendarService.setUserKey(userKey);
        fr.ynov.dap.services.microsoft.Event microsoftEvent =
                microsoftCalendarService.getNextEventsOfAllAccount(userKey);

        Event convertedMicrosoftEvent = convertMicrosoftEvent(microsoftEvent);

        if (googleEvent == null && convertedMicrosoftEvent == null) {
            return null;
        }

        if (googleEvent == null && convertedMicrosoftEvent != null) {
            return convertedMicrosoftEvent;
        }

        if (convertedMicrosoftEvent == null && googleEvent != null) {
            return googleEvent;
        }

        if (googleEvent.getStart().getDateTime().getValue()
                < convertedMicrosoftEvent.getStart().getDateTime().getValue()) {
            return googleEvent;
        } else {
            return convertedMicrosoftEvent;
        }
    }

    /**
     * Convert a Microsoft event to a Google event. That way, the API with the client will not be broken.
     * @param microsoftEvent the Microsoft Event to convert.
     * @return the converted Microsoft Event to Google Event.
     */
    private Event convertMicrosoftEvent(final fr.ynov.dap.services.microsoft.Event microsoftEvent) {
        if (microsoftEvent == null) {
            return null;
        }

        Event convertedEvent = new Event();
        convertedEvent.setSummary(microsoftEvent.getSubject());
        long microsoftStartTime = microsoftEvent.getStart().getDateTime().getTime();
        convertedEvent.setStart(new EventDateTime().setDateTime(new DateTime(microsoftStartTime)));
        long microsoftEndTime = microsoftEvent.getEnd().getDateTime().getTime();
        convertedEvent.setEnd(new EventDateTime().setDateTime(new DateTime(microsoftEndTime)));

        //EventAttendee[] attendees = new EventAttendee[1];
        List<EventAttendee> attendees = new ArrayList<EventAttendee>();
        //attendees[0].setOrganizer(microsoftEvent.getIsOrganizer());
        attendees.add(new EventAttendee().setOrganizer(microsoftEvent.getIsOrganizer()));
        convertedEvent.setAttendees(attendees);

        return convertedEvent;
    }
}
