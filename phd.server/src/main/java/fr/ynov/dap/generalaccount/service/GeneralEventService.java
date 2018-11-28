package fr.ynov.dap.generalaccount.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.generalaccount.model.GeneralEvent;
import fr.ynov.dap.microsoft.service.MicrosoftEventService;
import fr.ynov.dap.service.CalendarService;

/**
 *
 * @author Dom
 *
 */
@Service
public class GeneralEventService {

    /**
     *
     */
    @Autowired
    private MicrosoftEventService microsoftEventService;

    /**
     *
     */
    @Autowired
    private CalendarService calendarService;

    /**
     *
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public GeneralEvent getGeneralEvent(final String userId) throws IOException, GeneralSecurityException {
        fr.ynov.dap.microsoft.data.Event eventMicrosoft = microsoftEventService.getNextEventForAllAccount(userId);
        Event eventCalendar = calendarService.getNextEventForAll(userId);
        GeneralEvent generalEvent = new GeneralEvent();
        if (eventMicrosoft != null && eventCalendar != null) {
            if (eventMicrosoft.getStart().getDateTime().getTime() < eventCalendar.getStart().getDateTime().getValue()) {
                generalEvent.setSubject(eventMicrosoft.getSubject());
                generalEvent.setOrganisator(eventMicrosoft.getOrganizer().getEmailAddress().getAddress());
                generalEvent.setEventStart(eventMicrosoft.getStart().toString());
                generalEvent.setEventEnd(eventMicrosoft.getEnd().getTimeZone().toString());
            } else {
                generalEvent.setSubject(eventCalendar.getSummary());
                generalEvent.setOrganisator(eventCalendar.getOrganizer().getEmail());
                generalEvent.setEventStart(eventCalendar.getStart().toString());
                generalEvent.setEventEnd(eventCalendar.getEnd().toString());
            }
        }

        if (eventCalendar == null && eventMicrosoft != null) {
            generalEvent.setSubject(eventMicrosoft.getSubject());
            generalEvent.setOrganisator(eventMicrosoft.getOrganizer().getEmailAddress().getAddress());
            generalEvent.setEventStart(eventMicrosoft.getStart().getDateTime().toString());
            generalEvent.setEventEnd(eventMicrosoft.getEnd().getDateTime().toString());
        }

        if (eventMicrosoft == null && eventCalendar != null) {
            generalEvent.setSubject(eventCalendar.getSummary());
            generalEvent.setOrganisator(eventCalendar.getOrganizer().getEmail());
            generalEvent.setEventStart(eventCalendar.getStart().getDateTime().toString());
            generalEvent.setEventEnd(eventCalendar.getEnd().getDateTime().toString());
        }

        return generalEvent;
    }

}
