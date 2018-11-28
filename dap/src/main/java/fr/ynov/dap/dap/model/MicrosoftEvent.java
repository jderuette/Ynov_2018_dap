package fr.ynov.dap.dap.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 * @author David_tepoche
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MicrosoftEvent {

    /**
     * Store event subject.
     */
    private String subject;

    /**
     * Store event organizer.
     */
    private Recipient organizer;

    /**
     * Store starting date.
     */
    private DateTimeTimeZone start;

    /**
     * Store the status.
     */
    private Status responseStatus;

    /**
     * Store ending date.
     */
    private DateTimeTimeZone end;

    /**
     * List of every attendee for current event.
     */
    private ArrayList<MicrosoftAttendee> attendees;

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * @return the organizer
     */
    public Recipient getOrganizer() {
        return organizer;
    }

    /**
     * @param organizer the organizer to set
     */
    public void setOrganizer(final Recipient organizer) {
        this.organizer = organizer;
    }

    /**
     * @return the start
     */
    public DateTimeTimeZone getStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(final DateTimeTimeZone start) {
        this.start = start;
    }

    /**
     * @return the end
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(final DateTimeTimeZone end) {
        this.end = end;
    }

    /**
     * @return the attendees
     */
    public ArrayList<MicrosoftAttendee> getAttendees() {
        return attendees;
    }

    /**
     * @return the responseStatus
     */
    public Status getResponseStatus() {
        return responseStatus;
    }

    /**
     * @param responseStatus the responseStatus to set
     */
    public void setResponseStatus(final Status responseStatus) {
        this.responseStatus = responseStatus;
    }

    /**
     * @param attendees the attendees to set
     */
    public void setAttendees(final ArrayList<MicrosoftAttendee> attendees) {
        this.attendees = attendees;
    }

}
