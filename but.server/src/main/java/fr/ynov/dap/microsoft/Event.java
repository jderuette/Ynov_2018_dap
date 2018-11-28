package fr.ynov.dap.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Entity Event.
 * @author thibault
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {
    /**
     * Unique Id.
     */
    private String id;
    /**
     * Subjet.
     */
    private String subject;
    /**
     * I'm organiser ?
     */
    private Recipient organizer;
    /**
     * Start date.
     */
    private DateTimeTimeZone start;
    /**
     * End date.
     */
    private DateTimeTimeZone end;
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param idToSet the id to set
     */
    public void setId(final String idToSet) {
        this.id = idToSet;
    }
    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    /**
     * @param subjectToSet the subject to set
     */
    public void setSubject(final String subjectToSet) {
        this.subject = subjectToSet;
    }
    /**
     * @return the organizer
     */
    public Recipient getOrganizer() {
        return organizer;
    }
    /**
     * @param organizerToSet the organizer to set
     */
    public void setOrganizer(final Recipient organizerToSet) {
        this.organizer = organizerToSet;
    }
    /**
     * @return the start
     */
    public DateTimeTimeZone getStart() {
        return start;
    }
    /**
     * @param startToSet the start to set
     */
    public void setStart(final DateTimeTimeZone startToSet) {
        this.start = startToSet;
    }
    /**
     * @return the end
     */
    public DateTimeTimeZone getEnd() {
        return end;
    }
    /**
     * @param endToSet the end to set
     */
    public void setEnd(final DateTimeTimeZone endToSet) {
        this.end = endToSet;
    }
}
