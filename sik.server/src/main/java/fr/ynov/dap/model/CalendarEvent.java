package fr.ynov.dap.model;

import java.util.Date;
import java.util.Optional;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

/**
 * Class to store an event.
 * Based on 'Proxy' design pattern.
 * @author Kévin Sibué
 *
 */
public class CalendarEvent {

    /**
     * Event from Google Calendar API.
     */
    private Event event;

    /**
     * Default constructor.
     * @param evnt Current Event from Google API
     */
    public CalendarEvent(final Event evnt) {
        this.event = evnt;
    }

    /**
     * @return the subject of the stored Google's event
     */
    public String getSubject() {
        return event.getSummary();
    }

    /**
     * @return the startDate of the stored Google's event
     */
    public Date getStartDate() {
        long val = event.getStart().getDateTime().getValue();
        return new Date(val);
    }

    /**
     * @return the endDate of the stored Google's event
     */
    public Date getEndDate() {
        long val = event.getEnd().getDateTime().getValue();
        return new Date(val);
    }

    /**
     * @return the status of the stored Google's event
     */
    public EventStatusEnum getStatus() {
        switch (this.event.getStatus()) {
        case "confirmed":
            return EventStatusEnum.CONFIRMED;
        case "tentative":
            return EventStatusEnum.TENTATIVE;
        case "cancelled":
            return EventStatusEnum.CANCELLED;
        default:
            return EventStatusEnum.UNKNOW;
        }
    }

    /**
     * Return status for a particular attendee of current event.
     * @param userEmail Current user email
     * @return User's status for the current event
     */
    public AttendeeEventStatusEnum getStatusForAttendee(final String userEmail) {

        //TODO sik by Djer Bonne aproche ! Mais tu as oublié le cas "organisateur".
        if (event == null) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        Optional<EventAttendee> attendee = event.getAttendees().stream().filter(a -> a.getEmail().equals(userEmail))
                .findFirst();

        if (attendee.isPresent()) {
            switch (attendee.get().getResponseStatus()) {
            case "needsAction":
                return AttendeeEventStatusEnum.NEEDS_ACTION;
            case "declined":
                return AttendeeEventStatusEnum.DECLINED;
            case "tentative":
                return AttendeeEventStatusEnum.TENTATIVE;
            case "accepted":
                return AttendeeEventStatusEnum.ACCEPTED;
            default:
                return AttendeeEventStatusEnum.UNKNOWN;
            }
        }

        return AttendeeEventStatusEnum.UNKNOWN;

    }

}
