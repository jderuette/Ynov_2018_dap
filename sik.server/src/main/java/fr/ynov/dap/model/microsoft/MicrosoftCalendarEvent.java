package fr.ynov.dap.model.microsoft;

import java.util.Date;

import fr.ynov.dap.contract.ApiEvent;
import fr.ynov.dap.microsoft.model.OutlookAttendee;
import fr.ynov.dap.microsoft.model.OutlookEvent;
import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;
import fr.ynov.dap.utils.StrUtils;

public class MicrosoftCalendarEvent implements ApiEvent {

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
    private AttendeeEventStatusEnum currentUserStatus;

    public MicrosoftCalendarEvent(final OutlookEvent event, final String email) {
        this.event = event;
        this.userMail = email;
    }

    public OutlookEvent getEvent() {
        return event;
    }

    @Override
    public Date getStartDate() {
        return event.getStart().getDateTime();
    }

    @Override
    public Date getEndDate() {
        return event.getEnd().getDateTime();
    }

    @Override
    public String getSubject() {
        return event.getSubject();
    }

    @Override
    public AttendeeEventStatusEnum getCurrentUserStatus() {
        if (StrUtils.isNullOrEmpty(userMail)) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }
        return getStatusForAttendee(userMail);
    }

    @Override
    public final AttendeeEventStatusEnum getStatusForAttendee(final String mail) {

        if (getEvent() == null) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        if (StrUtils.isNullOrEmpty(mail) || getEvent().getAttendees().isEmpty()) {
            return AttendeeEventStatusEnum.UNKNOWN;
        }

        if (getEvent().getOrganizer() != null && getEvent().getOrganizer().getEmailAddress() != null) {
            String outlookMail = getEvent().getOrganizer().getEmailAddress().getAddress();
            if (outlookMail != null) {
                if (outlookMail.equals(mail)) {
                    return AttendeeEventStatusEnum.OWNER;
                }
            }
        }

        OutlookAttendee attendee = null;

        for (OutlookAttendee currAttendee : getEvent().getAttendees()) {
            if (currAttendee.getEmailAddress() != null && currAttendee.getEmailAddress().getAddress() != null
                    && currAttendee.getEmailAddress().getAddress().equals(mail)) {
                attendee = currAttendee;
                break;
            }
        }

        if (attendee != null && attendee.getStatus() != null) {
            return attendee.getStatus().getEventStatus();
        }

        return AttendeeEventStatusEnum.UNKNOWN;

    }

}
