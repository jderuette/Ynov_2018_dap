package fr.ynov.dap.dap.model;

import java.sql.Timestamp;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;

/**
 *
 * @author David_tepoche
 *
 */
public class CalendarEvent /* implements Comparable<CalendarEvent> */ {
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

        this.personnalStatus = setPersonnalStatus(event, emailCurrentUser);
    }

    /**
     * create CalendarEvent from an microsoft Event.
     *
     * @param event            msEvent
     * @param emailCurrentUser the email of the user
     */
    public CalendarEvent(final MicrosoftEvent event, final String emailCurrentUser) {
        this.startDate = new Timestamp(event.getStart().getDateTime().getTime());
        this.endDate = new Timestamp(event.getEnd().getDateTime().getTime());
        this.subject = event.getSubject();
        this.status = event.getResponseStatus().getResponse();
        if (!this.status.equalsIgnoreCase("organizer")) {
            this.personnalStatus = setPersonnalStatus(event, emailCurrentUser);
        }
    }

    /**
     * find the personnal status of the current user.
     *
     * @param event the GOOGLE Event
     * @param email the email of the current user
     * @return the personnal status
     */
    private String setPersonnalStatus(final Event event, final String email) {

        if (event.getOrganizer() != null && email.equalsIgnoreCase(event.getOrganizer().getEmail())) {
            personnalStatus = "Owner";
        } else {
            if (event.getAttendees() != null) {
                EventAttendee attendee = event.getAttendees().stream().filter(a -> email.equalsIgnoreCase(a.getEmail()))
                        .findFirst().orElse(null);
                if (attendee != null) {
                    personnalStatus = attendee.getResponseStatus();
                }
            }
        }
        return personnalStatus;
    }

    /**
     * get the personnal status.
     *
     * @param event msEvent
     * @param email email of the currentUSer
     * @return an email
     */
    private String setPersonnalStatus(final MicrosoftEvent event, final String email) {

        if (event.getAttendees() != null) {

            MicrosoftAttendee attendee = event.getAttendees().stream()
                    .filter(a -> email.equalsIgnoreCase(a.getEmailAddress().getAddress())).findFirst().orElse(null);
            if (attendee != null) {
                personnalStatus = attendee.getStatus().getResponse();
            }
        }
        return personnalStatus;
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
