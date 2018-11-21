package fr.ynov.dap.model.microsoft;

import java.util.Date;

import fr.ynov.dap.contract.ApiEvent;
import fr.ynov.dap.microsoft.model.OutlookAttendee;
import fr.ynov.dap.microsoft.model.OutlookEvent;
import fr.ynov.dap.model.enumeration.AttendeeEventStatusEnum;
import fr.ynov.dap.utils.StrUtils;

/**
 * This class implement Proxy pattern.
 * It allow to manage Outlook event.
 * @author Kévin Sibué
 *
 */
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
    public Date getStartDate() {
        return event.getStart().getDateTime();
    }

    /**
     * Return ending date.
     */
    @Override
    public Date getEndDate() {
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
