package fr.ynov.dap.microsoft.model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This class describe an event from Graph API.
 * @author Kévin Sibué
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutlookEvent {

    /**
     * Store event id.
     */
    private String id;

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
     * Store ending date.
     */
    private DateTimeTimeZone end;

    /**
     * List of every attendee for current event.
     */
    private ArrayList<OutlookAttendee> attendees;

    /**
     * Event id.
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Set event id.
     * @param val new event id
     */
    public void setId(final String val) {
        this.id = val;
    }

    /**
     * Event subject.
     * @return Subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set event subject.
     * @param val new event subject
     */
    public void setSubject(final String val) {
        this.subject = val;
    }

    /**
     * Event organizer.
     * @return Organizer
     */
    public Recipient getOrganizer() {
        return organizer;
    }

    /**
     * Set event organizer.
     * @param val Event organizer
     */
    public void setOrganizer(final Recipient val) {
        this.organizer = val;
    }

    /**
     * Event starting date.
     * @return Starting date
     */
    public DateTimeTimeZone getStart() {
        return start;
    }

    /**
     * Set starting date.
     * @param val Starting date
     */
    public void setStart(final DateTimeTimeZone val) {
        this.start = val;
    }

    /**
     * Event ending date.
     * @return Ending date
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }

    /**
     * Set event ending date.
     * @param val Ending date
     */
    public void setEnd(final DateTimeTimeZone val) {
        this.end = val;
    }

    /**
     * @return the attendees
     */
    public ArrayList<OutlookAttendee> getAttendees() {
        return attendees;
    }

    /**
     * @param val the attendees to set
     */
    public void setAttendees(final ArrayList<OutlookAttendee> val) {
        this.attendees = val;
    }

}
