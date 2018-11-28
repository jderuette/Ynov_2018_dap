package fr.ynov.dap.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

import fr.ynov.dap.data.Guest;
import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;
import fr.ynov.dap.model.enumeration.StatusEventEnum;

/**
 * The Class GoogleCalendarEvent.
 */
public class GoogleCalendarEvent implements EventAllApi{
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
    private GuestStatusEventEnum currentUserStatus;

    /**
     * Default constructor.
     * @param evnt Current Event from Google API
     * @param userEmail Current user email
     */
    public GoogleCalendarEvent(final Event evnt, final String userEmail) {
        this.event = evnt;
        this.googleUserEmail = userEmail;
        if (googleUserEmail != null) {
            //this.setCurrentUserStatus(getStatusForGuest(googleUserEmail));
        }
    }

    /**
     * Gets the subject.
     *
     * @return the subject of the stored Google's event
     */
    @Override
    public String getSubject() {
        return event.getSummary();
    }

    /**
     * Gets the start.
     *
     * @return the startDate of the stored Google's event
     */
    @Override
    public Date getStart() {
        long val = event.getStart().getDateTime().getValue();
        return new Date(val);
    }

    /**
     * Gets the end.
     *
     * @return the endDate of the stored Google's event
     */
    @Override
    public Date getEnd() {
        long val = event.getEnd().getDateTime().getValue();
        return new Date(val);
    }

    /**
     * Gets the status.
     *
     * @return the status of the stored Google's event
     */
    public StatusEventEnum getStatus() {
        switch (this.event.getStatus()) {
        case "confirmed":
            return StatusEventEnum.CONFIRMED;
        case "tentative":
            return StatusEventEnum.TENTATIVE;
        case "cancelled":
            return StatusEventEnum.CANCELLED;
        default:
            return StatusEventEnum.UNKNOW;
        }
    }

    /**
     * Return status for a particular Guest of current event.
     * @param userMail user email
     * @return User's status for the current event
     */
    @Override
    public GuestStatusEventEnum getStatusForGuest(final String userMail) {

        if (event == null) {
            return GuestStatusEventEnum.UNKNOWN;
        }

        if (event.getCreator() != null) {
            if (event.getCreator().getEmail().equals(userMail)) {
                return GuestStatusEventEnum.OWNER;
            }
        }

        if (event.getAttendees() == null) {
            return GuestStatusEventEnum.UNKNOWN;
        }

        Optional<EventAttendee> attendee = event.getAttendees().stream().filter(a -> a.getEmail().equals(userMail))
        		.findFirst();
        
        if (attendee.isPresent()) {
            switch (attendee.get().getResponseStatus()) {
            case "accepted":
            	return GuestStatusEventEnum.ACCEPTED;
            case "tentative":
            	return GuestStatusEventEnum.TENTATIVE;
            case "declined":
            	return GuestStatusEventEnum.DECLINED;
            case "needsAction":
                return GuestStatusEventEnum.NOT_ANSWERED;
            default:
                return GuestStatusEventEnum.UNKNOWN;
            }
        }

        return GuestStatusEventEnum.UNKNOWN;

    }

    /**
     * Gets the current user status.
     *
     * @return the currentUserStatus
     */
    @Override
    public GuestStatusEventEnum getCurrentUserStatus() {
        return currentUserStatus;
    }

    /**
     * Sets the current user status.
     *
     * @param val the currentUserStatus to set
     */
    public void setCurrentUserStatus(final GuestStatusEventEnum val) {
        this.currentUserStatus = val;
    }

    /**
     * Gets the guest.
     *
     * @return the guest
     */
    @Override
    public final ArrayList<Guest> getGuest() {
        ArrayList<Guest> res = new ArrayList<Guest>();
        if (event != null && event.getAttendees() != null) {
            for (EventAttendee att : event.getAttendees()) {
            	Guest nAtt = new Guest();
                nAtt.setMail(att.getEmail());
                nAtt.setStatus(getStatusForGuest(att.getEmail()));
                res.add(nAtt);
            }
        }
        return res;
}
}
