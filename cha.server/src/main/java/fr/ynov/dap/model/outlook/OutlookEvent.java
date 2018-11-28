package fr.ynov.dap.model.outlook;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private DateTimeZone start;

    /**
     * Store ending date.
     */
    private DateTimeZone end;

    /**
     * List of every attendee for current event.
     */
    private ArrayList<OutlookGuest> guest;

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
    public DateTimeZone getStart() {
        return start;
    }

    /**
     * Set starting date.
     * @param val Starting date
     */
    public void setStart(final DateTimeZone val) {
        this.start = val;
    }

    /**
     * Event ending date.
     * @return Ending date
     */
    public DateTimeZone getEnd() {
        return end;
    }

    /**
     * Set event ending date.
     * @param val Ending date
     */
    public void setEnd(final DateTimeZone val) {
        this.end = val;
    }

    /**
     * @return the attendees
     */
    public ArrayList<OutlookGuest> getGuests() {
        return guest;
    }

    /**
     * @param val the attendees to set
     */
    public void setAttendees(final ArrayList<OutlookGuest> val) {
        this.guest = val;
    }
}    
