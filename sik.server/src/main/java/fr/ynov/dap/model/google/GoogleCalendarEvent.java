package fr.ynov.dap.model.google;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

import fr.ynov.dap.contract.ApiEvent;
import fr.ynov.dap.model.Attendee;
import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;
import fr.ynov.dap.model.enumeration.EventStatusEnum;

/**
 * Class to store an event.
 * Based on 'Proxy' design pattern.
 * @author Kévin Sibué
 *
 */
public class GoogleCalendarEvent implements ApiEvent {

    /**
     * Event from Google Calendar API.
     */
    private Event event;

    /**
     * Google account email.
     */
    private String googleUserEmail;

    /**
     * Current user status.
     */
    private AttendeeEventStatusEnum currentUserStatus;

    /**
     * Default constructor.
     * @param evnt Current Event from Google API
     * @param userEmail Current user email
     */
    public GoogleCalendarEvent(final Event evnt, final String userEmail) {
        this.event = evnt;
        this.googleUserEmail = userEmail;
        if (googleUserEmail != null) {
            this.setCurrentUserStatus(getStatusForAttendee(googleUserEmail));
        }
    }

    /**
     * @return the subject of the stored Google's event
     */
    @Override
    public String getSubject() {
        return event.getSummary();
    }

    /**
     * @return the startDate of the stored Google's event
     */
    @Override
    public Date getStartDate() {
        long val = event.getStart().getDateTime().getValue();
        return new Date(val);
    }

    /**
     * @return the endDate of the stored Google's event
     */
    @Override
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
     * @param userMail user email
     * @return User's status for the current event
     */
    @Override
    public AttendeeEventStatusEnum getStatusForAttendee(final String userMail) {

        if (event == null) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        if (event.getCreator() != null) {
            if (event.getCreator().getEmail().equals(userMail)) {
                return AttendeeEventStatusEnum.OWNER;
            }
        }

        if (event.getAttendees() == null) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        Optional<EventAttendee> attendee = event.getAttendees().stream().filter(a -> a.getEmail().equals(userMail))
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

    /**
     * @return the currentUserStatus
     */
    @Override
    public AttendeeEventStatusEnum getCurrentUserStatus() {
        return currentUserStatus;
    }

    /**
     * @param val the currentUserStatus to set
     */
    public void setCurrentUserStatus(final AttendeeEventStatusEnum val) {
        this.currentUserStatus = val;
    }

    @Override
    public final ArrayList<Attendee> getAttendees() {
        ArrayList<Attendee> res = new ArrayList<Attendee>();
        if (event != null && event.getAttendees() != null) {
            for (EventAttendee att : event.getAttendees()) {
                Attendee nAtt = new Attendee();
                nAtt.setMail(att.getEmail());
                nAtt.setStatus(getStatusForAttendee(att.getEmail()));
                res.add(nAtt);
            }
        }
        return res;
    }

}
