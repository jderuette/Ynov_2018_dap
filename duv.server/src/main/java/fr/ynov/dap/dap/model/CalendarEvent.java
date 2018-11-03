package fr.ynov.dap.dap.model;

import java.sql.Timestamp;
import java.util.List;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

/**
 *
 * @author David_tepoche
 *
 */
public class CalendarEvent {

    /**
     * subject of the calendar event.
     */
    private String subject;
    /**
     * start of the event.
     */
    private Timestamp startDate;
    /**
     * end of the event.
     */
    private Timestamp endDate;

    /**
     * status of the event.
     */
    private String status;

    /**
     * status of the user on the event.
     */
    private String personnalStatus;

    /**
     * create CalendarEvent from an google Event.
     *
     * @param event            envent from google api response
     * @param emailCurrentUser email of the authenticated user
     */
    public CalendarEvent(final Event event, final String emailCurrentUser) {
        this.startDate = new Timestamp(event.getStart().getDateTime().getValue());
        this.endDate = new Timestamp(event.getEnd().getDateTime().getValue());
        this.subject = event.getSummary();
        this.status = event.getStatus();
        // TODO duv by Djer Pas mal, mais tu n'as pas traité le cas de "je suis
        // l'organisateur"
        this.personnalStatus = setPersonnalStatus(event.getAttendees(), emailCurrentUser);
    }

    /**
     * find the personnal status of the current user.
     *
     * @param attendees list of people in the event
     * @param email     the email of the current user
     * @return the personnal status
     */
    private String setPersonnalStatus(final List<EventAttendee> attendees, final String email) {
        if (attendees == null) {
            return null;
        }
        EventAttendee attendee = attendees.stream().filter(a -> a.getEmail().equalsIgnoreCase(email)).findFirst()
                .orElse(null);
        if (attendee != null) {
            return attendee.getResponseStatus();
        }
        return null;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subjectGiven the subject to set
     */
    public void setSubject(final String subjectGiven) {
        this.subject = subjectGiven;
    }

    /**
     * @return the personnalStatus
     */
    public String getPersonnalStatus() {
        return personnalStatus;
    }

    /**
     * @param personnalStatusGiven the personnalStatus to set
     */
    public void setPersonnalStatus(final String personnalStatusGiven) {
        this.personnalStatus = personnalStatusGiven;
    }

    /**
     * @return the startDate
     */
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * @param startDateTimeStamp the startDate to set
     */
    public void setStartDate(final Timestamp startDateTimeStamp) {
        this.startDate = startDateTimeStamp;
    }

    /**
     * @return the endDate
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * @param endDateTimeStamp the endDate to set
     */
    public void setEndDate(final Timestamp endDateTimeStamp) {
        this.endDate = endDateTimeStamp;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param statusGiven the status to set
     */
    public void setStatus(final String statusGiven) {
        this.status = statusGiven;
    }

}
