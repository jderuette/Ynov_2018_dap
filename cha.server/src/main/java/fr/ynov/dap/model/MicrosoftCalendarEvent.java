package fr.ynov.dap.model;

import java.util.ArrayList;
import java.util.Date;

import fr.ynov.dap.data.Guest;
import fr.ynov.dap.model.enumeration.GuestStatusEventEnum;
import fr.ynov.dap.model.outlook.OutlookEvent;
import fr.ynov.dap.model.outlook.OutlookGuest;

public class MicrosoftCalendarEvent implements EventAllApi{
	 /**
     * Event from Microsoft Calendar API.
     */
    private OutlookEvent event;

    /**
     * Microsoft account email.
     */
    private String userMail;

    /**
     * Current user status.
     */
    private GuestStatusEventEnum currentUserStatus;

    /**
     * Default constructor.
     * @param evt Outlook event
     * @param mail User email
     */
    public MicrosoftCalendarEvent(final OutlookEvent evt, final String mail) {
        this.event = evt;
        this.userMail = mail;
    }

    /**
     * Get Outlook event.
     * @return Event
     */
    public OutlookEvent getEvent() {
        return event;
    }

    /**
     * Return starting date.
     */
    @Override
    public Date getStart() {
        return event.getStart().getDateTime();
    }

    /**
     * Return ending date.
     */
    @Override
    public Date getEnd() {
        return event.getEnd().getDateTime();
    }

    /**
     * Return event subject.
     */
    @Override
    public String getSubject() {
        return event.getSubject();
    }

    /**
     * Return current user status for linked event.
     */
    @Override
    public GuestStatusEventEnum getCurrentUserStatus() {
        if (userMail.isEmpty() && userMail == null) {
            return GuestStatusEventEnum.UNKNOWN;
        }
        return getStatusForGuest(userMail);
    }

    @Override
    public final GuestStatusEventEnum getStatusForGuest(final String mail) {

        if (getEvent() == null) {
            return GuestStatusEventEnum.UNKNOWN;
        }

        if (mail.isEmpty() || getEvent().getGuests().isEmpty()) {
            return GuestStatusEventEnum.UNKNOWN;
        }

        if (getEvent().getOrganizer() != null && getEvent().getOrganizer().getEmailAddress() != null) {
            String outlookMail = getEvent().getOrganizer().getEmailAddress().getAddress();
            if (outlookMail != null) {
                if (outlookMail.equals(mail)) {
                    return GuestStatusEventEnum.OWNER;
                }
            }
        }

        OutlookGuest attendee = null;

        for (OutlookGuest currAttendee : getEvent().getGuests()) {
            if (currAttendee.getEmailAddress() != null && currAttendee.getEmailAddress().getAddress() != null
                    && currAttendee.getEmailAddress().getAddress().equals(mail)) {
                attendee = currAttendee;
                break;
            }
        }

        if (attendee != null && attendee.getStatus() != null) {
            return attendee.getStatus().getEventStatus();
        }

        return GuestStatusEventEnum.UNKNOWN;

    }

    @Override
    public final ArrayList<Guest> getGuest() {
        ArrayList<Guest> res = new ArrayList<Guest>();
        if (getEvent() != null && getEvent().getGuests() != null) {
            for (OutlookGuest att : getEvent().getGuests()) {
            	Guest nAtt = new Guest();
                nAtt.setMail(att.getEmailAddress().getAddress());
                nAtt.setStatus(getStatusForGuest(att.getEmailAddress().getAddress()));
                res.add(nAtt);
            }
        }
        return res;
}
}
