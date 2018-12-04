package fr.ynov.dap.global.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.microsoft.service.MicrosoftEventService;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.service.CalendarService;

/**
 * @author Mon_PC
 */
@Service
public class EventGlobalService {

    /**.
     * microsoftEventService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftEventService microsoftEventService;

    /**.
     * eventService is managed by Spring on the loadConfig()
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * @param userKey user passé en param
     * @return globalMailUnread of microsoft and google
     * @throws Exception exeception si problème rencontré
     */
    public EventModel getNextEventMicrosoftOrGoogle(final String userKey) throws Exception {

        fr.ynov.dap.microsoft.data.Event nextEventMicrosoft = microsoftEventService
                .getNextEventForAllAccountMicrosoft(userKey);
        Event nextEventGoogle = calendarService.getNextEvent(userKey);

        EventModel nextEvent = new EventModel();

        if (nextEventGoogle == null && nextEventMicrosoft != null) {
            //TODO bog by Djer |POO| Une méthode qui initialise le "EventModel" à partir d'un "Microsoft Event" ou d'un "Google Event" aurait été pas mal
            nextEvent.setOrganisateur(nextEventMicrosoft.getOrganizer().getEmailAddress().getAddress());
            nextEvent.setSujet(nextEventMicrosoft.getSubject());
            nextEvent.setDateDebut(nextEventMicrosoft.getStart().getDateTime().toString());
            nextEvent.setDateFin(nextEventMicrosoft.getEnd().getDateTime().toString());

        } else if (nextEventGoogle != null && nextEventMicrosoft == null) {

            nextEvent.setOrganisateur(nextEventGoogle.getCreator().getEmail());
            nextEvent.setSujet(nextEventGoogle.getSummary());
            nextEvent.setDateDebut(nextEventGoogle.getStart().getDateTime().toString());
            nextEvent.setDateFin(nextEventGoogle.getEnd().getDateTime().toString());

        } else if (nextEventGoogle != null && nextEventMicrosoft != null) {
            if (nextEventMicrosoft.getStart().getDateTime().getTime() < nextEventGoogle.getStart().getDateTime()
                    .getValue()) {
                nextEvent.setOrganisateur(nextEventMicrosoft.getOrganizer().getEmailAddress().getAddress());
                nextEvent.setSujet(nextEventMicrosoft.getSubject());
                nextEvent.setDateDebut(nextEventMicrosoft.getStart().getDateTime().toString());
                nextEvent.setDateFin(nextEventMicrosoft.getEnd().getDateTime().toString());
            } else {
                nextEvent.setOrganisateur(nextEventGoogle.getCreator().getEmail());
                nextEvent.setSujet(nextEventGoogle.getSummary());
                nextEvent.setDateDebut(nextEventGoogle.getStart().getDateTime().toString());
                nextEvent.setDateFin(nextEventGoogle.getEnd().getDateTime().toString());
            }
        }

        return nextEvent;
    }
}
